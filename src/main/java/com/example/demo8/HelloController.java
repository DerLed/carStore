package com.example.demo8;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        try {
            conn.Conn();
            conn.CreateDB();
            conn.WriteDB();
            conn.ReadDB();
            conn.CloseDB();
        }
        catch (Exception e){

        }
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void initialize(){

        System.out.println("init");
    }
}