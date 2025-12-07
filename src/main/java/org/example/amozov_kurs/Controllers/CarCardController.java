package org.example.amozov_kurs.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.amozov_kurs.Models.Car;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CarCardController {

    @FXML
    private ImageView carImage;
    @FXML
    private Label titleLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label specsLabel;
    @FXML
    private Button detailsButton;

    private Car car;

    public void setCar(Car car) {
        this.car = car;
        titleLabel.setText(car.getManufacturerName() + " " + car.getModelName() + " " + car.getYear());
        priceLabel.setText(car.getPrice() + " Rubles");
        specsLabel.setText(car.getBodyType() + " • " + car.getEngineType() + " • " + car.getTransmission());
        loadCarImage();
    }

    private void loadCarImage() {
        try {
            String imageName = car.getImagePath();
            if (imageName != null && !imageName.trim().isEmpty()) {
                File file = new File("src/main/resources/org/example/amozov_kurs/image/" + imageName);
                if (file.exists()) {
                    carImage.setImage(new Image(file.toURI().toString()));
                } else {
                    carImage.setImage(null);
                }
            } else {
                carImage.setImage(null);
            }
        } catch (Exception e) {
            carImage.setImage(null);
        }
    }
    @FXML
    private void handleDetails() throws IOException {
        FXMLLoader detailLoader = new FXMLLoader(getClass().getResource("/org/example/amozov_kurs/detail.fxml"));
        Stage detailsStage = new Stage();
        detailsStage.setScene(new Scene(detailLoader.load(), 500, 500));

        DetailController controller = detailLoader.getController();
        controller.setCar(car);

        controller.initialize();

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/amozov_kurs/image/icon.png")));
        detailsStage.getIcons().add(icon);

        detailsStage.initModality(Modality.APPLICATION_MODAL);
        detailsStage.setTitle("Detail car");
        detailsStage.showAndWait();
    }
}