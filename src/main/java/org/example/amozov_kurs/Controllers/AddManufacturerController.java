package org.example.amozov_kurs.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.amozov_kurs.DAO.ManufacturerDAO;
import org.example.amozov_kurs.Service.ManufacturerService;

public class AddManufacturerController {
    @FXML
    private TextField countryField;

    @FXML
    private TextField manufactureField;

    @FXML
    private Button saveButton;

    ManufacturerDAO manufacturerDAO = new ManufacturerDAO();

    private final ManufacturerService manufacturerService = new ManufacturerService();

    @FXML
    private void handleSave() {
        String nameManufacturer = manufactureField.getText();
        String country = countryField.getText();

        String result = manufacturerService.addManufacturer(nameManufacturer, country);
        switch (result) {
            case "ok" -> showAlert("Success", "manufacturer added");

            case "fail" -> showAlert("Error", "couldn't add manufacturer");

            case "empty" -> showAlert("Error", "fill in all the fields");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
