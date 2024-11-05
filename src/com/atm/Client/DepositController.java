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

import com.atm.OOP.BankAccount;

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
        String amountText = depositAmount.getText().trim();
    
        if (amountText.isEmpty()) {
            showAlert("Erreur de saisie", "Veuillez entrer un montant pour le dépôt.");
            return;
        }
    
        try {
            double amount = Double.parseDouble(amountText);
    
            String result = bankAccount.deposit(amount);
    
            if (result.equals("Dépôt réussi")) {
                showAlert("Opération réussie", "Votre dépôt de " + String.format("%.2f", amount) + "€ a été effectué avec succès ! Votre nouveau solde a été mis à jour.");
                handleBack();
            } else {
                showAlert("Opération échouée", result);
            }
    
        } catch (NumberFormatException e) {
            showAlert("Erreur de format", "Veuillez entrer un montant numérique valide.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur de système", "Une erreur s'est produite lors de la mise à jour de votre solde. Veuillez réessayer plus tard.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur inconnue", "Une erreur inattendue s'est produite. Veuillez contacter le support si le problème persiste.");
        }
    }

    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Menu.fxml"));
            Parent loginRoot = loader.load();

            MenuController menuController = loader.getController();
            menuController.setBankAccount(bankAccount);

            /// Get actual
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
