package org.example.amozov_kurs.DAO;

import org.example.amozov_kurs.Models.Car;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    public static List<Car> loadCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT c.id_cars, c.id_manufacturers, m.name, c.model, c.body_type, c.year, c.price, c.engine_type, c.transmission FROM cars c LEFT JOIN manufacturers m ON c.id_manufacturers = m.id_manufacturers ORDER BY c.year DESC, c.model";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Car car = new Car(
                        rs.getInt("id_cars"),
                        rs.getInt("id_manufacturers"),
                        rs.getString("model"),
                        rs.getString("body_type"),
                        rs.getInt("year"),
                        rs.getInt("price"),
                        rs.getString("engine_type"),
                        rs.getString("transmission")

                );
                car.setManufacturerName(rs.getString("name"));

                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }
}
