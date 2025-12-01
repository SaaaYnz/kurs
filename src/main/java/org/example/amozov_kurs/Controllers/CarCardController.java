package org.example.amozov_kurs.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.amozov_kurs.Models.Car;

import java.awt.*;
import java.io.InputStream;

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
        priceLabel.setText(car.getPrice() + " Р");
        specsLabel.setText(car.getBodyType() + " • " + car.getEngineType() + " • " + car.getTransmission());
        loadCarImage();
    }

    private void loadCarImage() {
        try {
            String imageName = car.getImagePath();

            if (imageName != null && !imageName.trim().isEmpty()) {
                String imagePath = "/org/example/amozov_kurs/image/" + imageName;

                InputStream stream = getClass().getResourceAsStream(imagePath);
                if (stream != null) {
                    Image image = new Image(stream);
                    carImage.setImage(image);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    @FXML
    private void handleDetails() {

    }
}