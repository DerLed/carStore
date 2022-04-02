package com.example.demo8;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateWindowAddUpdate {
    public void createWindowAddUpdate(Car selectRowCar) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edit-window.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        EditWindowController editWindowController = fxmlLoader.getController();
        editWindowController.setSelectRowCar(selectRowCar);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
