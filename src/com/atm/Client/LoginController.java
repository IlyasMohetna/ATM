package com.atm.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import com.atm.OOP.Bank.BankAccountRepository;
import com.atm.OOP.Bank.BankAccountRepositoryImpl;
import com.atm.OOP.Bank.BankAccountService;
import com.atm.Utils.UIAlert;

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

    public String getAccountNumberFieldText()
    {
        return accountNumberField.getText();
    }

    public String getPinFieldText()
    {
        return pinField.getText();
    }

    private void handleLogin() {
        String accountNumber = this.getAccountNumberFieldText();
        String pin = this.getPinFieldText();

        BankAccountRepository repository = new BankAccountRepositoryImpl();
        BankAccountService service = new BankAccountService(repository);

        try {
            service.authenticate(accountNumber, pin);
            BaseController.setBankAccountService(service);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Client/Menu.fxml"));
            Parent loginRoot = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(loginRoot));
            stage.setTitle("Dashboard");
        } catch (IllegalArgumentException e) {
            UIAlert.showError("Erreur", e.getMessage(), true);
        } catch (IOException e) {
            UIAlert.showGeneralError();
            System.err.println(e);
        }   
    }
}
