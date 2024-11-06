package com.atm.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import com.atm.OOP.BankAccount;

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
    public void initialize() {
        super.initialize();

        generatePinButton.setOnAction(event -> generateNewPin());
        generateAccountNumberAndPin();
    }

    // private void handleAddAccount() {
    //     String firstname = firstNameField.getText();
    //     String lastname = lastNameField.getText();
    //     String accountNumber = accountNumberField.getText();
    //     String pin = pinField.getText();
    //     double initialBalance;

    //     // Validate and parse the balance
    //     try {
    //         initialBalance = Double.parseDouble(balanceField.getText());
    //     } catch (NumberFormatException e) {
    //         showAlert("Erreur", "Balance initiale invalide.");
    //         return;
    //     }

    //     // Create the account
    //     try {
    //         String resultMessage = BankAccount.createAccount(accountNumber, pin, firstname, lastname, initialBalance);
    //         showAlert("Résultat", resultMessage);

    //         if (resultMessage.equals("Compte créé avec succès.")) {
    //             clearForm(); // Clear the form fields if account creation is successful
    //             generateAccountNumberAndPin(); // Generate new account number and PIN for next user
    //         }
    //     } catch (IOException e) {
    //         showAlert("Erreur", "Une erreur est survenue lors de la création du compte.");
    //         e.printStackTrace();
    //     }
    // }

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

    private void generateAccountNumberAndPin() {
        // Generate a random account number and PIN
        String randomAccountNumber = String.valueOf((int) (Math.random() * 1_000_000_000)); // 9-digit random number
        String randomPin = generateRandomPin();

        accountNumberField.setText(randomAccountNumber);
        pinField.setText(randomPin);
    }

    private void generateNewPin() {
        // Generate a new random PIN and set it in the pinField
        pinField.setText(generateRandomPin());
    }

    private String generateRandomPin() {
        // Generates a 4-digit random PIN
        return String.format("%04d", (int) (Math.random() * 10_000));
    }
}
