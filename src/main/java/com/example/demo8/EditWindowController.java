package com.example.demo8;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.example.demo8.Settings.PATHSQLDB;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> st = FXCollections.observableArrayList();
        for (TypeVehicle tt : TypeVehicle.values()) {
            st.add(tt.getType());
        }
        addTypeVehicleChoiceBox.setItems(st);
    }

    @FXML
    protected void handleCloseButtonAction() {
       closeWindow();

    }

    @FXML
    protected void addButtonAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

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
        }
        int hasTrailer = hasTrailerCheckBox.isSelected() ? 1 : 0;


        //Добавляем новую запись в БД
        try (Connection connectionDB = DriverManager.getConnection(PATHSQLDB)) {

            String updateSql = "UPDATE vehicle SET brand = ?, model = ?, category = ?, " +
                    "registrationNumber = ?, typeVehicle = ?, year = ?, " +
                    "hasTrailer = ? WHERE id = " + idUpdate + " ;";

            String addSql = "INSERT INTO 'vehicle' ('brand', 'model', 'category', 'registrationNumber', 'typeVehicle', " +
                    "'year', 'hasTrailer') VALUES (?, ?, ?, ?, ?, ?, ? ) ;";

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

            if (!checkRegistrationNumber(registrationNumber) | isUpdate) {
                int rows = preparedStatement.executeUpdate();
                System.out.printf("%d rows added", rows);
            } else
                System.out.println("Номер существует");


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
        try (Connection connectionDB = DriverManager.getConnection(PATHSQLDB);) {
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
//        if(selectRowCar.getYear() == 0) {
//            addYearTextField.setText("");
//        }
//        else
        addYearTextField.setText(String.valueOf(selectRowCar.getYear()));
        addTypeVehicleChoiceBox.setValue(selectRowCar.getTypeVehicle());
    }

    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}

