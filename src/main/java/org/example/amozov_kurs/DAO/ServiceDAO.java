package org.example.amozov_kurs.DAO;

import org.example.amozov_kurs.Models.Car;
import org.example.amozov_kurs.Models.Service;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {
    private static final DbConnection dbConnection = new DbConnection();
    public static List<Service> getAllService() {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT id_services, service_name FROM services ORDER BY service_name";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_services");
                String name = rs.getString("service_name");
                services.add(new Service(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return services;
    }

    public boolean addOrder(int idCar,
                                int idUser,
                                int idService,
                                LocalDate orderDate) {

        String sql = "INSERT INTO service_orders (id_cars, order_date, status, id_users, id_services) " +
                "VALUES (?, ?, 'create', ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCar);
            ps.setDate(2, java.sql.Date.valueOf(orderDate));
            ps.setInt(3, idUser);
            ps.setInt(4, idService);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении заказа: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
