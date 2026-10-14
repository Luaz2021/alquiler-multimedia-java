package org.example.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.example.model.*;
import org.example.repository.*;
import org.example.Utils.ValidacionesUtil;

public class GestionAlquiler {

    private final Scanner sc = new Scanner(System.in);
    private final ValidacionesUtil v = new ValidacionesUtil();

    private final UsuarioDao usuarioDao = new UsuarioDao();
    private final ArticuloDao articuloDao = new ArticuloDao();
    private final PrestamoDao prestamoDao = new PrestamoDao();

    // ==========================================
    //            GESTIÓN DE USUARIOS
    // ==========================================

    public void registrarUsuario() {
        String nombreUsuario = v.leerTextoNoVacio(sc, "Ingrese el nombre del usuario: ");
        String dniUsuario = v.leerDniValidado(sc, "Ingrese el DNI/NIE del usuario: ");
        int edadUsuario = v.leerEnteroPositivo(sc, "Ingrese edad del usuario: ");

        System.out.println("\n¿Qué tipo de usuario es?");
        System.out.println("1. Estudiante");
        System.out.println("2. Docente");
        int opcionRegistro = v.leerEnteroValidado(sc, "Ingrese opción: ");

        switch (opcionRegistro) {
            case 1:
                Alumno alumno = new Alumno(nombreUsuario, dniUsuario, edadUsuario);
                usuarioDao.guardar(alumno);
                break;
            case 2:
                String asignatura = v.leerTextoNoVacio(sc, "Ingrese la asignatura: ");
                Docente docente = new Docente(nombreUsuario, dniUsuario, edadUsuario, asignatura);
                usuarioDao.guardar(docente);
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    public void modificarUsuario() {
        String dni = v.leerDniValidado(sc, "Ingrese el DNI/NIE del usuario a modificar: ");
        AbstractUsuario usuarioExistente = usuarioDao.buscarPorDni(dni);

        if (usuarioExistente == null) {
            System.out.println("⚠️ Usuario no encontrado.");
            return;
        }

        System.out.println("Modificando datos de: " + usuarioExistente.getNombre());
        String nuevoNombre = v.leerTextoNoVacio(sc, "Nuevo nombre: ");
        int nuevaEdad = v.leerEnteroPositivo(sc, "Nueva edad: ");

        String nuevaAsignatura = null;
        if (usuarioExistente instanceof Docente) {
            nuevaAsignatura = v.leerTextoNoVacio(sc, "Nueva asignatura: ");
        }

        usuarioDao.actualizar(dni, nuevoNombre, nuevaEdad, nuevaAsignatura, usuarioExistente.getTipo());
    }

    public void eliminarUsuario() {
        String dni = v.leerDniValidado(sc, "Ingrese el DNI/NIE del usuario a eliminar: ");
        usuarioDao.eliminarPorDni(dni);
    }

    // ==========================================
    //            GESTIÓN DE ARTÍCULOS
    // ==========================================

    public void registrarArticulo() {
        String numSerie = v.leerTextoNoVacio(sc, "Ingrese número de serie: ");
        String titulo = v.leerTextoNoVacio(sc, "Ingrese título: ");
        String genero = v.leerTextoNoVacio(sc, "Ingrese género: ");

        System.out.println("\n¿Qué tipo de artículo es?");
        System.out.println("1. Película");
        System.out.println("2. Videojuego");
        int opcionArticulo = v.leerEnteroValidado(sc, "Ingrese opción: ");

        switch (opcionArticulo) {
            case 1:
                Pelicula pelicula = new Pelicula(genero, numSerie, titulo);
                articuloDao.guardar(pelicula);
                break;
            case 2:
                String plataforma = v.leerTextoNoVacio(sc, "Ingrese plataforma (PS5, PC, Switch...): ");
                VideoJuego juego = new VideoJuego(genero, numSerie, titulo, plataforma);
                articuloDao.guardar(juego);
                break;
            default:
                System.out.println("⚠️ Opción no válida.");
                break;
        }
    }

    public void modificarArticulo() {
        String numSerie = v.leerTextoNoVacio(sc, "Ingrese el número de serie del artículo a modificar: ");
        AbstractArticulo artExistente = articuloDao.buscarPorNumSerie(numSerie);

        if (artExistente == null) {
            System.out.println("⚠️ Artículo no encontrado.");
            return;
        }

        System.out.println("Modificando artículo: " + artExistente.getTitulo());
        String nuevoTitulo = v.leerTextoNoVacio(sc, "Nuevo título: ");
        String nuevoGenero = v.leerTextoNoVacio(sc, "Nuevo género: ");

        String nuevaPlataforma = null;
        if (artExistente instanceof VideoJuego) {
            nuevaPlataforma = v.leerTextoNoVacio(sc, "Nueva plataforma: ");
        }

        articuloDao.actualizar(numSerie, nuevoTitulo, nuevoGenero, nuevaPlataforma, artExistente.getTipo());
    }

    public void eliminarArticulo() {
        String numSerie = v.leerTextoNoVacio(sc, "Ingrese el número de serie del artículo a eliminar: ");
        articuloDao.eliminarPorNumSerie(numSerie);
    }

    // ==========================================
    //      GESTIÓN DE PRÉSTAMOS Y DEVOLUCIONES
    // ==========================================

    public void prestarArticulo() {
        // Valida tanto DNI como NIE
        String dni = v.leerDniValidado(sc, "Ingrese DNI/NIE del usuario: ");
        // Admite letras y números para el código del artículo
        String numSerie = v.leerTextoNoVacio(sc, "Ingrese número de serie del artículo: ");
        int diasPrestamo = v.leerEnteroPositivo(sc, "¿Por cuántos días se prestará?: ");

        LocalDate fechaPrestamo = LocalDate.now();
        LocalDate fechaDevolucionEsperada = fechaPrestamo.plusDays(diasPrestamo);

        Prestamo nuevoPrestamo = new Prestamo(numSerie, fechaDevolucionEsperada, null, fechaPrestamo, 0, dni);
        prestamoDao.registrarPrestamo(nuevoPrestamo);
    }

    public void devolverArticulo() {
        int idPrestamo = v.leerEnteroPositivo(sc, "Ingrese el ID del préstamo a devolver: ");
        LocalDate fechaReal = LocalDate.now();

        prestamoDao.registrarDevolucion(idPrestamo, fechaReal);
    }

    // ==========================================
    //            CONSULTAS GENERALES
    // ==========================================

    public void revisarDatos() {
        System.out.println("\n=== LISTA DE USUARIOS ===");
        List<AbstractUsuario> usuarios = usuarioDao.listarTodos();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            for (AbstractUsuario u : usuarios) {
                System.out.println("- [" + u.getTipo() + "] DNI: " + u.getDni() + " | Nombre: " + u.getNombre() + " | Edad: " + u.getEdad());
            }
        }

        System.out.println("\n=== LISTA DE ARTÍCULOS ===");
        List<AbstractArticulo> articulos = articuloDao.listarTodos();
        if (articulos.isEmpty()) {
            System.out.println("No hay artículos registrados.");
        } else {
            for (AbstractArticulo a : articulos) {
                System.out.println("- [" + a.getTipo() + "] Serie: " + a.getNum_serie() + " | Título: " + a.getTitulo() + " | Género: " + a.getGenero());
            }
        }

        System.out.println("\n=== ESTADO DE PRÉSTAMOS ===");
        List<Prestamo> prestamos = prestamoDao.listarTodos();
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos registrados.");
        } else {
            LocalDate hoy = LocalDate.now();

            for (Prestamo p : prestamos) {
                String estado;
                if (p.getFecha_devolucion_real() != null) {
                    estado = "DEVUELTO el " + p.getFecha_devolucion_real();
                } else if (hoy.isAfter(p.getFecha_devolucion_esperada())) {
                    estado = "¡VENCIDO! (Límite era: " + p.getFecha_devolucion_esperada() + ")";
                } else {
                    estado = "ACTIVO (Fecha límite: " + p.getFecha_devolucion_esperada() + ")";
                }

                System.out.println("- ID: " + p.getId_prestamo() +
                        " | Doc/DNI: " + p.getUsuario_dni() +
                        " | Num Serie: " + p.getArticulo_num_serie() +
                        " | Estado: " + estado);
            }
        }
    }
}