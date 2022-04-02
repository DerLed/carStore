package com.example.demo8;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloController {
//    public Button updateButton;
//    public TextField findBrand;
//    private ObservableList<Car> carData = FXCollections.observableArrayList();
//
//
//    @FXML
//    private TableView<Car> vehicleList;
//
//    @FXML
//    private TableColumn<Car, String> brandColumn;
//    @FXML
//    private TableColumn<Car, String> modelColumn;
//    @FXML
//    private TableColumn<Car, String> categoryColumn;
//    @FXML
//    private TableColumn<Car, String> registrationNumberColumn;
//    @FXML
//    private TableColumn<Car, String> typeVehicleColumn;
//    @FXML
//    private TableColumn<Car, Integer> yearColumn;
//    @FXML
//    private TableColumn<Car, Boolean> hasTrailerColumn;
//
//
//
//    @FXML
//    protected void onHelloButtonClick() throws SQLException, ClassNotFoundException {
//        String fs = findBrand.getText();
//        vehicleList.setItems(conn.Finder(fs));
//
//        vehicleList.refresh();
//
//    }
//
//    @FXML
//    protected void showCell() {
//        System.out.println("хочу установить значения ячеек");
//    }
//
//    @FXML
//    protected void initialize() throws SQLException, ClassNotFoundException {
//        initData();
//
//        brandColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("brand"));
//        modelColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("model"));
//        categoryColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("category"));
//        registrationNumberColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("registrationNumber"));
//        typeVehicleColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("typeVehicle"));
//        yearColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("year"));
//        hasTrailerColumn.setCellValueFactory(new PropertyValueFactory<Car, Boolean>("hasTrailer"));
//
//        // заполняем таблицу данными
//        conn.Conn();
//        conn.CreateDB();
////        conn.ReadDB();
//        conn.WriteDB();
////        vehicleList.setItems(carData);
//        vehicleList.setItems(conn.ReadDBOut());
////        vehicleList.setItems(conn.Finder("Оп"));
////        conn.CloseDB();
//
//        System.out.println("init");
//    }
//
//    // подготавливаем данные для таблицы
//    // вы можете получать их с базы данных
//    private void initData() throws SQLException, ClassNotFoundException {
//        carData.add(new Car("LADA", "Priora", "Спорткар", "А000АА00", "Легковая", 2008, false));
////        conn.Conn();
////        carData = conn.ReadDBOut();
////        conn.CloseDB();
//    }
}