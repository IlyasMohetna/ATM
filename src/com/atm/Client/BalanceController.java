package com.atm.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class BalanceController extends BaseController{
    @FXML
    private Button actionButton7;

    @FXML
    private Text balanceLabel;

    @FXML
    public void initialize() {
        super.initialize();

        balanceLabel.setText(String.format("%.2f €", getBankAccountService().getBankAccount().getBalance()));
        actionButton7.setOnAction(event -> handleAction("menu"));
    }
}
