package org.example.amozov_kurs.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.amozov_kurs.DAO.EmployeeDAO;
import org.example.amozov_kurs.Models.Employee;

import java.io.IOException;

public class LoginController {

    @FXML
    private CheckBox CheckRemember;

    @FXML
    private TextField LoginField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button LoginButton;

    @FXML
    private Button RegistrationButton;

    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private static Employee currentEmployee;


    @FXML
    public void initialize() {

    }

    @FXML
    private void handleLogin() throws IOException {
        String username = LoginField.getText();
        String password = PasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Fill in all the fields");
            return;
        }

        Employee employee = employeeDAO.validateUser(username, password);

        if (employee != null) {
            currentEmployee = employee;
            openMainWindow();
        } else {
            showAlert("Error", "There is no such user");
        }
    }

    @FXML
    private void handleRegister() throws IOException {
        Stage stage = (Stage) PasswordField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/amozov_kurs/registration.fxml"));
        Parent root = loader.load();
        stage.setTitle("");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void openMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("new-window.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Новое окно");
        stage.setScene(new Scene(root));
        stage.initOwner(LoginButton.getScene().getWindow());
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static Employee getCurrentEmployee() {
        return currentEmployee;
    }
}
