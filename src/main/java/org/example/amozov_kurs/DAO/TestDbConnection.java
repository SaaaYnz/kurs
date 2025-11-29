package org.example.amozov_kurs.DAO;


import java.sql.Connection;
import java.sql.DriverManager;

public class TestDbConnection {
    static void main(String[] args) {
        String URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=car_dealership";
        String USER = "postgres";
        String PASSWORD = "123";

        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("ура");
            conn.close();
        } catch (Exception e) {
            System.out.println("не ура " + e.getMessage());
        }
    }
}