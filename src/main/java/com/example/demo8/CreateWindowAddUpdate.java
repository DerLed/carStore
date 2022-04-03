package com.example.demo8;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateWindowAddUpdate {
    private FXMLLoader fxmlLoader;
    private Parent root;
    private Stage stage;
    private Scene scene;

    public void createWindowAddUpdate() {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edit-window.fxml"));
//        Parent root = null;
//        try {
//            root = fxmlLoader.load();
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//        Stage stage = new Stage();
//        stage.initModality(Modality.APPLICATION_MODAL);
//        assert root != null;
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
        initWindow();
        stage.setTitle("Добавление записи");
        stage.show();
    }

    public void createWindowAddUpdate(Vehicle selectRowCar) {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edit-window.fxml"));
//        Parent root = null;
//        try {
//            root = fxmlLoader.load();
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//        Stage stage = new Stage();
//        stage.initModality(Modality.APPLICATION_MODAL);
//        assert root != null;
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
        initWindow();

        EditWindowController editWindowController = fxmlLoader.getController();
        editWindowController.setSelectRowCar(selectRowCar);



        stage.setTitle("Редактирование записи");
        stage.show();
    }


    private void initWindow(){
        fxmlLoader = new FXMLLoader(getClass().getResource("edit-window.fxml"));
        root = null;
        try {
            root = fxmlLoader.load();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        assert root != null;
        scene = new Scene(root);
        stage.setScene(scene);
    }
}
