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
import java.util.List;

public class AddController {

    @FXML private ImageView carImagePath;
    @FXML private ChoiceBox<String> manufactureChoice;
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
    private File selectedImageFile;  // Добавьте это поле
    private Image selectedImage;

    @FXML
    private String selectedImageName = ""; // Инициализируем пустой строкой

    @FXML
    public void initialize() {
        List<String> manufacturers = ManufacturerDAO.getAllManufacturers();
        manufactureChoice.getItems().addAll(manufacturers);
        if (!manufacturers.isEmpty()) {
            manufactureChoice.setValue(manufacturers.get(0));
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

            if (car.getImagePath() != null) {
                loadImage(car.getImagePath());
            }
        }
    }

    @FXML
    private void handleSave() {
        if (modelField.getText().isEmpty() || yearField.getText().isEmpty() || priceField.getText().isEmpty()) {
            showAlert("Error", "Fill required fields");
            return;
        }

        Integer manufacturerId = ManufacturerDAO.getManufacturerIdByName(manufactureChoice.getValue());
        if (manufacturerId == null) return;

        try {
            Car newCar = new Car(
                    null,
                    manufacturerId,
                    modelField.getText(),
                    bodyTypeField.getText(),
                    Integer.parseInt(yearField.getText()),
                    Integer.parseInt(priceField.getText()),
                    engineTypeField.getText(),
                    transmissionField.getText(),
                    selectedImageName.isEmpty() ? null : selectedImageName // Если пусто - null, иначе имя файла
            );

            System.out.println("Saving car with image: " + selectedImageName); // Для отладки

            boolean success = CarDAO.addCar(newCar);
            if (success) {
                showAlert("Success", "Car added successfully");
                closeWindow();
            } else {
                showAlert("Error", "Failed to add car");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Error: " + e.getMessage());
        }
    }
    private String saveImageToProject() throws IOException {
        if (selectedImage == null) {
            return null;
        }

        // Создаем уникальное имя файла
        String fileName = "car_" + System.currentTimeMillis() + ".png";
        String projectImageDir = "src/main/resources/org/example/amozov_kurs/image/";

        // Создаем папку если нет
        File dir = new File(projectImageDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fullPath = projectImageDir + fileName;

        // Сохраняем изображение (в реальном приложении нужна более сложная логика)
        // Здесь просто возвращаем имя файла, так как мы уже скопировали файл в chooseImage()
        return fileName;
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
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) carImagePath.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                Image preview = new Image(selectedFile.toURI().toString());
                carImagePath.setImage(preview);

                // Сохраняем имя файла
                selectedImageName = copyImageToProject(selectedFile);
                System.out.println("Image saved as: " + selectedImageName);

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
            InputStream stream = getClass().getResourceAsStream("/org/example/amozov_kurs/image/" + imageName);
            if (stream != null) {
                Image image = new Image(stream);
                carImagePath.setImage(image);
            }
        } catch (Exception e) {
            System.out.println("Couldn't save image: " + imageName);
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