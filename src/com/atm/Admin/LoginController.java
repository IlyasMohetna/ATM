package com.atm.Admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import com.atm.OOP.Admin.AdminRepository;
import com.atm.OOP.Admin.AdminService;
import com.atm.Utils.UIAlert;


public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        AdminRepository repository = new AdminRepository();
        AdminService service = new AdminService(repository);

        try {
            service.authenticate(email, password);
            AdminBaseController.setAdminService(service);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/Analytics.fxml"));
            Parent loginRoot = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Dashboard");
        } catch (IllegalArgumentException e) {
            UIAlert.showError("Erreur", e.getMessage(), true);
        } catch (IOException e) {
            UIAlert.showGeneralError();
            e.printStackTrace();

            System.err.println(e);
        } catch (Exception e) {
            UIAlert.showGeneralError();
            System.err.println(e);
        }
    }
}
