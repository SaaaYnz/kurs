package org.example.amozov_kurs.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.amozov_kurs.Models.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderDAO {
    public void loadOrders() {
        ObservableList<Order> orders = FXCollections.observableArrayList();

        String sql = "SELECT so.id, c.model AS car_name, u.username AS user_name, " +
                "s.name AS service_name, so.order_date, so.status " +
                "FROM service_orders so " +
                "JOIN cars c ON so.id_cars = c.id " +
                "JOIN users u ON so.id_users = u.id " +
                "JOIN services s ON so.id_services = s.id";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getString("car_name"),
                        rs.getString("user_name"),
                        rs.getString("service_name"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
