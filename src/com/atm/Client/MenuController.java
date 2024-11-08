package com.atm.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Contrôleur pour la gestion du menu principal de l'application ATM.
 * Cette classe gère les interactions avec les boutons du menu et hérite de BaseController.
 */
public class MenuController extends BaseController {

    // Déclaration des boutons de l'interface utilisateur avec l'annotation @FXML
    // pour permettre leur liaison avec le fichier FXML
    @FXML
    private Button actionButton1;  // Bouton pour le dépôt d'argent
    @FXML
    private Button actionButton2;  // Bouton pour le changement de PIN
    @FXML
    private Button actionButton3;  // Bouton pour la déconnexion
    @FXML
    private Button actionButton5;  // Bouton pour le retrait d'argent
    @FXML
    private Button actionButton6;  // Bouton pour l'affichage du relevé
    @FXML
    private Button actionButton7;  // Bouton pour la consultation du solde

    /**
     * Méthode d'initialisation appelée automatiquement après le chargement du FXML.
     * Configure les gestionnaires d'événements pour chaque bouton du menu.
     */
    @FXML
    public void initialize() {
        super.initialize();  // Appel de l'initialisation de la classe parente
        
        // Configuration des actions pour chaque bouton
        // Utilisation des expressions lambda pour définir le comportement
        actionButton1.setOnAction(event -> handleAction("deposit"));    // Gestion du dépôt
        actionButton2.setOnAction(event -> handleAction("changePin"));  // Gestion du changement de PIN
        actionButton3.setOnAction(event -> handleAction("logout"));     // Gestion de la déconnexion
        actionButton5.setOnAction(event -> handleAction("withdraw"));   // Gestion du retrait
        actionButton6.setOnAction(event -> handleAction("statement"));  // Gestion de l'affichage du relevé
        actionButton7.setOnAction(event -> handleAction("balance"));    // Gestion de la consultation du solde
    }
}
