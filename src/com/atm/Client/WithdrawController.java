package com.atm.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

import com.atm.Utils.UIAlert;

public class WithdrawController extends BaseController {
    // Bouton pour effectuer l'action de retrait
    @FXML
    private Button actionButton7;

    // Bouton pour retourner au menu
    @FXML
    private Button actionButton8;

    // Champ de texte pour entrer le montant à retirer
    @FXML
    private TextField withdrawAmount;

    // Méthode d'initialisation appelée après le chargement du fichier FXML
    @FXML
    public void initialize() {
        super.initialize();

        // Associe l'action de retrait au bouton actionButton7
        actionButton7.setOnAction(event -> handleWithdraw());
        // Associe l'action de retour au menu au bouton actionButton8
        actionButton8.setOnAction(event -> handleAction("menu"));
    }

    // Méthode pour gérer l'action de retrait
    private void handleWithdraw() {
        // Récupère le montant à retirer depuis le champ de texte
        double amount = Double.parseDouble(withdrawAmount.getText());
    
        try {
            // Tente de retirer le montant spécifié du compte bancaire
            getBankAccountService().withdraw(amount);
            // Affiche un message de succès si le retrait est effectué avec succès
            UIAlert.showSuccess("Opération réussie", "Votre retrait de " + String.format("%.2f", amount) + "€ a été effectué avec succès ! Votre nouveau solde a été mis à jour.", false);
            // Retourne au menu après un retrait réussi
            handleAction("menu");
        } catch (IllegalArgumentException e) {
            // Affiche un message d'avertissement si le montant est invalide
            UIAlert.showWarning("Erreur", e.getMessage(), true);
        } catch (IOException e) {
            // Affiche un message d'erreur en cas de problème de mise à jour du solde
            e.printStackTrace();
            UIAlert.showError("Erreur", "Une erreur s'est produite lors de la mise à jour de votre solde. Veuillez réessayer plus tard.", true);
        } catch (Exception e) {
            // Affiche un message d'erreur général pour toute autre exception
            e.printStackTrace();
            UIAlert.showGeneralError();
        }
    }

}
