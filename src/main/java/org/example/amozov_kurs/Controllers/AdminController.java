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
import org.example.amozov_kurs.DAO.DbConnection;
import org.example.amozov_kurs.DAO.OrderDAO;
import org.example.amozov_kurs.Models.Car;
import org.example.amozov_kurs.DAO.CarDAO;
import org.example.amozov_kurs.Models.Order;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Objects;

public class AdminController {

    public Label labelText;
    @FXML
    public TableColumn<Order, Integer> orderIdColumn;

    @FXML
    public TableColumn<Order, String> carNameColumn;

    @FXML
    public TableColumn<Order, String> serviceNameColumn;

    @FXML
    public TableColumn<Order, String> userNameColumn;

    @FXML
    public TableColumn<Order, Date> orderDateColumn;

    @FXML
    public TableColumn<Order, String> statusColumn;

    @FXML
    public Button carsButton;

    @FXML
    public Button orderButton;

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
    public TableView<Order> orderTable;

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

    private ObservableList<Order> orderData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        setupCarTableColumn();
        setupOrderTableColumn();
        loadCarData();
        loadOrderData();

    }

    public void loadCarData() {
        carData.clear();
        carData.addAll(CarDAO.loadCars());
        carTable.setItems(carData);
        carTable.refresh();
    }

    public void loadOrderData() {
        orderData.clear();
        orderData.addAll(OrderDAO.loadOrders());
        orderTable.setItems(orderData);
        orderTable.refresh();
    }


    public void showCarsTable() {
        carTable.setVisible(true);
        carTable.setManaged(true);

        orderTable.setVisible(false);
        orderTable.setManaged(false);

        addButton.setVisible(true);
        deleteButton.setVisible(true);

        carsButton.setStyle("-fx-background-color: #0fb7ff; -fx-background-radius: 15; -fx-text-fill: white");

        orderButton.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: #0fb7ff; -fx-text-fill: #0fb7ff");
    }

    public void showOrdersTable() {
        carTable.setVisible(false);
        carTable.setManaged(false);

        orderTable.setVisible(true);
        orderTable.setManaged(true);

        addButton.setVisible(false);
        deleteButton.setVisible(false);

        carsButton.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: #0fb7ff; -fx-text-fill: #0fb7ff");

        orderButton.setStyle("-fx-background-color: #0fb7ff; -fx-background-radius: 15; -fx-text-fill: white");
    }

    public void setupOrderTableColumn() {
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        carNameColumn.setCellValueFactory(new PropertyValueFactory<>("carName"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void setupCarTableColumn() {

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
        FXMLLoader editLoader = new FXMLLoader(getClass().getResource("/org/example/amozov_kurs/edit.fxml"));
        Parent root = editLoader.load();
        Stage editStage = new Stage();
        editStage.initModality(Modality.APPLICATION_MODAL);

        editStage.centerOnScreen();

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/amozov_kurs/image/icon.png")));
        editStage.getIcons().add(icon);

        EditController editController = editLoader.getController();
        editController.setCar(selectedCar);

        editStage.setTitle("Editing car");
        editStage.setScene(new Scene(root));
        editStage.showAndWait();
        setupCarTableColumn();
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
    private void handleAddManufacture() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/amozov_kurs/add-manufacture.fxml"));
        Stage addStage = new Stage();
        addStage.setScene(new Scene(loader.load()));
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/amozov_kurs/image/icon.png")));
        addStage.getIcons().add(icon);
        addStage.initModality(Modality.APPLICATION_MODAL);
        addStage.setTitle("Add manufacturer");
        addStage.showAndWait();
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
                showAlert("success", "car delete");
            } else {
                showAlert("error", "car not delete");
            }
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }
}