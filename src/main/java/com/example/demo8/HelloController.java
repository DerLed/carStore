package com.example.demo8;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class HelloController {
    private ObservableList<User> usersData = FXCollections.observableArrayList();
    private ObservableList<Car> carData = FXCollections.observableArrayList();

    @FXML
    private Label welcomeText;

    @FXML
    private TableView<Car> vehicleList;

    @FXML
    private TableColumn<Car, String> brandColumn;
    @FXML
    private TableColumn<Car, String> modelColumn;
    @FXML
    private TableColumn<Car, String> categoryColumn;
    @FXML
    private TableColumn<Car, String> registrationNumberColumn;
    @FXML
    private TableColumn<Car, String> typeVehicleColumn;
    @FXML
    private TableColumn<Car, Integer> yearColumn;
    @FXML
    private TableColumn<Car, Boolean> hasTrailerColumn;

    @FXML
    protected void onHelloButtonClick() {
        try {
            conn.Conn();
            conn.CreateDB();
            conn.WriteDB();
            conn.ReadDB();
            conn.CloseDB();
        } catch (Exception e) {
        }
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void showCell() {
        System.out.println("хочу установить значения ячеек");
    }

    @FXML
    protected void initialize() throws SQLException, ClassNotFoundException {
        initData();

        brandColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("brand"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("model"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("category"));
        registrationNumberColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("registrationNumber"));
        typeVehicleColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("typeVehicle"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("year"));
        hasTrailerColumn.setCellValueFactory(new PropertyValueFactory<Car, Boolean>("hasTrailer"));

        // заполняем таблицу данными
        vehicleList.setItems(carData);

        System.out.println("init");
    }

    // подготавливаем данные для таблицы
    // вы можете получать их с базы данных
    private void initData() throws SQLException, ClassNotFoundException {
        //carData.add(new Car("LADA", "Priora", "Спорткар", "А000АА00", "Легковая", 2008, false));
        conn.Conn();
        carData = conn.ReadDBOut();
        conn.CloseDB();
    }
}