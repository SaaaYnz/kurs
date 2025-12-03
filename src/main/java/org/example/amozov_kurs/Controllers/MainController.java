package org.example.amozov_kurs.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.amozov_kurs.DAO.CarDAO;
import org.example.amozov_kurs.Models.Car;
import org.example.amozov_kurs.Models.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    public Button filterButton;
    @FXML
    private Button accountButton;

    @FXML
    private Button adminButton;

    @FXML
    private FlowPane catalogFlow;

    @FXML
    private TextField searchField;

    private List<Car> allCars;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User currentUser = LoginController.getCurrentUser();
        if (currentUser != null && "admin".equalsIgnoreCase(currentUser.getRole())) {
            adminButton.setVisible(true);
        } else {
            adminButton.setVisible(false);
        }
        loadCars();
    }

    private void loadCars() {
        catalogFlow.getChildren().clear();
        allCars = CarDAO.loadCars();

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

    @FXML
    private void openAdminPanel() throws IOException {
        Stage stage = (Stage) searchField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/amozov_kurs/admin-panel.fxml"));
        Parent root = loader.load();
        stage.centerOnScreen();
        stage.setTitle("Admin-panel");
        stage.setScene(new Scene(root));
        stage.show();
    }
}