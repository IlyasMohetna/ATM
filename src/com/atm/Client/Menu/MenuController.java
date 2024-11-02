package com.atm.Client.Menu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button actionButton1;
    private Button actionButton2;
    private Button actionButton3;
    private Button actionButton4;
    private Button actionButton5;
    private Button actionButton6;
    private Button actionButton7;
    private Button actionButton8;

    @FXML
    public void initialize() {
        actionButton1.setOnAction(event -> handleDepot());
    }

    public MenuController() {
        
    }

    private void handleDepot()
    {
        System.out.println("Solde");
    }
}
