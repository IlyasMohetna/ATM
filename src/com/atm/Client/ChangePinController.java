package com.atm.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import java.io.IOException;

import com.atm.Utils.UIAlert;

public class ChangePinController extends BaseController {
    // Bouton pour confirmer le changement de PIN
    @FXML
    private Button actionButton4;

    // Bouton pour annuler et retourner au menu
    @FXML
    private Button actionButton8;

    // Champ pour entrer le PIN actuel
    @FXML
    private PasswordField actualPIN;

    // Champ pour entrer le nouveau PIN
    @FXML
    private PasswordField newPIN;

    // Champ pour confirmer le nouveau PIN
    @FXML
    private PasswordField confirmNewPIN;

    // Méthode d'initialisation appelée après le chargement du fichier FXML
    @FXML
    public void initialize() {
        super.initialize();

        // Associe l'action de changement de PIN au bouton actionButton8
        actionButton8.setOnAction(event -> handleChangePin());
        // Associe l'action de retour au menu au bouton actionButton4
        actionButton4.setOnAction(event -> handleAction("menu"));
    }

    // Méthode pour gérer le changement de PIN
    private void handleChangePin() {
        // Récupère les valeurs des champs de texte
        String actualPin = actualPIN.getText();
        String newPin = newPIN.getText();
        String confirmNewPin = confirmNewPIN.getText();
    
        try {
            // Appelle le service pour changer le PIN
            getBankAccountService().changePin(actualPin, newPin, confirmNewPin);
            
            // Affiche un message de succès
            UIAlert.showSuccess("Opération réussie", "Votre PIN a été mis à jour avec succès.", false);
            // Retourne au menu
            handleAction("menu");

        } catch (IllegalArgumentException e) {
            // Affiche un avertissement si les arguments sont invalides
            UIAlert.showWarning("Erreur", e.getMessage(), true);
        } catch (IOException e) {
            // Affiche une erreur en cas de problème d'entrée/sortie
            e.printStackTrace();
            UIAlert.showError("Erreur", "Une erreur s'est produite lors de la mise à jour de votre PIN. Veuillez réessayer plus tard.", true);
        } catch (Exception e) {
            // Affiche une erreur générale pour toute autre exception
            e.printStackTrace();
            UIAlert.showGeneralError();
        }
    }

}
