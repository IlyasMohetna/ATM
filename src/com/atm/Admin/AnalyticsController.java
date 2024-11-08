package com.atm.Admin;

import com.atm.OOP.Admin.BankAnalytics;
import com.atm.OOP.Bank.BankAccountRepository;
import com.atm.OOP.Bank.BankAccountRepositoryImpl;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.collections.FXCollections;
import javafx.scene.text.Text;
import javafx.scene.chart.XYChart;

public class AnalyticsController extends AdminBaseController {

    // Annotation FXML pour lier les éléments de l'interface utilisateur
    @FXML
    private Text usersCountText;

    @FXML
    private Text depositCountText;

    @FXML
    private Text withdrawCountText;

    @FXML
    private Text totalBalanceText;

    @FXML
    private BarChart<String, Number> barChart;

    // Instance de BankAnalytics pour effectuer des analyses bancaires
    BankAnalytics analytics;

    // Méthode d'initialisation appelée automatiquement après le chargement du fichier FXML
    @FXML
    public void initialize() {
        // Appel de la méthode initialize() de la classe parente
        super.initialize();
        try {
            // Création d'une instance de BankAccountRepositoryImpl
            BankAccountRepository repository = new BankAccountRepositoryImpl();
            // Initialisation de l'instance analytics avec le repository
            analytics = new BankAnalytics(repository);
        } catch (Exception e) {
            // Gestion des exceptions en imprimant la pile d'appels
            e.printStackTrace();
        }
        // Chargement des données analytiques
        loadAnalytics();
        // Configuration du graphique à barres
        setupBarChart();
    }

    // Méthode pour charger les données analytiques et les afficher dans l'interface utilisateur
    private void loadAnalytics() {
        // Affichage du nombre d'utilisateurs
        usersCountText.setText(String.valueOf(this.analytics.getUserCount()));
        // Affichage du nombre de dépôts
        depositCountText.setText(String.valueOf(this.analytics.getDepositCount()));
        // Affichage du nombre de retraits
        withdrawCountText.setText(String.valueOf(this.analytics.getWithdrawCount()));
        // Affichage du solde total formaté en euros
        totalBalanceText.setText(String.format("%.2f €", this.analytics.getTotalBalance()));
    }

    // Méthode pour configurer le graphique à barres avec les données analytiques
    private void setupBarChart() {
        // Création d'une série de données pour les dépôts
        XYChart.Series<String, Number> depositSeries = new XYChart.Series<>();
        depositSeries.setName("Dépôts");
        depositSeries.getData().add(new XYChart.Data<>("Dépôts", analytics.getDepositCount()));

        // Création d'une série de données pour les retraits
        XYChart.Series<String, Number> withdrawSeries = new XYChart.Series<>();
        withdrawSeries.setName("Retraits");
        withdrawSeries.getData().add(new XYChart.Data<>("Retraits", analytics.getWithdrawCount()));

        // Initialisation des données du graphique à barres
        barChart.setData(FXCollections.observableArrayList());
        // Ajout des séries de données au graphique
        barChart.getData().add(depositSeries);
        barChart.getData().add(withdrawSeries);
    }
}
