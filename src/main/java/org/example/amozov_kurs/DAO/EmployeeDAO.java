package org.example.amozov_kurs.DAO;

import org.example.amozov_kurs.Models.Employee;

import java.sql.*;

public class EmployeeDAO {

    public Employee validateUser(String log, String password) {
        String sql = "SELECT * FROM employees WHERE (login = ? OR email = ?) AND password = ?";

        try (Connection db = DbConnection.getConnection();
             PreparedStatement stmt = db.prepareStatement(sql)) {

            stmt.setString(1, log);
            stmt.setString(2, log);
            stmt.setString(3, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Employee(
                            rs.getInt("id_employees"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("role"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getString("birthday"),
                            rs.getString("login")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registerUser(String first_name, String last_name, String email, String login, String password) {
        String sql = "INSERT INTO employees (first_name, last_name, email, login, password) VALUES (?, ?, ?, ?)";

        try (Connection db = DbConnection.getConnection();
             PreparedStatement stmt = db.prepareStatement(sql)) {

            stmt.setString(1, first_name);
            stmt.setString(2, last_name);
            stmt.setString(3, email);
            stmt.setString(4, login);
            stmt.setString(5, password);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean userExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";

        try (Connection db = DbConnection.getConnection();
             PreparedStatement stmt = db.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}