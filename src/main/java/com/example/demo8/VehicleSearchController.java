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


public class VehicleSearchController implements Initializable {

    public TextField findBrandTextField;
    public TextField findModelTextField;
    public TextField findRegistrationNumberTextField;
    public TextField findCategoryTextField;
    public TextField findYearTextField;
    public Button updateButton;
    private static Vehicle selectRowCar;


    @FXML
    private TableView<Vehicle> vehicleList;
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
    //private TableColumn<Car, Boolean> hasTrailerColumn;
    private TableColumn<Car, String> hasTrailerColumn;

    private ObservableList<Vehicle> carData = FXCollections.observableArrayList();

    @FXML
    protected void addButtonClick() {
        CreateWindowAddUpdate newWindow = new CreateWindowAddUpdate();
        newWindow.createWindowAddUpdate();

    }

    @FXML
    protected void updateButtonClick() {
        CreateWindowAddUpdate newWindow = new CreateWindowAddUpdate();
        newWindow.createWindowAddUpdate(selectRowCar);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String sqlRequest = "SELECT * FROM vehicle";


        try {
            //Class.forName("org.sqlite.JDBC");
            Connection connectionDB = DriverManager.getConnection(PATHSQLDB);
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(sqlRequest);

            while (queryOutput.next()) {
                Integer id = queryOutput.getInt("id");
                String brand = queryOutput.getString("brand");
                String model = queryOutput.getString("model");
                String category = queryOutput.getString("category");
                String registrationNumber = queryOutput.getString("registrationNumber");
                TypeVehicle typeVehicle = TypeVehicle.getTypeByName(queryOutput.getString("typeVehicle"));
                int year = queryOutput.getInt("year");
                boolean hasTrailer = queryOutput.getInt("hasTrailer") == 1;

                //carData.add(new Car(brand, model, category, registrationNumber, year, hasTrailer));
                VehicleFactory vehicleFactory = new VehicleFactory();
                carData.add(vehicleFactory.createVehicle(typeVehicle, brand, model, category, registrationNumber, year, hasTrailer));

                brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
                modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
                categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
                registrationNumberColumn.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
                typeVehicleColumn.setCellValueFactory(new PropertyValueFactory<>("typeVehicle"));

                yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
//
                hasTrailerColumn.setCellValueFactory(cellData -> {
                    boolean isHasTrailer = cellData.getValue().isHasTrailer();
                    String hasTrailerAsString;
                    if (isHasTrailer) hasTrailerAsString = "Да";
                    else hasTrailerAsString = "Нет";
                    return new ReadOnlyStringWrapper(hasTrailerAsString);
                });

                vehicleList.setItems(carData);
            }

//            FilteredList<Car> filteredCarData = new FilteredList<>(carData, b -> true);
            FilteredList<Vehicle> filteredCarData = new FilteredList<>(carData);

//            findBrandTextField.textProperty().addListener((observable, oldValue, newValue) ->{
//                filteredCarData.setPredicate(Car -> {
//                    if(newValue.isEmpty() || newValue.isBlank()){
//                        return true;
//                    }
//                    String searchKeyword = newValue.toLowerCase();
//                    return Car.getBrand().toLowerCase().contains(searchKeyword);
//                });
//            });

//            findModelTextField.textProperty().addListener((observable, oldValue, newValue) ->{
//                filteredCarData.setPredicate(Car -> {
//                    if(newValue.isEmpty() || newValue.isBlank()){
//                        return true;
//                    }
//                    String searchKeyword = newValue.toLowerCase();
//                    return Car.getModel().toLowerCase().contains(searchKeyword);
//                });
//            });




            //Поиск по колонкам
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

        } catch (SQLException e){
            e.printStackTrace();
        }

        //Выбор значения в таблице
        vehicleList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {

                    if (newValue == null) {
                        updateButton.setDisable(true);
                        selectRowCar = null;
                    }
                    else {
                        updateButton.setDisable(false);
                        selectRowCar = newValue;
                        System.out.println(newValue.getModel());
                    }
                });
    }
}
