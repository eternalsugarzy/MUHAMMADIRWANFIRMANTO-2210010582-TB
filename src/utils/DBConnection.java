package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/db_ppdb"; // Ganti sesuai nama database Anda
    private static final String USER = "root"; // Ganti sesuai username database Anda
    private static final String PASSWORD = ""; // Ganti sesuai password database Anda

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Gagal terhubung ke database.");
        }
    }
}
