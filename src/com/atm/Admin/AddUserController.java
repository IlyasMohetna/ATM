package com.atm.Admin;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

import com.atm.OOP.BankAccount;
import com.atm.OOP.BankAccountRepository;
import com.atm.OOP.BankAccountRepositoryImpl;
import com.atm.Utils.UIAlert;

public class AddUserController extends AdminBaseController {

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

    @FXML
    private Button generatePinButton;

    @FXML 
    private Button addAccountButton;

    BankAccountRepository repository = new BankAccountRepositoryImpl();

    @FXML
    public void initialize() {
        super.initialize();

        generatePinButton.setOnAction(event -> setGeneratedPin());
        addAccountButton.setOnAction(event -> handleAddAccount());

        setGeneratedPin();
        setGeneratedAccountNumber();
    }

    private void handleAddAccount() {
        String firstname = firstNameField.getText();
        String lastname = lastNameField.getText();
        String accountNumber = accountNumberField.getText();
        String pin = pinField.getText();
        double initialBalance;

        try {
            initialBalance = Double.parseDouble(balanceField.getText());
            BankAccount newAccount = new BankAccount(accountNumber, pin, firstname, lastname, initialBalance);
            repository.save(newAccount);
            UIAlert.showSuccess("Succès", "Le compte a été créé avec succès.", false);
            clearForm();
            handleAction("users");
        } catch (NumberFormatException e) {
            UIAlert.showError("Erreur", "Balance initiale invalide.", true);
        }catch (IOException e) {
            showAlert("Erreur", "Une erreur est survenue lors de la création du compte.");
            UIAlert.showError("Erreur", "Une erreur est survenue lors de la création du compte.", true);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        balanceField.clear();
    }

    // Set methods
    
    private void setGeneratedPin() {
        pinField.setText(generateRandomPin());
    }

    private void setGeneratedAccountNumber() {
        accountNumberField.setText(generateUniqueAccountNumber());
    }

    // Utils

    private String generateUniqueAccountNumber() {
        String randomAccountNumber = this.generateRandomAccountNumber();
        while (repository.isAccountNumberExists(randomAccountNumber)) {
            randomAccountNumber = this.generateRandomAccountNumber();
        }
        
        return randomAccountNumber;
    }

    private String generateRandomAccountNumber() {
        return String.format("%09d", (int) (Math.random() * 1_000_000_000));
    }

    private String generateRandomPin() {
        return String.format("%04d", (int) (Math.random() * 10_000));
    }
}
