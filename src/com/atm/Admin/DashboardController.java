package com.atm.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Label fullnameLabel;

    @FXML
    private ImageView avatarImageView;

    @FXML
    private Button btnSignout; // The logout button

    @FXML
    public void initialize() {
        btnSignout.setOnAction(event -> handleLogout());
    }

    public void setUserDetails(String fullname, String avatarPath) {
        fullnameLabel.setText(fullname); // Set the full name on the label
        Image avatarImage = new Image(getClass().getResourceAsStream(avatarPath)); // Load the avatar image
        avatarImageView.setImage(avatarImage); // Set the avatar image on the ImageView
    }

    private void handleLogout() {
        try {
            // Load the Login.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/Login.fxml"));
            Parent loginRoot = loader.load();

            // Get the current stage
            Stage stage = (Stage) btnSignout.getScene().getWindow();
            
            // Set the new scene (Login) to the stage
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
