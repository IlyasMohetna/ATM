package com.atm.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import java.io.IOException;

import com.atm.Utils.UIAlert;

public class ChangePinController extends BalanceController {
    @FXML
    private Button actionButton4;

    @FXML
    private Button actionButton8;

    @FXML
    private PasswordField actualPIN;

    @FXML
    private PasswordField newPIN;

    @FXML
    private PasswordField confirmNewPIN;

    @FXML
    public void initialize() {
        super.initialize();

        actionButton8.setOnAction(event -> handleChangePin());
        actionButton4.setOnAction(event -> handleAction("menu"));
    }

    private void handleChangePin() {
        String actualPin = actualPIN.getText();
        String newPin = newPIN.getText();
        String confirmNewPin = confirmNewPIN.getText();
    
        try {
            getBankAccountService().changePin(actualPin, newPin, confirmNewPin);
            
            UIAlert.showSuccess("Opération réussie", "Votre PIN a été mis à jour avec succès.", false);
            handleAction("menu");

        } catch (IllegalArgumentException e) {
            UIAlert.showWarning("Erreur", e.getMessage(), true);
        } catch (IOException e) {
            e.printStackTrace();
            UIAlert.showError("Erreur", "Une erreur s'est produite lors de la mise à jour de votre PIN. Veuillez réessayer plus tard.", true);
        }catch (Exception e) {
            e.printStackTrace();
            UIAlert.showGeneralError();
        }
    }

}
