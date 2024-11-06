package com.atm.Admin;

import com.atm.OOP.BankAccountRepository;
import com.atm.OOP.BankAccountRepositoryImpl;
import com.atm.OOP.Admin.BankAnalytics;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.collections.FXCollections;
import javafx.scene.text.Text;
import javafx.scene.chart.XYChart;

public class AnalyticsController extends AdminBaseController {

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

    BankAnalytics analytics;

    @FXML
    public void initialize() {
        super.initialize();
        try {
            BankAccountRepository repository = new BankAccountRepositoryImpl();
            analytics = new BankAnalytics(repository);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadAnalytics();
        setupBarChart();
    }

    private void loadAnalytics() {
        usersCountText.setText(String.valueOf(this.analytics.getUserCount()));
        depositCountText.setText(String.valueOf(this.analytics.getDepositCount()));
        withdrawCountText.setText(String.valueOf(this.analytics.getWithdrawCount()));
        totalBalanceText.setText(String.format("%.2f €", this.analytics.getTotalBalance()));
    }

    private void setupBarChart() {
        XYChart.Series<String, Number> depositSeries = new XYChart.Series<>();
        depositSeries.setName("Dépôts");
        depositSeries.getData().add(new XYChart.Data<>("Dépôts", analytics.getDepositCount()));

        XYChart.Series<String, Number> withdrawSeries = new XYChart.Series<>();
        withdrawSeries.setName("Retraits");
        withdrawSeries.getData().add(new XYChart.Data<>("Retraits", analytics.getWithdrawCount()));

        barChart.setData(FXCollections.observableArrayList());
        barChart.getData().add(depositSeries);
        barChart.getData().add(withdrawSeries);
    }
}
