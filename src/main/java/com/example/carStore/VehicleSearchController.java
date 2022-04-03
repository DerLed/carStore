package com.example.carStore;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.sql.*;

import static com.example.carStore.Settings.PATHSQLDB;


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
    FilteredList<Vehicle> filteredCarData = new FilteredList<>(vehicleData);

    //Переменная для хранения выбраной строки
    private Vehicle selectRowCar;

    private FXMLLoader fxmlLoader;
    private Stage stage;

    public static Connection connectionDB;
    public static Statement statement;
    public static ResultSet queryOutput;




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

    /**
     * Обработчик нажатия кнопки добавления новой записи
     */
    @FXML
    protected void addButtonClick() {
        createWindowAddUpdate();
    }

    /**
     * Обработчик нажатия кнопки редактирования
     */
    @FXML
    protected void updateButtonClick() {
        createWindowAddUpdate(selectRowCar);
    }

    @FXML
    public void initialize() {
        //Обновление записей при первом запуске приложения
        updateDB();

        //Заполнение стлобцов таблице получеными значениями
        for (Vehicle v : vehicleData){
            brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
            modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            registrationNumberColumn.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
            typeVehicleColumn.setCellValueFactory(new PropertyValueFactory<>("typeVehicle"));
            yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
            //Здесь для для удобного чтения данных в таблице булевые преобразуются в соответсвующие строковые значения
            hasTrailerColumn.setCellValueFactory(cellData -> {
                boolean isHasTrailer = cellData.getValue().isHasTrailer();
                String hasTrailerAsString;
                if (isHasTrailer) hasTrailerAsString = "Да";
                else hasTrailerAsString = "Нет";
                return new ReadOnlyStringWrapper(hasTrailerAsString);
            });
        }

        findValue();

        //Выбор значения в таблице
        //запускается "слушатель" выбранной строки в таблице
        //при каждом новом выборе строки записывает данные строки в переменную
        //или очищает ее если ничего не выбрано
        //для того что бы при попытке редактирования, если ничего не выбрано не было ошибок
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

    /**
     * Метод инициализирует новое окно для редактирования или добаления нового ТС
     * вынесен отдельно для возможности перегрузки метода запуска окна отдельно
     * для редактирования и отдельно для добавления
     * Так же в методе запускается событие на закрытие дочернего окна
     * после закрытия дочернего окна, обновляются данные для отображения в таблице
     * и каждый раз запускается метод для осуществления поиска
     */
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
            updateDB();
            findValue();
        });
    }

    /**
     * Метод запускает окно добавления записи
     */
    public void createWindowAddUpdate() {
        initWindow();
        stage.setTitle("Добавление записи");
        stage.show();
    }

    /**
     *Метод запускает окно редактирования записи
     * принимает переменную хранящую данные выбраной строки в даблице
     */
    public void createWindowAddUpdate(Vehicle selectRowCar) {
        initWindow();
        EditWindowController editWindowController = fxmlLoader.getController();
        editWindowController.setSelectRowCar(selectRowCar);
        stage.setTitle("Редактирование записи");
        stage.show();
    }

    /**
     * Функция подключается к базе данных и обновляет каждый раз все значения
     * в списке vehicleData
     */
    private void updateDB(){
        ObservableList<Vehicle> carData = FXCollections.observableArrayList();

        String sqlRequest = "SELECT * FROM vehicle";
        try {
            connectionDB = null;
            Class.forName("org.sqlite.JDBC");
            connectionDB = DriverManager.getConnection(PATHSQLDB);
            statement = connectionDB.createStatement();
            statement.execute("CREATE TABLE if not exists 'vehicle' ('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'brand' VARCHAR(50), 'model' VARCHAR(50), 'category' VARCHAR(50), 'registrationNumber' VARCHAR(15)," +
                "'typeVehicle' VARCHAR(15), 'year' INT, hasTrailer INT(1));");
            queryOutput = statement.executeQuery(sqlRequest);

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
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connectionDB.close();
            statement.close();
            queryOutput.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        vehicleData = carData;
    }

    /**
     * Фунция обработчик для поиска значений в таблице
     * связывает значения введные в поля и обновляет список данных
     * для года и категории поиск начинается с начального вхожения
     * для остальных полей поиск по любому вхождению в строке
     */
    public void findValue(){
        //Поиск по колонкам
        filteredCarData = new FilteredList<>(vehicleData);

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
    }
}
