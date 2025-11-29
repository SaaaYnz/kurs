package org.example.amozov_kurs.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.example.amozov_kurs.Models.Car;

public class CarCardController {

    @FXML private ImageView carImage;
    @FXML private Label titleLabel;
    @FXML private Label priceLabel;
    @FXML private Label specsLabel;
    @FXML private Button detailsButton;

    private Car car;

    public void setCar(Car car) {
        this.car = car;
        titleLabel.setText(car.getManufacturerName() + " " + car.getModelName() + " " + car.getYear());
        priceLabel.setText((car.getPrice()) + " Р");
        specsLabel.setText(car.getBodyType() + " • " + car.getEngineType() + " • " + car.getTransmission());
    }

    @FXML
    private void handleDetails() {
        System.out.println("Подробнее: " + car.getModelName());
    }
}