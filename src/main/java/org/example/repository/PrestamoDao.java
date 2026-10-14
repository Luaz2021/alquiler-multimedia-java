package org.example.repository;

import org.example.database.ConexionDB;
import org.example.model.Prestamo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDao {

    public void registrarPrestamo(Prestamo prestamo) {
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(Query.INSERTAR_PRESTAMO)) {

            ps.setString(1, prestamo.getUsuario_dni());
            ps.setString(2, prestamo.getArticulo_num_serie());
            ps.setDate(3, Date.valueOf(prestamo.getFecha_prestamo()));
            ps.setDate(4, Date.valueOf(prestamo.getFecha_devolucion_esperada()));

            if (prestamo.getFecha_devolucion_real() != null) {
                ps.setDate(5, Date.valueOf(prestamo.getFecha_devolucion_real()));
            } else {
                ps.setNull(5, Types.DATE);
            }

            ps.executeUpdate();
            System.out.println("¡Préstamo registrado exitosamente en la base de datos!");

        } catch (SQLException e) {
            System.err.println("Error al registrar préstamo: " + e.getMessage());
        }
    }

    public void registrarDevolucion(int idPrestamo, LocalDate fechaReal) {
        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(Query.REGISTRAR_DEVOLUCION)) {

            ps.setDate(1, Date.valueOf(fechaReal));
            ps.setInt(2, idPrestamo);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("¡Devolución procesada con éxito para el préstamo ID: " + idPrestamo + "!");
            } else {
                System.out.println("No se encontró ningún préstamo con el ID: " + idPrestamo);
            }

        } catch (SQLException e) {
            System.err.println("Error al procesar la devolución: " + e.getMessage());
        }
    }

    public List<Prestamo> listarTodos() {
        List<Prestamo> lista = new ArrayList<>();

        try (Connection con = ConexionDB.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(Query.LISTAR_TODOS_PRESTAMOS);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_prestamo");
                String dni = rs.getString("usuario_dni");
                String serie = rs.getString("articulo_num_serie");
                LocalDate fPrestamo = rs.getDate("fecha_prestamo").toLocalDate();
                LocalDate fEsperada = rs.getDate("fecha_devolucion_esperada").toLocalDate();

                Date fRealSQL = rs.getDate("fecha_devolucion_real");
                LocalDate fReal = (fRealSQL != null) ? fRealSQL.toLocalDate() : null;

                lista.add(new Prestamo(serie, fEsperada, fReal, fPrestamo, id, dni));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar préstamos: " + e.getMessage());
        }
        return lista;
    }
}