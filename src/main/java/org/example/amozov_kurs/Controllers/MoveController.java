package org.example.amozov_kurs.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.amozov_kurs.DAO.ServiceDAO;
import org.example.amozov_kurs.Models.Service;
import org.example.amozov_kurs.Service.OrderService;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;

public class MoveController {

    @FXML
    private ChoiceBox<Service> choiceService;

    @FXML
    private DatePicker datePick;

    @FXML
    private Button saveButton;

    private int carId;
    private int userId;

    public void setData(int carId, int userId) {
        this.carId = carId;
        this.userId = userId;
    }
    ServiceDAO serviceDAO = new ServiceDAO();
    OrderService orderService = new OrderService();

    @FXML
    public void initialize() {

        choiceService.setConverter(new StringConverter<Service>() {
            @Override
            public String toString(Service service) {
                return service != null ? service.getServiceName() : "";
            }

            @Override
            public Service fromString(String string) {
                return null;
            }
        });

        loadServices();

        saveButton.setOnAction(event -> saveOrder());
    }

    private void loadServices() {
        List<Service> services = ServiceDAO.getAllService();
        choiceService.getItems().addAll(services);
    }

    private void saveOrder() {
        Service selectedService = choiceService.getValue();
        LocalDate date = datePick.getValue();

        String result = orderService.addOrder(carId, userId, selectedService, date);
        switch (result) {
            case "ok" -> showAlert("Success", "we will contact you");

            case "past_date" -> showErrorAlert("Error", "the date cannot be earlier in the past");

            case "empty" -> showErrorAlert("Error", "fill in all the fields");

            case "fail" -> showErrorAlert("Error", "couldn't add order");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}