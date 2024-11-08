package com.atm.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import com.atm.OOP.Admin.AdminRepository;
import com.atm.OOP.Admin.AdminService;
import com.atm.Utils.UIAlert;

public class LoginController {

    // Champs de texte pour l'email et le mot de passe
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    // Bouton de connexion
    @FXML
    private Button loginButton;

    // Méthode d'initialisation appelée après le chargement du fichier FXML
    @FXML
    public void initialize() {
        // Définir l'action du bouton de connexion pour appeler la méthode handleLogin
        loginButton.setOnAction(event -> handleLogin());
    }

    // Méthode pour gérer la connexion
    private void handleLogin() {
        // Récupérer les valeurs des champs de texte
        String email = emailField.getText();
        String password = passwordField.getText();

        // Créer des instances de AdminRepository et AdminService
        AdminRepository repository = new AdminRepository();
        AdminService service = new AdminService(repository);

        try {
            // Authentifier l'admin avec l'email et le mot de passe fournis
            service.authenticate(email, password);
            // Définir le service admin dans le contrôleur de base
            AdminBaseController.setAdminService(service);
            // Charger la nouvelle scène (tableau de bord) après une connexion réussie
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/Analytics.fxml"));
            Parent loginRoot = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Dashboard");
        } catch (IllegalArgumentException e) {
            // Afficher une erreur si les informations d'identification sont incorrectes
            UIAlert.showError("Erreur", e.getMessage(), true);
        } catch (IOException e) {
            // Afficher une erreur générale en cas de problème de chargement de la scène
            UIAlert.showGeneralError();
            e.printStackTrace();
            System.err.println(e);
        } catch (Exception e) {
            // Afficher une erreur générale pour toute autre exception
            UIAlert.showGeneralError();
            System.err.println(e);
        }
    }
}
