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

public class AddUserController {

    @FXML
    private Label fullnameLabel;

    @FXML
    private ImageView avatarImageView;

    @FXML
    private Button btnSignout;

    @FXML
    public void initialize() {
        btnSignout.setOnAction(event -> handleLogout());
    }

    public void setUserDetails(String fullname, String avatarPath) {
        fullnameLabel.setText(fullname);
        Image avatarImage = new Image(getClass().getResourceAsStream(avatarPath));
        avatarImageView.setImage(avatarImage);
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
}
