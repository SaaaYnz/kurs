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
import javafx.stage.Stage;
import org.example.amozov_kurs.Models.Car;
import org.example.amozov_kurs.DAO.CarDAO;

import java.io.IOException;

public class AdminController {

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
    private TableView<Car> carTable;

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

    private void loadCarData() {
        carData.setAll(CarDAO.loadCars());
        carTable.setItems(carData);
    }

    private void setupTableColumn() {
        manufactureColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturerName"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        bodyTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bodyType"));
        engineTypeColumn.setCellValueFactory(new PropertyValueFactory<>("engineType"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        transmissionColumn.setCellValueFactory(new PropertyValueFactory<>("transmission"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        imagePathColumn.setCellValueFactory(p -> {
            String url = getClass().getResource("/org/example/amozov_kurs/image/") + p.getValue().getImagePath();
            Image image = new Image(url, 133, 80, false, true, true);
            return new ReadOnlyObjectWrapper<>(new ImageView(image));
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
        editStage.setScene(new Scene(editLoader.load()));

        EditController editController = editLoader.getController();
        editController.setCar(selectedCar);

        editStage.setTitle("Editing car");

        editStage.showAndWait();

        recreateAdminWindow(currentStage);
    }

    private void recreateAdminWindow(Stage oldStage) {
        try {
            oldStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/amozov_kurs/admin-panel.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle("Admin-panel");
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "couldn't update the window");
        }
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/amozov_kurs/edit.fxml"));
        Stage editStage = new Stage();
        editStage.setScene(new Scene(loader.load()));

        editStage.setTitle("Add car");
        editStage.showAndWait();

        loadCarData();
    }

}
