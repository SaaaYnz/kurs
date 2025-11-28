package org.example.amozov_kurs.DAO;

import java.net.URL;
import java.sql.*;

public class DbConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=car_dealership";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}