package org.example.amozov_kurs.Controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.example.amozov_kurs.Models.Car;
import org.example.amozov_kurs.DAO.CarDAO;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class AdminController {

    public Label labelText;
    @FXML
    private ContextMenu editContextMenu;

    @FXML
    private MenuItem editItem;

    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    @FXML
    public TableView<Car> carTable;

    @FXML
    private TableColumn<Car, String> manufactureColumn;
    @FXML
    private TableColumn<Car, String> bodyTypeColumn;
    @FXML
    private TableColumn<Car, String> modelColumn;
    @FXML
    private TableColumn<Car, String> engineTypeColumn;
    @FXML
    private TableColumn<Car, ImageView> imagePathColumn;
    @FXML
    private TableColumn<Car, Integer> priceColumn;
    @FXML
    private TableColumn<Car, String> transmissionColumn;
    @FXML
    private TableColumn<Car, Integer> yearColumn;

    private ObservableList<Car> carData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        setupTableColumn();
        loadCarData();


    }

    public void loadCarData() {
        carData.clear();
        carData.addAll(CarDAO.loadCars());
        carTable.setItems(carData);
        carTable.refresh();

    }

    public void setupTableColumn() {

        manufactureColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturerName"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        bodyTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bodyType"));
        engineTypeColumn.setCellValueFactory(new PropertyValueFactory<>("engineType"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        transmissionColumn.setCellValueFactory(new PropertyValueFactory<>("transmission"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        imagePathColumn.setCellValueFactory(p -> {
            String fileName = p.getValue().getImagePath();

            if (fileName != null && !fileName.isEmpty()) {
                try {
                    File file = new File("src/main/resources/org/example/amozov_kurs/image/" + fileName);
                    String uri = file.toURI().toString();
                    Image image = new Image(uri, 133, 80, false, true);
                    return new ReadOnlyObjectWrapper<>(new ImageView(image));
                } catch (Exception e) {

                }
            }
            return new ReadOnlyObjectWrapper<>(new ImageView());
        });
    }

    @FXML
    private void openMainWindow() throws IOException {
        Stage stage = (Stage) carTable.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/amozov_kurs/main-window.fxml"));
        Parent root = loader.load();
        stage.centerOnScreen();
        stage.setTitle("Catalog");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleEditCar() throws IOException {
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();

        Stage currentStage = (Stage) carTable.getScene().getWindow();

        FXMLLoader editLoader = new FXMLLoader(getClass().getResource("/org/example/amozov_kurs/edit.fxml"));
        Stage editStage = new Stage();
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.setScene(new Scene(editLoader.load()));

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/amozov_kurs/image/icon.png")));
        editStage.getIcons().add(icon);

        EditController editController = editLoader.getController();
        editController.setCar(selectedCar);

        editStage.setTitle("Editing car");

        editStage.showAndWait();

        setupTableColumn();
        loadCarData();
        carTable.refresh();
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleAddCar() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/amozov_kurs/add.fxml"));
        Stage addStage = new Stage();
        addStage.setScene(new Scene(loader.load()));
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/amozov_kurs/image/icon.png")));
        addStage.getIcons().add(icon);
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.setTitle("Add car");
        addStage.showAndWait();

        loadCarData();
    }

    @FXML
    private void handleDeleteCar() {
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();

        if (selectedCar == null) {
            return;
        }

        try {
            boolean success = CarDAO.deleteCar(selectedCar.getIdCars());

            if (success) {
                carTable.getItems().remove(selectedCar);
                System.out.println("Автомобиль успешно удален");
            } else {
                System.out.println("Не удалось удалить автомобиль");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при удалении: " + e.getMessage());
        }
    }
}