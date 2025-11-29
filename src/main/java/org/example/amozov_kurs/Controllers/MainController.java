package org.example.amozov_kurs.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.example.amozov_kurs.DAO.CarDAO;
import org.example.amozov_kurs.Models.Car;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private FlowPane catalogFlow;
    @FXML private TextField searchField;

    private List<Car> allCars;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCars();
    }

    private void loadCars() {
        catalogFlow.getChildren().clear();
        allCars = CarDAO.loadCars(); // Ваш метод из CarDAO

        for (Car car : allCars) {
            addCarCard(car);
        }
    }

    private void addCarCard(Car car) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/amozov_kurs/car-card.fxml")
            );
            VBox card = loader.load();
            CarCardController controller = loader.getController();
            controller.setCar(car);
            catalogFlow.getChildren().add(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}