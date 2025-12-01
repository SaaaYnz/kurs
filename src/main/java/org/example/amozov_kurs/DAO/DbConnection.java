package org.example.amozov_kurs.DAO;

import java.net.URL;
import java.sql.*;

public class DbConnection {
//    private static final String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=car_dealership";
//    private static final String USER = "postgres";
//    private static final String PASSWORD = "123";

    private static final String URL = "jdbc:postgresql://2.nntc.nnov.ru:6543/postgres_user_2?currentSchema=kurs_amozov";
    private static final String USER = "postgres_user_2";
    private static final String PASSWORD = "tb718r5w";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}