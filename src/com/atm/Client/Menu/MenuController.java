package com.atm.Client.Menu;

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
    private Button actionButton4;
    private Button actionButton5;
    private Button actionButton6;
    private Button actionButton7;
    private Button actionButton8;

    private BankAccount bankAccount;

    @FXML
    public void initialize() {
        actionButton1.setOnAction(event -> handleDepot());
        actionButton3.setOnAction(event -> handleLogout());
    }

    public MenuController() {

    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    private void handleDepot() {
        System.out.println("Solde");
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
}
