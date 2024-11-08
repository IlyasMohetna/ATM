package com.atm.Client;

// Importation des bibliothèques nécessaires
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;

import com.atm.OOP.Bank.BankAccountService;
import com.atm.Utils.NavigationService;
import com.atm.Utils.SoundService;

public abstract class BaseController {

    // Annotation FXML pour l'injection de dépendance
    @FXML
    private Parent root;

    // Service de compte bancaire partagé entre les contrôleurs
    public static BankAccountService bankAccountService;

    // Méthode statique pour définir le service de compte bancaire
    public static void setBankAccountService(BankAccountService service) {
        bankAccountService = service;
    }

    // Méthode statique pour obtenir le service de compte bancaire
    public static BankAccountService getBankAccountService() {
        return bankAccountService;
    }

    // Méthode d'initialisation appelée automatiquement après le chargement du FXML
    @FXML
    public void initialize() {
        // Initialisation du service de son
        SoundService.initializeSound();
        // Ajout de sons aux boutons présents dans la scène
        addSoundToButtons(root);
    }

    // Méthode pour obtenir la fenêtre (Stage) actuelle
    protected Stage getStage() {
        return root != null ? (Stage) root.getScene().getWindow() : null;
    }

    // Méthode récursive pour ajouter des sons aux boutons
    private void addSoundToButtons(Node node) {
        // Si le nœud est un bouton, ajouter un gestionnaire d'événements pour jouer un son
        if (node instanceof Button) {
            Button button = (Button) node;
            button.addEventHandler(ActionEvent.ACTION, event -> {
                SoundService.playButtonSound();
            });
        }

        // Si le nœud est un parent, appliquer récursivement la méthode aux enfants
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            for (Node child : parent.getChildrenUnmodifiable()) {
                addSoundToButtons(child);
            }
        }
    }

    // Méthode pour gérer les actions en fonction de la chaîne d'action reçue
    protected void handleAction(String action) {
        switch (action) {
            case "menu":
                // Navigation vers le menu principal
                NavigationService.navigateTo(getStage(), NavigationService.MENU);
                break;
            case "deposit":
                // Navigation vers la page de dépôt
                NavigationService.navigateTo(getStage(), NavigationService.DEPOSIT);
                break;
            case "changePin":
                // Navigation vers la page de changement de code PIN
                NavigationService.navigateTo(getStage(), NavigationService.CHANGE_PIN);
                break;
            case "logout":
                // Navigation vers la page de connexion (déconnexion)
                NavigationService.navigateTo(getStage(), NavigationService.LOGIN);
                break;
            case "withdraw":
                // Navigation vers la page de retrait
                NavigationService.navigateTo(getStage(), NavigationService.WITHDRAW);
                break;
            case "statement":
                // Ouverture d'une fenêtre modale pour l'état de compte
                NavigationService.openModal(getStage(), NavigationService.STATEMENT);
                break;
            case "balance":
                // Navigation vers la page de solde
                NavigationService.navigateTo(getStage(), NavigationService.BALANCE);
                break;
            default:
                // Message d'erreur pour une action inconnue
                System.err.println("Action inconnue");
        }
    }
    
}
