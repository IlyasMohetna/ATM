package com.atm.Admin;

import com.atm.Client.OOP.AccountAnalytics;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Label fullnameLabel;

    @FXML
    private ImageView avatarImageView;

    @FXML
    private Text usersCountText;

    @FXML
    private Text depositCountText;

    @FXML
    private Text withdrawCountText;

    @FXML
    private Text totalBalanceText;

    @FXML
    private Button analyticsButton;
    
    @FXML
    private Button usersButton;

    @FXML
    private Button addUserButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button btnSignout;

    @FXML
    private BarChart<String, Number> barChart; // Update this to BarChart

    @FXML
    public void initialize() {
        usersButton.setOnAction(event -> handleUsers());
        btnSignout.setOnAction(event -> handleLogout());
        addUserButton.setOnAction(event -> handleAddUser());
        loadAnalytics();
        setupBarChart(); // Update this to setupBarChart
    }

    public void setUserDetails(String fullname, String avatarPath) {
        fullnameLabel.setText(fullname);
        Image avatarImage = new Image(getClass().getResourceAsStream(avatarPath));
        avatarImageView.setImage(avatarImage);
    }

    private void loadAnalytics() {
        AccountAnalytics analytics = new AccountAnalytics();

        usersCountText.setText(String.valueOf(analytics.getUserCount()));
        depositCountText.setText(String.valueOf(analytics.getDepositCount()));
        withdrawCountText.setText(String.valueOf(analytics.getWithdrawCount()));
        totalBalanceText.setText(String.format("%.2f €", analytics.getTotalBalance()));
    }

    private void setupBarChart() {
        AccountAnalytics analytics = new AccountAnalytics();
    
        // Creating deposit data
        XYChart.Series<String, Number> depositSeries = new XYChart.Series<>();
        depositSeries.setName("Dépôts");
        depositSeries.getData().add(new XYChart.Data<>("Dépôts", analytics.getDepositCount()));

        // Creating withdrawal data
        XYChart.Series<String, Number> withdrawSeries = new XYChart.Series<>();
        withdrawSeries.setName("Retraits");
        withdrawSeries.getData().add(new XYChart.Data<>("Retraits", analytics.getWithdrawCount()));

        // Add data series to the bar chart
        barChart.getData().addAll(depositSeries, withdrawSeries);
    }

    private void handleUsers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/Users.fxml"));
            Parent usersRoot = loader.load();

            Stage stage = (Stage) usersButton.getScene().getWindow();
            stage.setScene(new Scene(usersRoot));
            stage.setTitle("Utilisateurs");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAddUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/AddUser.fxml"));
            Parent usersRoot = loader.load();

            Stage stage = (Stage) usersButton.getScene().getWindow();
            stage.setScene(new Scene(usersRoot));
            stage.setTitle("Utilisateurs");

        } catch (IOException e) {
            e.printStackTrace();
        }
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
