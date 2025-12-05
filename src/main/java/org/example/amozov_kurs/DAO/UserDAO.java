package org.example.amozov_kurs.DAO;

import org.example.amozov_kurs.Models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDate;

public class UserDAO {

//    public User validateUser(String log, String password) {
//        String sql = "SELECT * FROM users WHERE (login = ? OR email = ?) AND password = ?";
//        User u = findByUsername(log);
//        String unhash = String.valueOf(BCrypt.checkpw(password, u.getPassword()));
//        try (Connection db = DbConnection.getConnection();
//             PreparedStatement stmt = db.prepareStatement(sql)) {
//
//            stmt.setString(1, log);
//            stmt.setString(2, log);
//            stmt.setString(3, unhash);
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    Date birthdayDate = rs.getDate("birthday");
//
//                    return new User(
//                            rs.getInt("id_users"),
//                            rs.getString("first_name"),
//                            rs.getString("last_name"),
//                            rs.getString("role"),
//                            rs.getString("email"),
//                            rs.getString("login"),
//                            rs.getString("password"),
//                            birthdayDate,
//                            rs.getString("phone_number")
//                    );
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public User authenticate(String username, String plainPassword) throws  ClassNotFoundException, SQLException {
        User u = findByUsername(username);
        if (u == null) return null;
        System.out.println(BCrypt.checkpw(plainPassword, u.getPassword()));
        if (BCrypt.checkpw(plainPassword, u.getPassword())) return u;
        return null;
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE login = ?";
        try (Connection conn = DbConnection.getConnection();
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
                        rs.getString("login"),
                        rs.getString("password"),
                        birthdayDate,
                        rs.getString("phone_number")
                );
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean registerUser(String first_name, String last_name, String email,
                                String login, String password, LocalDate birthday,
                                String phone_number) {
        String role = "client";
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        String sql = "INSERT INTO users (first_name, last_name, email, login, password, role, birthday, phone_number) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection db = DbConnection.getConnection();
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

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}