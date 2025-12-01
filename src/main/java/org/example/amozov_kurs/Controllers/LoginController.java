package org.example.amozov_kurs.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.amozov_kurs.DAO.UserDAO;
import org.example.amozov_kurs.Models.User;

import java.io.IOException;
import java.util.Objects;

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

    private UserDAO userDAO = new UserDAO();
    private static User currentUser;

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

        User user = userDAO.validateUser(username, password);

        if (user != null) {
            currentUser = user;
            redirectBasedOnRole(user.getRole());
        } else {
            showAlert("Error", "There is no such user");
        }
    }

    private void redirectBasedOnRole(String role) throws IOException {
        Stage stage = (Stage) PasswordField.getScene().getWindow();
        Parent root;
        String title;
        switch (role.toLowerCase()) {
            case "admin":
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/amozov_kurs/main-window.fxml")));
                title = "Admin Panel";
                break;
            case "employee":
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/amozov_kurs/main-window.fxml")));
                title = "Consultant Panel";
                break;
            case "client":
            default:
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/amozov_kurs/main-window.fxml")));
                title = "Client Panel";
                break;
        }

        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleRegister() throws IOException {
        Stage stage = (Stage) PasswordField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/amozov_kurs/registration.fxml"));
        Parent root = loader.load();
        stage.setTitle("Registration");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static String getCurrentUserRole() {
        return currentUser != null ? currentUser.getRole() : null;
    }
}