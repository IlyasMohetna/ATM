package com.atm.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

import com.atm.Utils.UIAlert;

public class DepositController extends BaseController {
    @FXML
    private Button actionButton7;

    @FXML
    private Button actionButton8;

    @FXML
    private TextField depositAmount;

    @FXML
    public void initialize() {
        super.initialize();
        actionButton7.setOnAction(event -> handleDeposit());
        actionButton8.setOnAction(event -> handleAction("menu"));
    }

    private void handleDeposit() {
        String amountText = depositAmount.getText().trim();
    
        if (amountText.isEmpty()) {
            UIAlert.showWarning("Erreur de saisie", "Veuillez entrer un montant pour le dépôt.", true);
            return;
        }
    
        try {
            double amount = Double.parseDouble(amountText);
    
            getBankAccountService().deposit(amount);
   
            UIAlert.showSuccess("Opération réussie", "Votre dépôt de " + String.format("%.2f", amount) + "€ a été effectué avec succès ! Votre nouveau solde a été mis à jour.", false);
            handleAction("menu");
    
        } catch (NumberFormatException e) {
            UIAlert.showWarning("Erreur", "Veuillez entrer un montant numérique valide.", true);
        } catch (IllegalArgumentException e) {
            UIAlert.showError("Erreur", e.getMessage(), true);
        } catch (IOException e) {
            e.printStackTrace();
            UIAlert.showError("Erreur", "Une erreur s'est produite lors de la mise à jour de votre solde. Veuillez réessayer plus tard.", true);
        } catch (Exception e) {
            e.printStackTrace();
            UIAlert.showGeneralError();
        }
    }
}
