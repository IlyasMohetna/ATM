package com.atm.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.atm.Client.OOP.BankAccount;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField accountNumberField;

    @FXML
    private PasswordField pinField;

    @FXML
    private Button loginButton;

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        String accountNumber = accountNumberField.getText();
        String pin = pinField.getText();

        try {
            BankAccount account = BankAccount.authenticate(accountNumber, pin);

            if (account != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Menu.fxml"));
                Parent loginRoot = loader.load();

                MenuController menuController = loader.getController();
                menuController.setBankAccount(account);

                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(loginRoot));
                stage.setTitle("Dashboard");
            } else {
                System.out.println("Invalid account number or PIN.");
                showAlert("Error", "Invalid account number or PIN.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    // private void navigateToDashboard() {
    //     try {
    //         // Load Dashboard.fxml directly
    //         FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/Dashboard.fxml"));
    //         Parent dashboardRoot = loader.load();

    //         Stage stage = (Stage) loginButton.getScene().getWindow();
    //         stage.setScene(new Scene(dashboardRoot));
    //         stage.setTitle("Dashboard");

    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         showAlert("Error", "Failed to load the dashboard.");
    //     }
    // }

    // private boolean authenticateUser(String email, String password) {
    //     try {
    //         String content = new String(Files.readAllBytes(Paths.get("src/data/admin.json")));
    //         JSONArray users = new JSONArray(content);

    //         for (int i = 0; i < users.length(); i++) {
    //             JSONObject user = users.getJSONObject(i);
    //             if (user.getString("email").equals(email) && user.getString("password").equals(password)) {
    //                 fullname = user.getString("fullname");
    //                 avatar = user.getString("avatar");
    //                 return true;
    //             }
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         showAlert("Error", "Une erreur s'est produite lors de la lecture des données des administrateurs.");
    //     }

    //     return false;
    // }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
