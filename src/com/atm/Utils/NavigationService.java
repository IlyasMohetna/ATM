package com.atm.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Service de navigation pour l'application ATM.
 * Cette classe fournit des méthodes pour naviguer entre différentes scènes (fxml) de l'application.
 */
public class NavigationService {
    // Chemins des fichiers FXML pour les vues client
    public static final String LOGIN = "/fxml/Client/Login.fxml";
    public static final String MENU = "/fxml/Client/Menu.fxml";
    public static final String DEPOSIT = "/fxml/Client/Deposit.fxml";
    public static final String CHANGE_PIN = "/fxml/Client/ChangePin.fxml";
    public static final String WITHDRAW = "/fxml/Client/Withdraw.fxml";
    public static final String STATEMENT = "/fxml/Client/Statement.fxml";
    public static final String BALANCE = "/fxml/Client/Balance.fxml";

    // Chemins des fichiers FXML pour les vues administrateur
    public static final String ANALYTICS = "/fxml/Admin/Analytics.fxml";
    public static final String USERS = "/fxml/Admin/Users.fxml";
    public static final String ADD_USER = "/fxml/Admin/AddUser.fxml";
    public static final String LOGIN_ADMIN = "/fxml/Admin/Login.fxml";

    /**
     * Navigue vers une nouvelle scène en remplaçant la scène actuelle du stage.
     * 
     * @param stage Le stage actuel où la scène doit être affichée.
     * @param fxmlPath Le chemin du fichier FXML de la nouvelle scène.
     */
    public static void navigateTo(Stage stage, String fxmlPath) {
        try {
            // Chargement du fichier FXML
            FXMLLoader loader = new FXMLLoader(NavigationService.class.getResource(fxmlPath));
            Parent root = loader.load();
            // Configuration et affichage de la nouvelle scène
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            // Gestion des exceptions en cas d'erreur de chargement du fichier FXML
            e.printStackTrace();
        }
    }

    /**
     * Ouvre une nouvelle fenêtre modale avec la scène spécifiée.
     * 
     * @param stage Le stage parent de la fenêtre modale.
     * @param fxmlPath Le chemin du fichier FXML de la scène modale.
     */
    public static void openModal(Stage stage, String fxmlPath) {
        try {
            // Chargement du fichier FXML
            FXMLLoader loader = new FXMLLoader(NavigationService.class.getResource(fxmlPath));
            Parent root = loader.load();
            
            // Création et configuration de la nouvelle fenêtre modale
            Stage statementStage = new Stage();
            statementStage.setScene(new Scene(root));
            statementStage.setTitle("Statement");
            
            // Affichage de la fenêtre modale
            statementStage.show();

        } catch (IOException e) {
            // Gestion des exceptions en cas d'erreur de chargement du fichier FXML
            e.printStackTrace();
        }
    }

}
