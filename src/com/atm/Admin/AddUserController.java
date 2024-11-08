package com.atm.Admin;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

import com.atm.OOP.Bank.BankAccount;
import com.atm.OOP.Bank.BankAccountRepository;
import com.atm.OOP.Bank.BankAccountRepositoryImpl;
import com.atm.Utils.UIAlert;

public class AddUserController extends AdminBaseController {

    // Champs de texte pour les informations de l'utilisateur
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField accountNumberField;

    @FXML
    private TextField pinField;

    @FXML
    private TextField balanceField;

    // Boutons pour générer un PIN et ajouter un compte
    @FXML
    private Button generatePinButton;

    @FXML 
    private Button addAccountButton;

    // Référentiel pour gérer les comptes bancaires
    BankAccountRepository repository = new BankAccountRepositoryImpl();

    // Méthode d'initialisation appelée automatiquement après le chargement du FXML
    @FXML
    public void initialize() {
        super.initialize();

        // Définir les actions des boutons
        generatePinButton.setOnAction(event -> setGeneratedPin());
        addAccountButton.setOnAction(event -> handleAddAccount());

        // Générer un PIN et un numéro de compte par défaut
        setGeneratedPin();
        setGeneratedAccountNumber();
    }

    // Méthode pour gérer l'ajout d'un compte
    private void handleAddAccount() {
        // Récupérer les valeurs des champs de texte
        String firstname = firstNameField.getText();
        String lastname = lastNameField.getText();
        String accountNumber = accountNumberField.getText();
        String pin = pinField.getText();
        double initialBalance;

        try {
            // Convertir le solde initial en double
            initialBalance = Double.parseDouble(balanceField.getText());
            // Créer un nouveau compte bancaire
            BankAccount newAccount = new BankAccount(accountNumber, pin, firstname, lastname, initialBalance);
            // Sauvegarder le compte dans le référentiel
            repository.save(newAccount);
            // Afficher un message de succès
            UIAlert.showSuccess("Succès", "Le compte a été créé avec succès.", false);
            // Effacer le formulaire
            clearForm();
            // Gérer l'action après l'ajout du compte
            handleAction("users");
        } catch (NumberFormatException e) {
            // Afficher une erreur si le solde initial est invalide
            UIAlert.showError("Erreur", "Balance initiale invalide.", true);
        } catch (IOException e) {
            // Afficher une erreur en cas de problème lors de la création du compte
            showAlert("Erreur", "Une erreur est survenue lors de la création du compte.");
            UIAlert.showError("Erreur", "Une erreur est survenue lors de la création du compte.", true);
            e.printStackTrace();
        }
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour effacer le formulaire
    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        balanceField.clear();
    }

    // Méthode pour générer et définir un PIN aléatoire
    private void setGeneratedPin() {
        pinField.setText(generateRandomPin());
    }

    // Méthode pour générer et définir un numéro de compte unique
    private void setGeneratedAccountNumber() {
        accountNumberField.setText(generateUniqueAccountNumber());
    }

    // Méthode pour générer un numéro de compte unique
    private String generateUniqueAccountNumber() {
        String randomAccountNumber = this.generateRandomAccountNumber();
        // Vérifier si le numéro de compte existe déjà et en générer un nouveau si nécessaire
        while (repository.isAccountNumberExists(randomAccountNumber)) {
            randomAccountNumber = this.generateRandomAccountNumber();
        }
        return randomAccountNumber;
    }

    // Méthode pour générer un numéro de compte aléatoire
    private String generateRandomAccountNumber() {
        return String.format("%09d", (int) (Math.random() * 1_000_000_000));
    }

    // Méthode pour générer un PIN aléatoire
    private String generateRandomPin() {
        return String.format("%04d", (int) (Math.random() * 10_000));
    }
}
