package com.atm.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;
import javafx.scene.layout.Region;

/**
 * Classe UIAlert pour afficher des alertes personnalisées dans une application JavaFX.
 */
public class UIAlert {

    private String title; // Titre de l'alerte
    private String message; // Message de l'alerte
    private Boolean wait; // Indique si l'alerte doit attendre une interaction de l'utilisateur

    /**
     * Constructeur de la classe UIAlert.
     * 
     * @param title   Le titre de l'alerte
     * @param message Le message de l'alerte
     * @param wait    Indique si l'alerte doit attendre une interaction de l'utilisateur
     */
    public UIAlert(String title, String message, Boolean wait) {
        this.title = title;
        this.message = message;
        this.wait = wait;
    }

    /**
     * Affiche l'alerte avec le type spécifié.
     * 
     * @param alertType Le type de l'alerte (INFORMATION, WARNING, ERROR)
     */
    public void show(AlertType alertType) {
        Alert alert = new Alert(alertType); // Crée une nouvelle alerte du type spécifié
        alert.initStyle(StageStyle.UTILITY); // Définit le style de la fenêtre de l'alerte
        alert.setTitle(title); // Définit le titre de l'alerte
        alert.setHeaderText(null); // Supprime le texte d'en-tête de l'alerte
        alert.setContentText(message); // Définit le message de l'alerte
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE); // Ajuste la hauteur de l'alerte
        styleAlert(alert, alertType); // Applique le style personnalisé à l'alerte

        if (wait) {
            alert.showAndWait(); // Affiche l'alerte et attend une interaction de l'utilisateur
        } else {
            alert.show(); // Affiche l'alerte sans attendre
        }
    }

    /**
     * Applique un style personnalisé à l'alerte en fonction de son type.
     * 
     * @param alert     L'alerte à styliser
     * @param alertType Le type de l'alerte
     */
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

    /**
     * Affiche une alerte de succès.
     * 
     * @param title   Le titre de l'alerte
     * @param message Le message de l'alerte
     * @param wait    Indique si l'alerte doit attendre une interaction de l'utilisateur
     */
    public static void showSuccess(String title, String message, Boolean wait) {
        UIAlert alert = new UIAlert(title, message, wait);
        alert.show(AlertType.INFORMATION);
    }

    /**
     * Affiche une alerte d'erreur.
     * 
     * @param title   Le titre de l'alerte
     * @param message Le message de l'alerte
     * @param wait    Indique si l'alerte doit attendre une interaction de l'utilisateur
     */
    public static void showError(String title, String message, Boolean wait) {
        UIAlert alert = new UIAlert(title, message, wait);
        alert.show(AlertType.ERROR);
    }

    /**
     * Affiche une alerte d'avertissement.
     * 
     * @param title   Le titre de l'alerte
     * @param message Le message de l'alerte
     * @param wait    Indique si l'alerte doit attendre une interaction de l'utilisateur
     */
    public static void showWarning(String title, String message, Boolean wait) {
        UIAlert alert = new UIAlert(title, message, wait);
        alert.show(AlertType.WARNING);
    }

    /**
     * Affiche une alerte d'erreur générale avec un message par défaut.
     */
    public static void showGeneralError() {
        UIAlert alert = new UIAlert("Erreur", "Une erreur inattendue s'est produite.", true);
        alert.show(AlertType.ERROR);
    }
}
