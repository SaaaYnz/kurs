package org.example.amozov_kurs.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.amozov_kurs.DAO.ManufacturerDAO;

public class AddManufacturerController {
    @FXML
    private TextField countryField;

    @FXML
    private TextField manufactureField;

    @FXML
    private Button saveButton;

    ManufacturerDAO manufacturerDAO = new ManufacturerDAO();

    @FXML
    private void handleSave() {
        String nameManufacturer = manufactureField.getText();
        String country = countryField.getText();

        if(nameManufacturer.isEmpty() || country.isEmpty()) {
            showAlert("Error", "fill in all the fields");
            return;
        }
        boolean manufacturer =  manufacturerDAO.addManufacture(nameManufacturer, country);
        if (manufacturer) {
            showAlert("Success", "manufacture added");
        } else {
            showAlert("Error", "failed");
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
