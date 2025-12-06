
package org.example.amozov_kurs.DAO;

import org.example.amozov_kurs.Models.Manufacturer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManufacturerDAO {

    private static final DbConnection dbConnection = new DbConnection();
    public static List<String> getAllManufacturers() {
        List<String> manufacturers = new ArrayList<>();
        String sql = "SELECT name FROM manufacturers ORDER BY name";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                manufacturers.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return manufacturers;
    }

    public static boolean addManufacture(String manufacture_name, String country) {
        String sql = "insert into manufacturers (name, country) values (?, ?)";

        try (Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, manufacture_name);
            stmt.setString(2, country);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer getManufacturerIdByName(String name) {
        String sql = "SELECT id_manufacturers FROM manufacturers WHERE name = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_manufacturers");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
