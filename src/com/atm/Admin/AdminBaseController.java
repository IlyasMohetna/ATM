package com.atm.Admin;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import com.atm.OOP.Admin.AdminService;
import com.atm.Utils.NavigationService;

public abstract class AdminBaseController {
    // Annotation FXML pour lier l'élément Parent défini dans le fichier FXML
    @FXML
    private Parent root;

    // Annotation FXML pour lier les boutons définis dans le fichier FXML
    @FXML
    private Button dashboardButton;

    @FXML
    private Button usersButton;

    @FXML
    private Button addUserButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button btnSignout;

    // Service administrateur partagé entre les instances de contrôleurs
    public static AdminService adminService;

    // Méthode appelée automatiquement après le chargement du fichier FXML
    @FXML
    public void initialize() {
        // Initialisation des gestionnaires d'événements pour les boutons du menu
        // addMenuEventHandlers();
    }

    // Méthode statique pour définir le service administrateur
    public static void setAdminService(AdminService service) {
        adminService = service;
    }

    // Méthode statique pour obtenir le service administrateur
    public static AdminService getAdminService() {
        return adminService;
    }

    // Méthode protégée pour gérer les actions des boutons du menu
    protected void handleAction(String action) {
        switch (action) {
            case "analytics":
                // Navigation vers la page des analyses
                NavigationService.navigateTo(getStage(), NavigationService.ANALYTICS);
                break;
            case "users":
                // Navigation vers la page des utilisateurs
                NavigationService.navigateTo(getStage(), NavigationService.USERS);
                break;
            case "addUser":
                // Navigation vers la page d'ajout d'utilisateur
                NavigationService.navigateTo(getStage(), NavigationService.ADD_USER);
                break;
            case "logout":
                // Navigation vers la page de connexion administrateur
                NavigationService.navigateTo(getStage(), NavigationService.LOGIN_ADMIN);
                break;
            default:
                // Message d'erreur pour une action inconnue
                System.err.println("Action inconnue");
        }
    }
    
    // Méthode protégée pour obtenir la fenêtre (Stage) actuelle
    protected Stage getStage() {
        // Retourne la fenêtre actuelle si root n'est pas null
        return root != null ? (Stage) root.getScene().getWindow() : null;
    }
}
