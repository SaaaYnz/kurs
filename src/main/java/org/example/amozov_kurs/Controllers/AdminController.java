package org.example.amozov_kurs.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.amozov_kurs.Models.Car;

public class AdminController {

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
    private TableColumn<Car, String> imagePathColumn;
    @FXML
    private TableColumn<Car, Integer> priceColumn;
    @FXML
    private TableColumn<Car, String> transmissionColumn;
    @FXML
    private TableColumn<Car, Integer> yearColumn;

    @FXML
    public void initialize() {
        setupTableColumn();
    }

    private void setupTableColumn() {
        manufactureColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        bodyTypeColumn.setCellValueFactory(new PropertyValueFactory<>("body_type"));
        engineTypeColumn.setCellValueFactory(new PropertyValueFactory<>("engine_type"));
        imagePathColumn.setCellValueFactory(new PropertyValueFactory<>("image_path"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        transmissionColumn.setCellValueFactory(new PropertyValueFactory<>("transmission"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

    }
}
