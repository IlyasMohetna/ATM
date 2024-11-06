package com.atm.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;
import javafx.scene.layout.Region;

public class UIAlert {

    private String title;
    private String message;
    private Boolean wait;

    public UIAlert(String title, String message, Boolean wait) {
        this.title = title;
        this.message = message;
        this.wait = wait;
    }

    public void show(AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        styleAlert(alert, alertType);

        if (wait) {
            alert.showAndWait();
        } else {
            alert.show();
        }
    }

    private void styleAlert(Alert alert, AlertType alertType) {
        switch (alertType) {
            case INFORMATION:
                alert.getDialogPane().setStyle("-fx-background-color: #d4edda; -fx-border-color: #155724; -fx-border-width: 2;");
                break;
            case WARNING:
                alert.getDialogPane().setStyle("-fx-background-color: #fff3cd; -fx-border-color: #856404; -fx-border-width: 2;");
                break;
            case ERROR:
                alert.getDialogPane().setStyle("-fx-background-color: #f8d7da; -fx-border-color: #721c24; -fx-border-width: 2;");
                break;
            default:
                break;
        }
    }

    public static void showSuccess(String title, String message, Boolean wait) {
        UIAlert alert = new UIAlert(title, message, wait);
        alert.show(AlertType.INFORMATION);
    }

    public static void showError(String title, String message, Boolean wait) {
        UIAlert alert = new UIAlert(title, message, wait);
        alert.show(AlertType.ERROR);
    }

    public static void showWarning(String title, String message, Boolean wait) {
        UIAlert alert = new UIAlert(title, message, wait);
        alert.show(AlertType.WARNING);
    }

    public static void showGeneralError() {
        UIAlert alert = new UIAlert("Erreur", "Une erreur inattendue s'est produite.",true);
        alert.show(AlertType.ERROR);
    }
}
