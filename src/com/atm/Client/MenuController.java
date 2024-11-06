package com.atm.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController extends BaseController {

    @FXML
    private Button actionButton1;
    @FXML
    private Button actionButton2;
    @FXML
    private Button actionButton3;
    @FXML
    private Button actionButton5;
    @FXML
    private Button actionButton6;
    @FXML
    private Button actionButton7;

    @FXML
    public void initialize() {
        super.initialize();
        actionButton1.setOnAction(event -> handleAction("deposit"));
        actionButton2.setOnAction(event -> handleAction("changePin"));
        actionButton3.setOnAction(event -> handleAction("logout"));
        actionButton5.setOnAction(event -> handleAction("withdraw"));
        actionButton6.setOnAction(event -> handleAction("statement"));
        actionButton7.setOnAction(event -> handleAction("balance"));
    }


}
