package org.example.amozov_kurs.DAO;

import org.example.amozov_kurs.Models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDate;

public class UserDAO {
    private static final DbConnection dbConnection = new DbConnection();
    public User authenticate(String username, String plainPassword) throws  ClassNotFoundException, SQLException {
        User u = findByUsername(username);
        if (u == null) return null;
        System.out.println(BCrypt.checkpw(plainPassword, u.getPassword()));
        if (BCrypt.checkpw(plainPassword, u.getPassword())) return u;
        return null;
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE login = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Date birthdayDate = rs.getDate("birthday");
                return new User(
                        rs.getInt("id_users"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getString("inn"),
                        rs.getString("passport"),
                        rs.getString("login"),
                        rs.getString("password"),
                        birthdayDate,
                        rs.getString("phone_number")
                );
            }
        } catch (SQLException e) { e.printStackTrace(); } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean registerUser(String first_name, String last_name, String email,
                                String login, String password, LocalDate birthday,
                                String phone_number, String inn, String passport) {
        String role = "client";
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        String sql = "INSERT INTO users (first_name, last_name, email, login, password, role, birthday, phone_number, inn, passport) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection db = dbConnection.getConnection();
             PreparedStatement stmt = db.prepareStatement(sql)) {

            stmt.setString(1, first_name);
            stmt.setString(2, last_name);
            stmt.setString(3, email);
            stmt.setString(4, login);
            stmt.setString(5, hash);
            stmt.setString(6, role);

            if (birthday != null) {
                stmt.setDate(7, Date.valueOf(birthday));
            } else {
                stmt.setNull(7, Types.DATE);
            }

            if (phone_number != null && !phone_number.trim().isEmpty()) {
                stmt.setString(8, phone_number.trim());
            } else {
                stmt.setNull(8, Types.VARCHAR);
            }
            stmt.setString(9, inn);
            stmt.setString(10, passport);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void checkUniqueEmail(String email) throws SQLException {
        if (email.isEmpty()) return;
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        checkExists(sql, email);
    }

    public static void checkUniqueInn(String inn) throws SQLException {
        if (inn.isEmpty()) return;
        String sql = "SELECT COUNT(*) FROM users WHERE inn = ?";
        checkExists(sql, inn);
    }

    public static void checkUniquePassport(String passport) throws SQLException {
        if (passport.isEmpty()) return;
        String sql = "SELECT COUNT(*) FROM users WHERE passport = ?";
        checkExists(sql, passport);
    }

    public static void checkUniqueLogin(String login) throws SQLException {
        if (login.isEmpty()) return;
        String sql = "SELECT COUNT(*) FROM users WHERE login = ?";
        checkExists(sql, login);
    }

    private static void checkExists(String sql, String value) throws SQLException {
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, value);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                throw new SQLException("Значение '" + value + "' уже существует");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}