package com.atm;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

import com.atm.Client.StatementController;

public class WelcomeController extends Application {

    @FXML
    private Button clientOptionButton;

    @FXML
    private Button adminOptionButton;

    public void initialize() {
        clientOptionButton.setOnAction(event -> handleClientOption());
        adminOptionButton.setOnAction(event -> handleAdminOption());
    }

    private void handleClientOption() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Login.fxml"));
            Parent clientLoginRoot = loader.load();

            Stage clientLoginStage = new Stage();
            clientLoginStage.setScene(new Scene(clientLoginRoot));
            clientLoginStage.setTitle("UTBM Banque - Connexion client");
            clientLoginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAdminOption() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/Login.fxml"));
            Parent adminLoginRoot = loader.load();

            Stage adminLoginStage = new Stage();
            adminLoginStage.setScene(new Scene(adminLoginRoot));
            adminLoginStage.setTitle("UTBM Banque - Connexion administrateur");
            adminLoginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Welcome.fxml"));
            
            Scene scene = new Scene(root);

            primaryStage.setTitle("Banque UTBM - Bienvenue");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
