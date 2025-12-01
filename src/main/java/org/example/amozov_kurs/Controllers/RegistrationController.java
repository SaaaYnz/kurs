package org.example.amozov_kurs.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.amozov_kurs.Models.User;
import org.example.amozov_kurs.DAO.UserDAO;

import java.io.IOException;

public class RegistrationController {





    @FXML
    private TextField FirstNameField;

    @FXML
    private TextField LastNameField;

    @FXML
    private TextField EmailField;

    @FXML
    private TextField LoginField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private PasswordField ConfirmPasswordField;

    @FXML
    private Button RegistrButton;

    @FXML
    private Button ReturnButton;

    private UserDAO userDAO = new UserDAO();
    private static User currentUser;


    @FXML
    private void handleRegister() throws IOException {
        String firstName = FirstNameField.getText();
        String lastName = LastNameField.getText();
        String email = EmailField.getText();
        String login = LoginField.getText();
        String password = PasswordField.getText();
        String confirmPassword = ConfirmPasswordField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || login.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "Fill in all the fields");
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Password and Confirm Password do not match");
            return;
        }

        boolean registered = userDAO.registerUser(firstName, lastName, email, login, password);
        if (registered) {
            showAlert("Success", "Registration successful!");
            openMainWindow();
        } else {
            showAlert("Error", "Registration failed, please try again");
        }

    }

    @FXML
    private void handleReturn() throws IOException {
        Stage stage = (Stage) FirstNameField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/amozov_kurs/login.fxml"));
        Parent root = loader.load();
        stage.setTitle("");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void openMainWindow() throws IOException {
        Stage stage = (Stage) FirstNameField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/amozov_kurs/main-window.fxml"));
        Parent root = loader.load();
        stage.setTitle("Новое окно");
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
