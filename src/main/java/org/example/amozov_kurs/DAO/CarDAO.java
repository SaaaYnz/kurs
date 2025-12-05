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
        String sql = "SELECT c.id_cars, c.id_manufacturers, m.name, c.model, c.body_type, c.year, c.price, c.engine_type, c.transmission, c.image_path FROM cars c LEFT JOIN manufacturers m ON c.id_manufacturers = m.id_manufacturers ORDER BY c.year DESC, c.model";

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
                        rs.getString("transmission"),
                        rs.getString("image_path")
                );
                car.setManufacturerName(rs.getString("name"));

                cars.add(car);
            }
            System.out.println(cars);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    public static boolean updateCar(Car car) {
        String sql = "UPDATE cars SET " +
                "id_manufacturers = ?, " +
                "model = ?, " +
                "body_type = ?, " +
                "year = ?, " +
                "price = ?, " +
                "engine_type = ?, " +
                "transmission = ?, " +
                "image_path = ? " +
                "WHERE id_cars = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, car.getIdManufacturers());
            stmt.setString(2, car.getModelName());
            stmt.setString(3, car.getBodyType());
            stmt.setInt(4, car.getYear());
            stmt.setInt(5, car.getPrice());
            stmt.setString(6, car.getEngineType());
            stmt.setString(7, car.getTransmission());
            stmt.setString(8, car.getImagePath());
            stmt.setInt(9, car.getIdCars());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> getAllImages() {
        List<String> manufacturers = new ArrayList<>();
        String sql = "SELECT DISTINCT image_path FROM cars ORDER BY image_path";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                manufacturers.add(rs.getString("image_path"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manufacturers;
    }

    public static boolean addCar(Car car) {
        String sql = "INSERT INTO cars (id_manufacturers, model, body_type, year, price, engine_type, transmission, image_path) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, car.getIdManufacturers());
            stmt.setString(2, car.getModelName());
            stmt.setString(3, car.getBodyType());
            stmt.setInt(4, car.getYear());
            stmt.setInt(5, car.getPrice());
            stmt.setString(6, car.getEngineType());
            stmt.setString(7, car.getTransmission());
            stmt.setString(8, car.getImagePath());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteCar(Integer carId) {
        String sql = "DELETE FROM cars WHERE id_cars = ?";

        try (Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, carId);
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    public static Car getCarById(int carId) {
        String sql = "SELECT c.*, m.name " +
                "FROM cars c " +
                "JOIN manufacturers m ON c.id_manufacturers = m.id_manufacturers " +
                "WHERE c.id_cars = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, carId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Car car = new Car(
                            rs.getInt("id_cars"),
                            rs.getInt("id_manufacturers"),
                            rs.getString("model"),
                            rs.getString("body_type"),
                            rs.getInt("year"),
                            rs.getInt("price"),
                            rs.getString("engine_type"),
                            rs.getString("transmission"),
                            rs.getString("image_path")
                    );
                    car.setManufacturerName(rs.getString("name"));
                    return car;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Car> searchCars(String query) {
        List<Car> cars = new ArrayList<>();

        String sql = "SELECT c.id_cars, c.id_manufacturers, m.name, " +
                "c.model, c.body_type, c.year, c.price, c.engine_type, c.transmission, c.image_path " +
                "FROM cars c " +
                "LEFT JOIN manufacturers m ON c.id_manufacturers = m.id_manufacturers " +
                "WHERE m.name ILIKE ? OR c.model ILIKE ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + query + "%");
            pstmt.setString(2, "%" + query + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Car car = new Car(
                        rs.getInt("id_cars"),
                        rs.getInt("id_manufacturers"),
                        rs.getString("model"),
                        rs.getString("body_type"),
                        rs.getInt("year"),
                        rs.getInt("price"),
                        rs.getString("engine_type"),
                        rs.getString("transmission"),
                        rs.getString("image_path")
                );

                // Устанавливаем название производителя
                car.setManufacturerName(rs.getString("name"));

                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
}