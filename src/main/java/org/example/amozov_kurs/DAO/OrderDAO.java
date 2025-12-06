package org.example.amozov_kurs.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.amozov_kurs.Models.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderDAO {
    private static final DbConnection dbConnection = new DbConnection();
    public static ObservableList<Order> loadOrders() {
        ObservableList<Order> orders = FXCollections.observableArrayList();

        String sql = "SELECT so.id_service_orders, c.model, u.login, " +
                "s.service_name, so.order_date, so.status " +
                "FROM service_orders so " +
                "JOIN cars c ON so.id_cars = c.id_cars " +
                "JOIN users u ON so.id_users = u.id_users " +
                "JOIN services s ON so.id_services = s.id_services";

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id_service_orders"),
                        rs.getString("model"),
                        rs.getString("login"),
                        rs.getString("service_name"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }
}
