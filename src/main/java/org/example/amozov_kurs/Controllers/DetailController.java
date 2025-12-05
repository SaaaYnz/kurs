package org.example.amozov_kurs.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.amozov_kurs.DAO.CarDAO;
import org.example.amozov_kurs.DAO.ManufacturerDAO;
import org.example.amozov_kurs.Models.Car;
import org.example.amozov_kurs.Models.User;
import org.example.amozov_kurs.Session.AuthSession;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class DetailController {

    @FXML
    private Label bodyTypeLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private ImageView carImagePath;

    @FXML
    private Label detaildLabel;

    @FXML
    private Label engineTypeLabel;

    @FXML
    private Label manuifactureLabel;

    @FXML
    private Label modelLabel;

    @FXML
    private Button moveButton;

    @FXML
    private Label priceLabel;

    @FXML
    private Label transmissionLabel;

    @FXML
    private Label yearLabel;

    private Car currentCar;
    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }
    @FXML
    public void initialize() {
        currentUser = AuthSession.getUser();
    }

    public void setCar(Car car) {
        this.currentCar = car;
        if (car == null) return;

        modelLabel.setText(car.getModelName());
        manuifactureLabel.setText(car.getManufacturerName() + ": ");
        bodyTypeLabel.setText(car.getBodyType());
        yearLabel.setText(String.valueOf(car.getYear()));
        engineTypeLabel.setText(car.getEngineType());
        transmissionLabel.setText(car.getTransmission());
        priceLabel.setText(car.getPrice() + " Rubles");

        if (car.getImagePath() != null && !car.getImagePath().trim().isEmpty()) {
            loadImage(car.getImagePath());
        }
    }


    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void loadImage(String imageName) {
        try {
            if (imageName == null || imageName.trim().isEmpty()) {
                carImagePath.setImage(null);
                return;
            }
            InputStream stream = getClass().getResourceAsStream("/org/example/amozov_kurs/image/" + imageName);
            if (stream != null) {
                Image image = new Image(stream);
                carImagePath.setImage(image);
                stream.close();
            } else {
                File file = new File("src/main/resources/org/example/amozov_kurs/image/" + imageName);
                if (file.exists()) {
                    String uri = file.toURI().toString();
                    Image image = new Image(uri);
                    carImagePath.setImage(image);
                } else {
                    carImagePath.setImage(null);
                }
            }
        } catch (Exception e) {
            carImagePath.setImage(null);
        }
    }
    public void handleMove() throws IOException {
        if (currentUser == null) {
            showError("Ошибка: пользователь не авторизован.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/amozov_kurs/move.fxml"));
        Parent root = loader.load();

        MoveController controller = loader.getController();
        controller.setData(currentCar.getIdCars(), currentUser.getId_users());

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

