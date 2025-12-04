package org.example.amozov_kurs.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.amozov_kurs.Models.Car;
import org.example.amozov_kurs.DAO.CarDAO;
import org.example.amozov_kurs.DAO.ManufacturerDAO;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class EditController {

    @FXML private ImageView carImagePath;
    @FXML private ChoiceBox<String> manufactureChoice;
    @FXML private ChoiceBox<String> imageChoice;
    @FXML private TextField modelField;
    @FXML private TextField bodyTypeField;
    @FXML private TextField yearField;
    @FXML private TextField engineTypeField;
    @FXML private TextField transmissionField;
    @FXML private TextField priceField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private Car currentCar;
    private String selectedImagePath;

    @FXML
    public void initialize() {
        manufactureChoice.getItems().addAll(ManufacturerDAO.getAllManufacturers());
        imageChoice.getItems().addAll(CarDAO.getAllImages());

        imageChoice.setOnAction(event -> handleImageChoice());
    }

    @FXML
    private void handleImageChoice() {
        String selectedImageName = imageChoice.getValue();

        if (selectedImageName != null && !selectedImageName.isEmpty()) {

            loadImage(selectedImageName);

            selectedImagePath = selectedImageName;
        }
    }

    public void setCar(Car car) {
        this.currentCar = car;

        if (car != null) {
            manufactureChoice.setValue(car.getManufacturerName());
            modelField.setText(car.getModelName());
            bodyTypeField.setText(car.getBodyType());
            yearField.setText(String.valueOf(car.getYear()));
            engineTypeField.setText(car.getEngineType());
            transmissionField.setText(car.getTransmission());
            priceField.setText(String.valueOf(car.getPrice()));

            // Устанавливаем изображение в ChoiceBox если оно есть
            if (car.getImagePath() != null) {
                imageChoice.setValue(car.getImagePath());
                loadImage(car.getImagePath());
                selectedImagePath = car.getImagePath();
            }
        }
    }

    @FXML
    private void handleSave() {
        Integer manufacturerId = ManufacturerDAO.getManufacturerIdByName(manufactureChoice.getValue());
        Car updatedCar = new Car(
                currentCar.getIdCars(),
                manufacturerId,
                modelField.getText(),
                bodyTypeField.getText(),
                Integer.parseInt(yearField.getText()),
                Integer.parseInt(priceField.getText()),
                engineTypeField.getText(),
                transmissionField.getText(),
                selectedImagePath != null ? selectedImagePath : currentCar.getImagePath()
        );
        //System.out.println(selectedImagePath);
        boolean success = CarDAO.updateCar(updatedCar);
        if (success) {
            closeWindow();
//            System.out.println);
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    @FXML
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Change image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Изображения", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) carImagePath.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                Image preview = new Image(selectedFile.toURI().toString());
                carImagePath.setImage(preview);

                selectedImagePath = copyImageToProject(selectedFile);


            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                showAlert("Error", "Couldn't save image");
            }
        }
    }

    private String copyImageToProject(File sourceFile) throws IOException {
        String projectImageDir = "src/main/resources/org/example/amozov_kurs/image/";

        String originalName = sourceFile.getName();
        String fileName = originalName;
        File destination = new File(projectImageDir + fileName);

        int counter = 1;
        while (destination.exists()) {
            String baseName = originalName.substring(0, originalName.lastIndexOf('.'));
            String extension = originalName.substring(originalName.lastIndexOf('.'));
            fileName = baseName + "_" + counter + extension;
            destination = new File(projectImageDir + fileName);
            counter++;
        }

        Files.copy(sourceFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);



        return fileName;
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}