package org.example.repository;

import org.example.database.ConexionDB;
import org.example.model.AbstractArticulo;
import org.example.model.Pelicula;
import org.example.model.VideoJuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDao {

    public void guardar(AbstractArticulo articulo) {
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(Query.INSERTAR_ARTICULO)) {

            ps.setString(1, articulo.getNum_serie());
            ps.setString(2, articulo.getTitulo());
            ps.setString(3, articulo.getGenero());

            // Plataforma solo si es Videojuego; en Película es NULL
            if (articulo instanceof VideoJuego juego) {
                ps.setString(4, juego.getPlataforma());
            } else {
                ps.setNull(4, Types.VARCHAR);
            }

            ps.setString(5, articulo.getTipo());

            ps.executeUpdate();
            System.out.println("Artículo (" + articulo.getTipo() + ") registrado con éxito en la base de datos!");

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.err.println("Error: Ya existe un artículo con número de serie: " + articulo.getNum_serie());
            } else {
                System.err.println("Error al guardar artículo en la BD: " + e.getMessage());
            }
        }
    }

    public AbstractArticulo buscarPorNumSerie(String numSerie) {
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(Query.BUSCAR_ARTICULO_POR_SERIE)) {

            ps.setString(1, numSerie);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String serie = rs.getString("num_serie");
                    String titulo = rs.getString("titulo");
                    String genero = rs.getString("genero");
                    String plataforma = rs.getString("plataforma");
                    String tipo = rs.getString("tipo");

                    if ("VIDEOJUEGO".equalsIgnoreCase(tipo)) {
                        return new VideoJuego(genero, serie, titulo, plataforma);
                    } else {
                        return new Pelicula(genero, serie, titulo);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar artículo: " + e.getMessage());
        }
        return null;
    }

    public List<AbstractArticulo> listarTodos() {
        List<AbstractArticulo> lista = new ArrayList<>();

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(Query.LISTAR_ARTICULOS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String serie = rs.getString("num_serie");
                String titulo = rs.getString("titulo");
                String genero = rs.getString("genero");
                String plataforma = rs.getString("plataforma");
                String tipo = rs.getString("tipo");

                if ("VIDEOJUEGO".equalsIgnoreCase(tipo)) {
                    lista.add(new VideoJuego(genero, serie, titulo, plataforma));
                } else {
                    lista.add(new Pelicula(genero, serie, titulo));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al listar artículos: " + e.getMessage());
        }
        return lista;
    }

    public void actualizar(String numSerie, String titulo, String genero, String plataforma, String tipo) {
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(Query.ACTUALIZAR_ARTICULO)) {

            ps.setString(1, titulo);
            ps.setString(2, genero);

            if (plataforma != null && !plataforma.isBlank()) {
                ps.setString(3, plataforma);
            } else {
                ps.setNull(3, Types.VARCHAR);
            }

            ps.setString(4, numSerie);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Artículo actualizado correctamente.");
            } else {
                System.out.println("No se encontró ningún artículo con serie: " + numSerie);
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar artículo: " + e.getMessage());
        }
    }

    public void eliminarPorNumSerie(String numSerie) {
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(Query.ELIMINAR_ARTICULO)) {

            ps.setString(1, numSerie);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Artículo con serie " + numSerie + " eliminado con éxito.");
            } else {
                System.out.println("No se encontró ningún artículo con serie: " + numSerie);
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar artículo: " + e.getMessage());
        }
    }
}