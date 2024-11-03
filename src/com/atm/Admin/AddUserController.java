package com.atm.Admin;

import com.atm.Client.OOP.BankAccount;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddUserController {

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
    private Button addButton;

    @FXML
    private Button btnSignout;

    @FXML
    private Button generatePinButton; // Button to generate a new random PIN

    @FXML
    public void initialize() {
        btnSignout.setOnAction(event -> handleLogout());
        addButton.setOnAction(event -> handleAddAccount());
        generatePinButton.setOnAction(event -> generateNewPin()); // Set action for the generatePinButton
        generateAccountNumberAndPin(); // Initial generation for account number and PIN
    }

    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/Login.fxml"));
            Parent loginRoot = loader.load();

            Stage stage = (Stage) btnSignout.getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAddAccount() {
        String firstname = firstNameField.getText();
        String lastname = lastNameField.getText();
        String accountNumber = accountNumberField.getText();
        String pin = pinField.getText();
        double initialBalance;

        // Validate and parse the balance
        try {
            initialBalance = Double.parseDouble(balanceField.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Balance initiale invalide.");
            return;
        }

        // Create the account
        try {
            String resultMessage = BankAccount.createAccount(accountNumber, pin, firstname, lastname, initialBalance);
            showAlert("Résultat", resultMessage);

            if (resultMessage.equals("Compte créé avec succès.")) {
                clearForm(); // Clear the form fields if account creation is successful
                generateAccountNumberAndPin(); // Generate new account number and PIN for next user
            }
        } catch (IOException e) {
            showAlert("Erreur", "Une erreur est survenue lors de la création du compte.");
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
