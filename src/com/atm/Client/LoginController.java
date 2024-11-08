package com.atm.Client;

// Importation des bibliothèques JavaFX nécessaires
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

// Importation des classes nécessaires pour la gestion des comptes bancaires et des alertes
import com.atm.OOP.Bank.BankAccountRepository;
import com.atm.OOP.Bank.BankAccountRepositoryImpl;
import com.atm.OOP.Bank.BankAccountService;
import com.atm.Utils.UIAlert;

public class LoginController {

    // Annotation FXML pour lier les éléments de l'interface utilisateur
    @FXML
    private TextField accountNumberField; // Champ de texte pour le numéro de compte

    @FXML
    private PasswordField pinField; // Champ de texte pour le code PIN

    @FXML
    private Button loginButton; // Bouton de connexion

    // Méthode appelée lors de l'initialisation du contrôleur
    @FXML
    public void initialize() {
        // Définir l'action du bouton de connexion pour appeler la méthode handleLogin
        loginButton.setOnAction(event -> handleLogin());
    }

    // Méthode pour obtenir le texte du champ de numéro de compte
    public String getAccountNumberFieldText() {
        return accountNumberField.getText();
    }

    // Méthode pour obtenir le texte du champ de code PIN
    public String getPinFieldText() {
        return pinField.getText();
    }

    // Méthode pour gérer la connexion
    private void handleLogin() {
        // Récupérer les valeurs des champs de texte
        String accountNumber = this.getAccountNumberFieldText();
        String pin = this.getPinFieldText();

        // Créer une instance du dépôt de comptes bancaires et du service de comptes bancaires
        BankAccountRepository repository = new BankAccountRepositoryImpl();
        BankAccountService service = new BankAccountService(repository);

        try {
            // Authentifier l'utilisateur avec le numéro de compte et le code PIN
            service.authenticate(accountNumber, pin);
            // Définir le service de compte bancaire dans le contrôleur de base
            BaseController.setBankAccountService(service);
            // Charger la vue du menu principal après une connexion réussie
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Menu.fxml"));
            Parent loginRoot = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Dashboard");
        } catch (IllegalArgumentException e) {
            // Afficher une alerte d'erreur si les informations d'identification sont incorrectes
            UIAlert.showError("Erreur", e.getMessage(), true);
        } catch (IOException e) {
            // Afficher une alerte d'erreur générale en cas de problème de chargement de la vue
            UIAlert.showGeneralError();
            System.err.println(e);
        } catch (Exception e) {
            // Afficher une alerte d'erreur générale pour toute autre exception
            UIAlert.showGeneralError();
            System.err.println(e);
        }
    }
}
