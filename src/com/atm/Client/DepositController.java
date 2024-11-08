package com.atm.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

import com.atm.Utils.UIAlert;

public class DepositController extends BaseController {
    // Bouton pour effectuer le dépôt
    @FXML
    private Button actionButton7;

    // Bouton pour retourner au menu
    @FXML
    private Button actionButton8;

    // Champ de texte pour entrer le montant du dépôt
    @FXML
    private TextField depositAmount;

    // Méthode d'initialisation appelée après le chargement du fichier FXML
    @FXML
    public void initialize() {
        super.initialize(); // Appel de la méthode d'initialisation de la classe parente
        // Définir l'action du bouton de dépôt
        actionButton7.setOnAction(event -> handleDeposit());
        // Définir l'action du bouton de retour au menu
        actionButton8.setOnAction(event -> handleAction("menu"));
    }

    // Méthode pour gérer le dépôt
    private void handleDeposit() {
        // Récupérer et nettoyer le texte du champ de dépôt
        String amountText = depositAmount.getText().trim();
    
        // Vérifier si le champ de dépôt est vide
        if (amountText.isEmpty()) {
            // Afficher un avertissement si le champ est vide
            UIAlert.showWarning("Erreur de saisie", "Veuillez entrer un montant pour le dépôt.", true);
            return;
        }
    
        try {
            // Convertir le texte en double
            double amount = Double.parseDouble(amountText);
    
            // Effectuer le dépôt via le service de compte bancaire
            getBankAccountService().deposit(amount);
   
            // Afficher un message de succès
            UIAlert.showSuccess("Opération réussie", "Votre dépôt de " + String.format("%.2f", amount) + "€ a été effectué avec succès ! Votre nouveau solde a été mis à jour.", false);
            // Retourner au menu après le dépôt
            handleAction("menu");
    
        } catch (NumberFormatException e) {
            // Afficher un avertissement si le montant n'est pas un nombre valide
            UIAlert.showWarning("Erreur", "Veuillez entrer un montant numérique valide.", true);
        } catch (IllegalArgumentException e) {
            // Afficher une erreur si le montant est invalide selon les règles de l'application
            UIAlert.showError("Erreur", e.getMessage(), true);
        } catch (IOException e) {
            // Afficher une erreur en cas de problème d'entrée/sortie
            e.printStackTrace();
            UIAlert.showError("Erreur", "Une erreur s'est produite lors de la mise à jour de votre solde. Veuillez réessayer plus tard.", true);
        } catch (Exception e) {
            // Afficher une erreur générale pour toute autre exception
            e.printStackTrace();
            UIAlert.showGeneralError();
        }
    }
}
