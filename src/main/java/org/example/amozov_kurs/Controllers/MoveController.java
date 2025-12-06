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

        if (selectedService == null || date == null) {
            showAlert("Error", "fill all fields");
            return;
        }
        if (date.isBefore(LocalDate.now())) {
            ServiceDAO.addOrder(
                    carId,
                    userId,
                    selectedService.getIdService(),
                    date
            );
        showAlert("Order", "we will contact you");
        closeWindow();
        } else {
            showErrorAlert("Error", "the date cannot be earlier in the past");
        }


    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
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