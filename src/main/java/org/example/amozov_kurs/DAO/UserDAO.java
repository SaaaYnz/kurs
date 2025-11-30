package org.example.amozov_kurs.DAO;

import org.example.amozov_kurs.Models.User;
import java.sql.*;

public class UserDAO {

    public User validateUser(String log, String password) {
        String sql = "SELECT * FROM users WHERE (login = ? OR email = ?) AND password = ?";

        try (Connection db = DbConnection.getConnection();
             PreparedStatement stmt = db.prepareStatement(sql)) {

            stmt.setString(1, log);
            stmt.setString(2, log);
            stmt.setString(3, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id_users"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("role"),
                            rs.getString("email"),
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
        String role = "client";

        String sql = "INSERT INTO users (first_name, last_name, email, login, password, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection db = DbConnection.getConnection();
             PreparedStatement stmt = db.prepareStatement(sql)) {

            stmt.setString(1, first_name);
            stmt.setString(2, last_name);
            stmt.setString(3, email);
            stmt.setString(4, login);
            stmt.setString(5, password);
            stmt.setString(6, role);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean userExists(String username) {
        String sql = "SELECT 1 FROM users WHERE login = ? or email = ?";

        try (Connection db = DbConnection.getConnection();
             PreparedStatement stmt = db.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserRole(int userId, String newRole) {
        if (!isValidRole(newRole)) {
            return false;
        }

        String sql = "UPDATE users SET role = ? WHERE id_users = ?";

        try (Connection db = DbConnection.getConnection();
             PreparedStatement stmt = db.prepareStatement(sql)) {

            stmt.setString(1, newRole);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public java.util.List<User> getUsersByRole(String role) {
        java.util.List<User> users = new java.util.ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = ?";

        try (Connection db = DbConnection.getConnection();
             PreparedStatement stmt = db.prepareStatement(sql)) {

            stmt.setString(1, role);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User(
                            rs.getInt("id_users"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("role"),
                            rs.getString("email"),
                            rs.getString("login")
                    );
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private boolean isValidRole(String role) {
        return "client".equals(role) || "consultant".equals(role) || "admin".equals(role);
    }
}