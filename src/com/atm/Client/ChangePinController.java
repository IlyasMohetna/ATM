package com.atm.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

import com.atm.OOP.BankAccount;

public class ChangePinController {
    @FXML
    private Button actionButton4;

    @FXML
    private Button actionButton8;

    @FXML
    private PasswordField actualPIN;

    @FXML
    private PasswordField newPIN;

    @FXML
    private PasswordField confirmNewPIN;

    private BankAccount bankAccount;

    @FXML
    public void initialize() {
        actionButton8.setOnAction(event -> handleChangePin());
        actionButton4.setOnAction(event -> handleBack());
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    private void handleChangePin() {
        String actualPin = actualPIN.getText();
        String newPin = newPIN.getText();
        String confirmNewPin = confirmNewPIN.getText();
    
        try {
            String resultMessage = bankAccount.changePin(actualPin, newPin, confirmNewPin);
            
            if (resultMessage.equals("Votre PIN a été mis à jour avec succès.")) {
                showAlert("Opération réussie", resultMessage);
                handleBack();
            } else {
                showAlert("Opération échouée", resultMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Opération échouée", "Un problème est survenu lors de la mise à jour de votre PIN.");
        }
    }

    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Menu.fxml"));
            Parent loginRoot = loader.load();

            MenuController menuController = loader.getController();
            menuController.setBankAccount(bankAccount);

            Stage stage = (Stage) actionButton4.getScene().getWindow();
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
