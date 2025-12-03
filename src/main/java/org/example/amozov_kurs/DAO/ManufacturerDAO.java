package org.example.amozov_kurs.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManufacturerDAO {

    public static List<String> getAllManufacturers() {
        List<String> manufacturers = new ArrayList<>();
        String sql = "SELECT name FROM manufacturers ORDER BY name";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                manufacturers.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manufacturers;
    }

    public static Integer getManufacturerIdByName(String name) {
        String sql = "SELECT id_manufacturers FROM manufacturers WHERE name = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_manufacturers");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}