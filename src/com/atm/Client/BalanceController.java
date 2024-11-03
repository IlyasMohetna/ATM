package com.atm.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import com.atm.Client.OOP.BankAccount;

public class BalanceController {
    @FXML
    private Button actionButton7;

    @FXML
    private Label balanceLabel;

    private BankAccount bankAccount;

    @FXML
    public void initialize() {
        actionButton7.setOnAction(event -> handleBack());
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        balanceLabel.setText(String.format("%.2f €", bankAccount.getBalance()));
    }

    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Menu.fxml"));
            Parent loginRoot = loader.load();

            MenuController menuController = loader.getController();
            menuController.setBankAccount(bankAccount);

            Stage stage = (Stage) actionButton7.getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Dashboard");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
