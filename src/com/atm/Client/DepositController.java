package com.atm.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import com.atm.Client.OOP.BankAccount;

public class DepositController {
    @FXML
    private Button actionButton7;

    @FXML
    private Button actionButton8;

    @FXML
    private TextField depositAmount;

    private BankAccount bankAccount;

    @FXML
    public void initialize() {
        actionButton7.setOnAction(event -> handleDeposit());
        actionButton8.setOnAction(event -> handleBack());
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    private void handleDeposit() {
        double amount = Double.parseDouble(depositAmount.getText());
        try{
            boolean deposit = bankAccount.deposit(amount);
            if(deposit){
                showAlert("Opération réussie", "Vitre dépôt de "+amount+"€ a effectué avec succès ! Votre nouveau solde a été mis à jour.");
                handleBack();
            } else {
                showAlert("Opération échouée", "Deposit failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
