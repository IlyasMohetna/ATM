package com.atm.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class BalanceController extends BaseController {
    // Bouton d'action identifié par l'ID actionButton7 dans le fichier FXML
    @FXML
    private Button actionButton7;

    // Étiquette de texte pour afficher le solde du compte
    @FXML
    private Text balanceLabel;

    // Méthode d'initialisation appelée automatiquement après le chargement du fichier FXML
    @FXML
    public void initialize() {
        // Appel de la méthode d'initialisation de la classe parente
        super.initialize();

        // Mise à jour de l'étiquette de texte avec le solde actuel du compte bancaire
        // Le solde est formaté en euros avec deux décimales
        balanceLabel.setText(String.format("%.2f €", getBankAccountService().getBankAccount().getBalance()));

        // Définition de l'action à effectuer lorsque le bouton est cliqué
        // Ici, l'action "menu" est gérée par la méthode handleAction
        actionButton7.setOnAction(event -> handleAction("menu"));
    }
}
