package com.example.carStore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.example.carStore.Settings.PATHSQLDB;

public class EditWindowController implements Initializable {

    public Button closeButton;

    public TextField addBrandTextField;
    public TextField addModelTextField;
    public TextField addCategoryTextField;
    public TextField addRegistrationNumberTextField;
    public ChoiceBox<String> addTypeVehicleChoiceBox;
    public TextField addYearTextField;
    public CheckBox hasTrailerCheckBox;

    private boolean isUpdate = false;
    private int idUpdate;
    String sql = "";

    /**
     *В инициализирующем методе окна
     * производится заполнение выпадающего списка категорий значениями
     * из enum с категориями
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> st = FXCollections.observableArrayList();
        for (TypeVehicle tv : TypeVehicle.values()) {
            st.add(tv.getType());
        }
        addTypeVehicleChoiceBox.setItems(st);
    }

    /**
     * Обработчик кнопки закрытия окна
     */
    @FXML
    protected void handleCloseButtonAction() {
       closeWindow();
    }

    @FXML
    protected void addButtonAction(){

        //Получаем значения из текстовых полей
        String brand = addBrandTextField.getText();
        String model = addModelTextField.getText();
        String category = addCategoryTextField.getText();
        String registrationNumber = addRegistrationNumberTextField.getText();
        String typeVehicle = addTypeVehicleChoiceBox.getValue();

        //Проверяем что в поле "Год выпуска" введено число
        int year = 0;
        try {
            year = Integer.parseInt(addYearTextField.getText());
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            //вывод исключения  не производится
            //правильно бы было обработать событие и не дать сохранить такую запись
        }
        int hasTrailer = hasTrailerCheckBox.isSelected() ? 1 : 0;


        //Добавляем или обновляем запись в БД
        //Обновление производится по первичному ключу
        //сложение строк плохая практикаб, но в данном случает оставил так
        try (Connection connectionDB = DriverManager.getConnection(PATHSQLDB)) {

            String updateSql = "UPDATE vehicle SET brand = ?, model = ?, category = ?, " +
                    "registrationNumber = ?, typeVehicle = ?, year = ?, " +
                    "hasTrailer = ? WHERE id = " + idUpdate + " ;";

            String addSql = "INSERT INTO 'vehicle' ('brand', 'model', 'category', 'registrationNumber', 'typeVehicle', " +
                    "'year', 'hasTrailer') VALUES (?, ?, ?, ?, ?, ?, ? ) ;";
            //Если окно запущено для редактирования или добавления и выбирается соответствующий SQL запрос
            if (isUpdate)
                sql = updateSql;
            else
                sql = addSql;

            PreparedStatement preparedStatement = connectionDB.prepareStatement(sql);
            preparedStatement.setString(1, brand);
            preparedStatement.setString(2, model);
            preparedStatement.setString(3, category);
            preparedStatement.setString(4, registrationNumber);
            preparedStatement.setString(5, typeVehicle);
            preparedStatement.setInt(6, year);
            preparedStatement.setInt(7, hasTrailer);
            //Проверка совпадения регистрационного знака
            //Если окно запущено для редактирования то проверка регистрационного знака не производится
            if (!checkRegistrationNumber(registrationNumber) | isUpdate) {
                preparedStatement.executeUpdate();
            } else
                System.out.println("Номер существует");
                //Вывод в консоль для отладки
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeWindow();

    }

    /**
     * Функция проверки совпадения регистрационного номера
     */
    private boolean checkRegistrationNumber(String registrationNumber) {
        boolean isRegistrationNumberExist = false;
        try (Connection connectionDB = DriverManager.getConnection(PATHSQLDB)) {
            String sql = "SELECT registrationNumber FROM vehicle WHERE registrationNumber = ?;";
            PreparedStatement preparedStatement = connectionDB.prepareStatement(sql);
            preparedStatement.setString(1, registrationNumber);
            ResultSet queryOutput = preparedStatement.executeQuery();
            if (queryOutput.next()) {
                isRegistrationNumberExist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isRegistrationNumberExist;
    }

    /**
     * Функция для передачи значения между контроллерами(окнами)
     * выполняется только при редактировании записи,
     * вызывается в обработчике кнопки "Редактировать запись"
     */
    public void setSelectRowCar(Vehicle selectRowCar) {
        //Устанавливаем переменную isUpdate в true если передана какая либо запись на редактиорвание
        //И получаем id записи которую будем редактировать
        if (selectRowCar.getId() > 0) isUpdate = true;
        idUpdate = selectRowCar.getId();
        //Заполняем все поля формы полученными значениями
        addBrandTextField.setText(selectRowCar.getBrand());
        addModelTextField.setText(selectRowCar.getModel());
        addCategoryTextField.setText(selectRowCar.getCategory());
        addRegistrationNumberTextField.setText(selectRowCar.getRegistrationNumber());
        hasTrailerCheckBox.setSelected(selectRowCar.isHasTrailer());
        addYearTextField.setText(String.valueOf(selectRowCar.getYear()));
        addTypeVehicleChoiceBox.setValue(selectRowCar.getTypeVehicle());
    }

    /**
     * Метод закрытия окна
     */
    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}

