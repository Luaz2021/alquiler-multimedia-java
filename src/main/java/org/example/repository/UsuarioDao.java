package org.example.repository;

import org.example.database.ConexionDB;
import org.example.model.AbstractUsuario;
import org.example.model.Alumno;
import org.example.model.Docente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    public void guardar(AbstractUsuario usuario) {
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(Query.INSERTAR_USUARIO)) {

            ps.setString(1, usuario.getDni());
            ps.setString(2, usuario.getNombre());
            ps.setInt(3, usuario.getEdad());

            if (usuario instanceof Docente docente) {
                ps.setString(4, docente.getAsignatura());
            } else {
                ps.setNull(4, Types.VARCHAR);
            }

            ps.setString(5, usuario.getTipo());

            ps.executeUpdate();
            System.out.println(" ¡Usuario (" + usuario.getTipo() + ") registrado con éxito en la base de datos!");

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.err.println(" Error: Ya existe un usuario registrado con el DNI: " + usuario.getDni());
            } else {
                System.err.println(" Error al guardar en la BD: " + e.getMessage());
            }
        }
    }

    public AbstractUsuario buscarPorDni(String dni) {
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(Query.BUSCAR_POR_DNI)) {

            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String dniBD = rs.getString("dni");
                    String nombre = rs.getString("nombre");
                    int edad = rs.getInt("edad");
                    String asignatura = rs.getString("asignatura");
                    String tipo = rs.getString("tipo");

                    if ("DOCENTE".equalsIgnoreCase(tipo)) {
                        return new Docente(nombre, dniBD, edad, asignatura);
                    } else {
                        return new Alumno(nombre, dniBD, edad);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println(" Error al consultar usuario: " + e.getMessage());
        }
        return null;
    }

    public List<AbstractUsuario> listarTodos() {
        List<AbstractUsuario> usuarios = new ArrayList<>();

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(Query.LISTAR_TODOS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                String asignatura = rs.getString("asignatura");
                String tipo = rs.getString("tipo");

                if ("DOCENTE".equalsIgnoreCase(tipo)) {
                    usuarios.add(new Docente(nombre, dni, edad, asignatura));
                } else {
                    usuarios.add(new Alumno(nombre, dni, edad));
                }
            }

        } catch (SQLException e) {
            System.err.println(" Error al listar usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    public void actualizar(String dni, String nombre, int edad, String asignatura, String tipo) {
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(Query.ACTUALIZAR)) {

            ps.setString(1, nombre);
            ps.setInt(2, edad);

            if (asignatura != null && !asignatura.isBlank()) {
                ps.setString(3, asignatura);
            } else {
                ps.setNull(3, Types.VARCHAR);
            }

            ps.setString(4, tipo);
            ps.setString(5, dni);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println(" Usuario actualizado correctamente.");
            } else {
                System.out.println("️ No se encontró ningún usuario con DNI: " + dni);
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

    public void eliminarPorDni(String dni) {
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(Query.ELIMINAR_POR_DNI)) {

            ps.setString(1, dni);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Usuario con DNI " + dni + " eliminado con éxito.");
            } else {
                System.out.println("No se encontró ningún usuario con DNI: " + dni);
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }
    }
}