package org.example.amozov_kurs.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.amozov_kurs.Models.User;
import org.example.amozov_kurs.DAO.UserDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class RegistrationController {

    @FXML
    public TextField InnField;

    @FXML
    public TextField PassportField;

    @FXML
    private TextField FirstNameField;

    @FXML
    private TextField LastNameField;

    @FXML
    private TextField EmailField;

    @FXML
    private TextField LoginField;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private PasswordField ConfirmPasswordField;

    @FXML
    private Button RegistrButton;

    @FXML
    private Button ReturnButton;

    @FXML
    private DatePicker datePick;

    private UserDAO userDAO = new UserDAO();

    @FXML
    private void handleRegister() throws IOException {
        String firstName = FirstNameField.getText();
        String lastName = LastNameField.getText();
        String email = EmailField.getText();
        String inn = InnField.getText();
        String passport = PassportField.getText();
        String login = LoginField.getText();
        String password = PasswordField.getText();
        String phone = phoneField.getText();
        String confirmPassword = ConfirmPasswordField.getText();
        LocalDate birthday = datePick.getValue();

        try {
            UserDAO.checkUniqueEmail(email);
        } catch (SQLException e) {
            if (e.getMessage().contains("unique") || e.getMessage().contains("duplicate")) {
                showError("Error", "this email exists");
            } else {
                showError("Error", "this email exists");
            }
            return;
        }

        try {
            UserDAO.checkUniqueInn(inn);
        } catch (SQLException e) {
            if (e.getMessage().contains("unique") || e.getMessage().contains("duplicate")) {
                showError("Error", "this inn exists");
            } else {
                showError("Error", "this inn exists");
            }
            return;
        }
        try {
            UserDAO.checkUniquePassport(passport);
        } catch (SQLException e) {
            if (e.getMessage().contains("unique") || e.getMessage().contains("duplicate")) {
                showError("Error", "this passport exists");
            } else {
                showError("Error", "this passport exists");
            }
            return;
        }
        try {
            UserDAO.checkUniqueLogin(login);
        } catch (SQLException e) {
            if (e.getMessage().contains("unique") || e.getMessage().contains("duplicate")) {
                showError("Error", "this login exists");
            } else {
                showError("Error", "this login exists");
            }
            return;
        }

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                login.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || birthday == null) {
            showAlert("Error", "Fill in all the fields");
            return;
        }

        if (!firstName.matches("^[А-ЯЁа-яёA-Za-z\\s'-]{1,40}$") || !lastName.matches("^[А-ЯЁа-яёA-Za-z\\s'-]{1,40}$")) {
            showAlert("Error", "Invalid name");
            return;
        }

        if (!email.matches("^[A-Za-z0-9._%+-]{1,64}@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            showAlert("Error", "Invalid email");
            return;
        }

        if (!login.matches("^[a-zA-Z0-9._-]{3,20}$")) {
            showAlert("Error", "Invalid login (3-20 chars: a-z, 0-9, ., -, _)");
            return;
        }

        if (!phone.isEmpty() && !phone.matches("^\\+7-\\d{3}-\\d{3}-\\d{2}-\\d{2}$")) {
            showAlert("Error", "Invalid phone format: +7-***-***-**-**");
            return;
        }

        if (!inn.matches("^[1-9]\\d{11}$")) {
            showAlert("Error", "Invalid inn");
            return;
        }
        if (!passport.matches("^([1-9]|9[1-9])\\d{9}$")) {
            showAlert("Error", "Invalid passport");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Password and Confirm Password do not match");
            return;
        }

        if (birthday != null && birthday.isAfter(LocalDate.now())) {
            showAlert("Error", "Birthday cannot be in the future");
            return;
        }

        if (!isAdult(birthday)) {
            showAlert("Error", "You must be at least 16 years old to register");
            return;
        }

        boolean registered = userDAO.registerUser(firstName, lastName, email, login, password, birthday, phone, inn, passport);
        if (registered) {
            showAlert("Success", "Registration successful!");
            openLoginWindow();
        } else {
            showAlert("Error", "Registration failed, please try again");
        }
    }

    @FXML
    private void handleReturn() throws IOException {
        Stage stage = (Stage) FirstNameField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/amozov_kurs/login.fxml"));
        Parent root = loader.load();
        stage.centerOnScreen();
        stage.setTitle("Authorization");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void openLoginWindow() throws IOException {
        Stage stage = (Stage) FirstNameField.getScene().getWindow();
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

    private void showError (String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isAdult(LocalDate birthday) {
        LocalDate today = LocalDate.now();
        LocalDate adultDate = birthday.plusYears(16);
        return !adultDate.isAfter(today);
    }
}