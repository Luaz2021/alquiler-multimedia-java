package org.example.service;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.model.AbstractArticulo;
import org.example.model.AbstractUsuario;
import org.example.model.Docente;
import org.example.model.Prestamo;
import org.example.model.VideoJuego;
import org.example.repository.ArticuloDao;
import org.example.repository.PrestamoDao;
import org.example.repository.UsuarioDao;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

public class ServidorWeb {

    private final UsuarioDao usuarioDao = new UsuarioDao();
    private final ArticuloDao articuloDao = new ArticuloDao();
    private final PrestamoDao prestamoDao = new PrestamoDao();

    public void iniciarServidor(int puerto) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(puerto), 0);

            // Endpoint para devolver todos los datos en JSON
            server.createContext("/api/datos", new DatosHandler());

            // Endpoint para procesar devoluciones
            server.createContext("/api/devolver", new DevolverHandler());

            server.setExecutor(null);
            server.start();
            System.out.println("¡Servidor Web activo en http://localhost:" + puerto + "!");
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    private class DatosHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            configurarCORS(exchange);

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            List<AbstractUsuario> usuarios = usuarioDao.listarTodos();
            List<AbstractArticulo> articulos = articuloDao.listarTodos();
            List<Prestamo> prestamos = prestamoDao.listarTodos();

            LocalDate hoy = LocalDate.now();
            int activos = 0;
            int vencidos = 0;
            int totalAlumnos = 0;
            int totalDocentes = 0;

            // JSON de Usuarios
            StringBuilder jsonUsuarios = new StringBuilder("[");
            for (int i = 0; i < usuarios.size(); i++) {
                AbstractUsuario u = usuarios.get(i);
                if ("DOCENTE".equalsIgnoreCase(u.getTipo())) totalDocentes++; else totalAlumnos++;

                String asig = (u instanceof Docente d) ? d.getAsignatura() : "";
                jsonUsuarios.append(String.format(
                        "{\"dni\":\"%s\",\"nombre\":\"%s\",\"edad\":%d,\"tipo\":\"%s\",\"asignatura\":\"%s\"}",
                        u.getDni(), u.getNombre(), u.getEdad(), u.getTipo(), asig != null ? asig : ""
                ));
                if (i < usuarios.size() - 1) jsonUsuarios.append(",");
            }
            jsonUsuarios.append("]");

            // JSON de Artículos
            StringBuilder jsonArticulos = new StringBuilder("[");
            for (int i = 0; i < articulos.size(); i++) {
                AbstractArticulo a = articulos.get(i);
                String plat = (a instanceof VideoJuego vj) ? vj.getPlataforma() : "";
                jsonArticulos.append(String.format(
                        "{\"serie\":\"%s\",\"titulo\":\"%s\",\"genero\":\"%s\",\"tipo\":\"%s\",\"plataforma\":\"%s\"}",
                        a.getNum_serie(), a.getTitulo(), a.getGenero(), a.getTipo(), plat != null ? plat : ""
                ));
                if (i < articulos.size() - 1) jsonArticulos.append(",");
            }
            jsonArticulos.append("]");

            // JSON de Préstamos
            StringBuilder jsonPrestamos = new StringBuilder("[");
            for (int i = 0; i < prestamos.size(); i++) {
                Prestamo p = prestamos.get(i);
                String estado;
                if (p.getFecha_devolucion_real() != null) {
                    estado = "DEVUELTO";
                } else if (hoy.isAfter(p.getFecha_devolucion_esperada())) {
                    estado = "VENCIDO";
                    vencidos++;
                } else {
                    estado = "ACTIVO";
                    activos++;
                }

                jsonPrestamos.append(String.format(
                        "{\"id\":%d,\"dni\":\"%s\",\"serie\":\"%s\",\"fechaPrestamo\":\"%s\",\"fechaEsperada\":\"%s\",\"estado\":\"%s\"}",
                        p.getId_prestamo(), p.getUsuario_dni(), p.getArticulo_num_serie(),
                        p.getFecha_prestamo(), p.getFecha_devolucion_esperada(), estado
                ));
                if (i < prestamos.size() - 1) jsonPrestamos.append(",");
            }
            jsonPrestamos.append("]");

            // Respuesta Completa
            String respuesta = String.format(
                    "{\"metricas\":{\"activos\":%d,\"vencidos\":%d,\"totalUsuarios\":%d,\"alumnos\":%d,\"docentes\":%d}," +
                            "\"usuarios\":%s,\"articulos\":%s,\"prestamos\":%s}",
                    activos, vencidos, usuarios.size(), totalAlumnos, totalDocentes,
                    jsonUsuarios, jsonArticulos, jsonPrestamos
            );

            byte[] bytes = respuesta.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    private class DevolverHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            configurarCORS(exchange);

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            // Obtener parámetro ?id=X
            String query = exchange.getRequestURI().getQuery();
            if (query != null && query.startsWith("id=")) {
                int idPrestamo = Integer.parseInt(query.substring(3));
                prestamoDao.registrarDevolucion(idPrestamo, LocalDate.now());
            }

            String respuesta = "{\"status\":\"ok\"}";
            byte[] bytes = respuesta.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    private void configurarCORS(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
    }
}