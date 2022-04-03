package com.example.demo8;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.example.demo8.Settings.PATHSQLDB;


public class VehicleSearchController {
    @FXML
    public TextField findBrandTextField;
    @FXML
    public TextField findModelTextField;
    @FXML
    public TextField findRegistrationNumberTextField;
    @FXML
    public TextField findCategoryTextField;
    @FXML
    public TextField findYearTextField;
    @FXML
    public Button updateButton;

    ObservableList<Vehicle> vehicleData = FXCollections.observableArrayList();

    //Переменная для хранения выбраной строки
    private Vehicle selectRowCar;

    private FXMLLoader fxmlLoader;
    private Stage stage;




    @FXML
    private TableView<Vehicle> vehicleList = new TableView<>(vehicleData);
    @FXML
    private TableColumn<Vehicle, String> brandColumn;
    @FXML
    private TableColumn<Vehicle, String> modelColumn;
    @FXML
    private TableColumn<Vehicle, String> categoryColumn;
    @FXML
    private TableColumn<Vehicle, String> registrationNumberColumn;
    @FXML
    private TableColumn<Vehicle, String> typeVehicleColumn;
    @FXML
    private TableColumn<Vehicle, Integer> yearColumn;
    @FXML
    private TableColumn<Vehicle, String> hasTrailerColumn;


    @FXML
    protected void addButtonClick() {
        createWindowAddUpdate();

    }

    @FXML
    protected void updateButtonClick() {
        createWindowAddUpdate(selectRowCar);
    }

    @FXML
    public void initialize() {
        updateDB();

        for (Vehicle v : vehicleData){
            brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
            modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            registrationNumberColumn.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
            typeVehicleColumn.setCellValueFactory(new PropertyValueFactory<>("typeVehicle"));
            yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
            hasTrailerColumn.setCellValueFactory(cellData -> {
                boolean isHasTrailer = cellData.getValue().isHasTrailer();
                String hasTrailerAsString;
                if (isHasTrailer) hasTrailerAsString = "Да";
                else hasTrailerAsString = "Нет";
                return new ReadOnlyStringWrapper(hasTrailerAsString);
            });
        }

        //Поиск по колонкам
        FilteredList<Vehicle> filteredCarData = new FilteredList<>(vehicleData);

        filteredCarData.predicateProperty().bind(Bindings.createObjectBinding(() ->
                        Car -> Car.getBrand().toLowerCase().contains(findBrandTextField.getText().toLowerCase())
                                && Car.getModel().toLowerCase().contains(findModelTextField.getText().toLowerCase())
                                && Car.getCategory().toLowerCase().startsWith(findCategoryTextField.getText().toLowerCase())
                                && Car.getRegistrationNumber().toLowerCase().contains(findRegistrationNumberTextField.getText().toLowerCase())
                                && String.valueOf(Car.getYear()).startsWith(findYearTextField.getText()),

                findBrandTextField.textProperty(),
                findModelTextField.textProperty(),
                findCategoryTextField.textProperty(),
                findRegistrationNumberTextField.textProperty(),
                findYearTextField.textProperty()
        ));

        vehicleList.setItems(filteredCarData);


        //Выбор значения в таблице

        vehicleList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {

                    if (newValue == null) {
                        updateButton.setDisable(true);
                        selectRowCar = null;
                    } else {
                        updateButton.setDisable(false);
                        selectRowCar = newValue;
                        System.out.println(newValue.getModel());

                    }
                });
    }

    private void initWindow() {
        fxmlLoader = new FXMLLoader(getClass().getResource("edit-window.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        assert root != null;
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setOnHiding(e -> {
            //initialize();
            updateDB();
            //vehicleList.refresh();
            vehicleList.setItems(vehicleData);
        });
    }

    public void createWindowAddUpdate() {
        initWindow();
        stage.setTitle("Добавление записи");
        stage.show();
    }

    public void createWindowAddUpdate(Vehicle selectRowCar) {
        initWindow();
        EditWindowController editWindowController = fxmlLoader.getController();
        editWindowController.setSelectRowCar(selectRowCar);
        stage.setTitle("Редактирование записи");
        stage.show();
    }

    private void updateDB(){
        ObservableList<Vehicle> carData = FXCollections.observableArrayList();

        String sqlRequest = "SELECT * FROM vehicle";
        try (Connection connectionDB = DriverManager.getConnection(PATHSQLDB)) {

            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(sqlRequest);

            while (queryOutput.next()) {
                int id = queryOutput.getInt("id");
                String brand = queryOutput.getString("brand");
                String model = queryOutput.getString("model");
                String category = queryOutput.getString("category");
                String registrationNumber = queryOutput.getString("registrationNumber");
                TypeVehicle typeVehicle = TypeVehicle.getTypeByName(queryOutput.getString("typeVehicle"));
                int year = queryOutput.getInt("year");
                boolean hasTrailer = queryOutput.getInt("hasTrailer") == 1;

                VehicleFactory vehicleFactory = new VehicleFactory();
                carData.add(vehicleFactory.createVehicle(typeVehicle, id, brand, model, category, registrationNumber, year, hasTrailer));

//                brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
//                modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
//                categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
//                registrationNumberColumn.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
//                typeVehicleColumn.setCellValueFactory(new PropertyValueFactory<>("typeVehicle"));
//                yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
//                hasTrailerColumn.setCellValueFactory(cellData -> {
//                    boolean isHasTrailer = cellData.getValue().isHasTrailer();
//                    String hasTrailerAsString;
//                    if (isHasTrailer) hasTrailerAsString = "Да";
//                    else hasTrailerAsString = "Нет";
//                    return new ReadOnlyStringWrapper(hasTrailerAsString);
//                });

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        vehicleData = carData;
    }
}
