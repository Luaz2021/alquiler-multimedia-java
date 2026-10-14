package org.example.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    // Configura con tus credenciales y nombre de base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    // Método validador que comprueba si la conexión está viva
    public static boolean probarConexion() {
        try (Connection con = obtenerConexion()) {
            return con != null && !con.isClosed();
        } catch (SQLException e) {
            System.err.println("Fallo de conexión: " + e.getMessage());
            return false;
        }
    }
}
