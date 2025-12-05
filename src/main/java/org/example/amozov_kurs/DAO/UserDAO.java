package org.example.amozov_kurs.DAO;

import org.example.amozov_kurs.Models.User;
import java.sql.*;
import java.time.LocalDate;

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
                    Date birthdayDate = rs.getDate("birthday");
                    LocalDate birthday = null;
                    if (birthdayDate != null) {
                        birthday = birthdayDate.toLocalDate();
                    }

                    return new User(
                            rs.getInt("id_users"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("role"),
                            rs.getString("email"),
                            rs.getString("login"),
                            birthday,
                            rs.getString("phone_number")  // Добавляем телефон
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registerUser(String first_name, String last_name, String email,
                                String login, String password, LocalDate birthday,
                                String phone_number) {  // Добавляем телефон
        String role = "client";

        String sql = "INSERT INTO users (first_name, last_name, email, login, password, role, birthday, phone_number) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection db = DbConnection.getConnection();
             PreparedStatement stmt = db.prepareStatement(sql)) {

            stmt.setString(1, first_name);
            stmt.setString(2, last_name);
            stmt.setString(3, email);
            stmt.setString(4, login);
            stmt.setString(5, password);
            stmt.setString(6, role);

            // Дата рождения
            if (birthday != null) {
                stmt.setDate(7, Date.valueOf(birthday));
            } else {
                stmt.setNull(7, Types.DATE);
            }

            // Телефон (может быть null)
            if (phone_number != null && !phone_number.trim().isEmpty()) {
                stmt.setString(8, phone_number.trim());
            } else {
                stmt.setNull(8, Types.VARCHAR);
            }

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Перегрузка для обратной совместимости
    public boolean registerUser(String first_name, String last_name, String email,
                                String login, String password, LocalDate birthday) {
        return registerUser(first_name, last_name, email, login, password, birthday, null);
    }

    public boolean registerUser(String first_name, String last_name, String email,
                                String login, String password) {
        return registerUser(first_name, last_name, email, login, password, null, null);
    }

    // Проверка существует ли телефон (чтобы не было дубликатов)
    public boolean phoneExists(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }

        String sql = "SELECT 1 FROM users WHERE phone_number = ?";

        try (Connection db = DbConnection.getConnection();
             PreparedStatement stmt = db.prepareStatement(sql)) {

            stmt.setString(1, phone.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Обновление телефона пользователя
    public boolean updateUserPhone(int userId, String phone) {
        String sql = "UPDATE users SET phone_number = ? WHERE id_users = ?";

        try (Connection db = DbConnection.getConnection();
             PreparedStatement stmt = db.prepareStatement(sql)) {

            if (phone != null && !phone.trim().isEmpty()) {
                stmt.setString(1, phone.trim());
            } else {
                stmt.setNull(1, Types.VARCHAR);
            }
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Поиск пользователя по телефону
    public User findUserByPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return null;
        }

        String sql = "SELECT * FROM users WHERE phone_number = ?";

        try (Connection db = DbConnection.getConnection();
             PreparedStatement stmt = db.prepareStatement(sql)) {

            stmt.setString(1, phone.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Date birthdayDate = rs.getDate("birthday");
                    LocalDate birthday = null;
                    if (birthdayDate != null) {
                        birthday = birthdayDate.toLocalDate();
                    }

                    return new User(
                            rs.getInt("id_users"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("role"),
                            rs.getString("email"),
                            rs.getString("login"),
                            birthday,
                            rs.getString("phone_number")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public java.util.List<User> getUsersByRole(String role) {
        java.util.List<User> users = new java.util.ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = ?";

        try (Connection db = DbConnection.getConnection();
             PreparedStatement stmt = db.prepareStatement(sql)) {

            stmt.setString(1, role);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Date birthdayDate = rs.getDate("birthday");
                    LocalDate birthday = null;
                    if (birthdayDate != null) {
                        birthday = birthdayDate.toLocalDate();
                    }

                    User user = new User(
                            rs.getInt("id_users"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("role"),
                            rs.getString("email"),
                            rs.getString("login"),
                            birthday,
                            rs.getString("phone_number")
                    );
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}