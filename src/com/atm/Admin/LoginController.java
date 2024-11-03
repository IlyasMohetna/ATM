package com.atm.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private String fullname;
    private String avatar;

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (authenticateUser(email, password)) {
            navigateToDashboard();
        } else {
            showAlert("Error", "Invalid email or password.");
        }
    }

    private void navigateToDashboard() {
        try {
            // Load Dashboard.fxml directly
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/Dashboard.fxml"));
            Parent dashboardRoot = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setUserDetails(fullname, avatar);

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(dashboardRoot));
            stage.setTitle("Dashboard");

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the dashboard.");
        }
    }

    private boolean authenticateUser(String email, String password) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/data/admin.json")));
            JSONArray users = new JSONArray(content);

            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                if (user.getString("email").equals(email) && user.getString("password").equals(password)) {
                    fullname = user.getString("fullname");
                    avatar = user.getString("avatar");
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Une erreur s'est produite lors de la lecture des données des administrateurs.");
        }

        return false;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
