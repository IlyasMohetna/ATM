package com.atm.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

import com.atm.Utils.UIAlert;

public class WithdrawController extends BaseController {
    @FXML
    private Button actionButton7;

    @FXML
    private Button actionButton8;

    @FXML
    private TextField withdrawAmount;

    @FXML
    public void initialize() {
        super.initialize();

        actionButton7.setOnAction(event -> handleWithdraw());
        actionButton8.setOnAction(event -> handleAction("menu"));
    }

    private void handleWithdraw() {
        double amount = Double.parseDouble(withdrawAmount.getText());
    
        try {
            getBankAccountService().withdraw(amount);
            UIAlert.showSuccess("Opération réussie", "Votre retrait de " + String.format("%.2f", amount) + "€ a été effectué avec succès ! Votre nouveau solde a été mis à jour.", false);
            handleAction("menu");
        } catch (IllegalArgumentException e) {
            UIAlert.showWarning("Erreur", e.getMessage(), true);
        } catch (IOException e) {
            e.printStackTrace();
            UIAlert.showError("Erreur", "Une erreur s'est produite lors de la mise à jour de votre solde. Veuillez réessayer plus tard.", true);
        } catch (Exception e) {
            e.printStackTrace();
            UIAlert.showGeneralError();
        }
    }

}
