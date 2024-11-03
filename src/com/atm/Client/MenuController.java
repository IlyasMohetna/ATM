package com.atm.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import com.atm.Client.OOP.BankAccount;

public class MenuController {

    @FXML
    private Button actionButton1;

    @FXML
    private Button actionButton2;

    @FXML
    private Button actionButton3;

    @FXML
    private Button actionButton7;

    @FXML
    private Button actionButton4;

    @FXML
    private Button actionButton5;

    @FXML
    private Button actionButton6;

    @FXML
    private Button actionButton8;

    private BankAccount bankAccount;

    @FXML
    public void initialize() {
        actionButton1.setOnAction(event -> handleDeposit());
        actionButton2.setOnAction(event -> handleChangePin());
        actionButton3.setOnAction(event -> handleLogout());
        actionButton5.setOnAction(event -> handleWithdraw());
        actionButton6.setOnAction(event -> handleStatement());
        actionButton7.setOnAction(event -> handleBalance());
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    private void handleDeposit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Deposit.fxml"));
            Parent loginRoot = loader.load();

            DepositController depositController = loader.getController();
            depositController.setBankAccount(bankAccount);

            Stage stage = (Stage) actionButton3.getScene().getWindow();

            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleChangePin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/ChangePin.fxml"));
            Parent loginRoot = loader.load();

            ChangePinController changePinController = loader.getController();
            changePinController.setBankAccount(bankAccount);

            Stage stage = (Stage) actionButton3.getScene().getWindow();

            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Login.fxml"));
            Parent loginRoot = loader.load();

            Stage stage = (Stage) actionButton3.getScene().getWindow();

            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleWithdraw() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Withdraw.fxml"));
            Parent loginRoot = loader.load();

            WithdrawController withdrawController = loader.getController();
            withdrawController.setBankAccount(bankAccount);

            Stage stage = (Stage) actionButton3.getScene().getWindow();

            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleStatement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Statement.fxml"));
            Parent statementRoot = loader.load();

            StatementController statementController = loader.getController();
            statementController.setBankAccount(bankAccount);

            Stage statementStage = new Stage();
            statementStage.setScene(new Scene(statementRoot));
            statementStage.setTitle("Statement");
            
            // Show the new window
            statementStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleBalance() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Balance.fxml"));
            Parent loginRoot = loader.load();

            BalanceController balanceController = loader.getController();
            balanceController.setBankAccount(bankAccount);

            Stage stage = (Stage) actionButton3.getScene().getWindow();

            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
