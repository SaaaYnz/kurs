package org.example.amozov_kurs.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.amozov_kurs.DAO.DbConnection;

import java.io.IOException;

public class DbSettingsController {

    @FXML
    private Button saveButton;
    @FXML
    private Button returnButton;
    @FXML
    private TextField HOST;
    @FXML
    private TextField PORT;
    @FXML
    private TextField DB_NAME;
    @FXML
    private TextField SCHEMA;
    @FXML
    private TextField LOGIN;
    @FXML
    private PasswordField PASS;

    private final DbConnection dbConnection = new DbConnection();

    @FXML
    public void initialize() {
        HOST.setText(dbConnection.getHost());
        PORT.setText(dbConnection.getPort());
        DB_NAME.setText(dbConnection.getDbName());
        SCHEMA.setText(dbConnection.getSchema());
        LOGIN.setText(dbConnection.getLogin());
        PASS.setText(dbConnection.getPassword());
    }

    @FXML
    private void save() {
        try {
            dbConnection.saveSettings(
                    HOST.getText(),
                    PORT.getText(),
                    DB_NAME.getText(),
                    SCHEMA.getText(),
                    LOGIN.getText(),
                    PASS.getText()
            );
            showAlert("Success", "data updated");
        } catch (Exception e) {
            showAlert("Error", "data not saved");
            e.printStackTrace();
        }
    }

    @FXML
    private void backToLogin() throws IOException {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/amozov_kurs/login.fxml"));
        Parent root = loader.load();
        stage.centerOnScreen();
        stage.setTitle("Authorization");
        stage.setScene(new Scene(root));
        stage.show();
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

