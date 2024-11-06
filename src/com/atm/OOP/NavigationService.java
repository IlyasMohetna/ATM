package com.atm.OOP;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// import com.atm.Client.BalanceController;
// import com.atm.Client.ChangePinController;
// import com.atm.Client.DepositController;
// import com.atm.Client.StatementController;
// import com.atm.Client.WithdrawController;

public class NavigationService {
    public static final String LOGIN = "/fxml/Client/Login.fxml";
    public static final String MENU = "/fxml/Client/Menu.fxml";
    public static final String DEPOSIT = "/fxml/Client/Deposit.fxml";
    public static final String CHANGE_PIN = "/fxml/Client/ChangePin.fxml";
    public static final String WITHDRAW = "/fxml/Client/Withdraw.fxml";
    public static final String STATEMENT = "/fxml/Client/Statement.fxml";
    public static final String BALANCE = "/fxml/Client/Balance.fxml";

    public static final String ANALYTICS = "/fxml/Admin/Analytics.fxml";
    public static final String USERS = "/fxml/Admin/Users.fxml";
    public static final String ADD_USER = "/fxml/Admin/AddUser.fxml";
    public static final String LOGIN_ADMIN = "/fxml/Admin/Login.fxml";

    public static void navigateTo(Stage stage, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationService.class.getResource(fxmlPath));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openModal(Stage stage, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationService.class.getResource(fxmlPath));
            Parent root = loader.load();
            
            Stage statementStage = new Stage();
            statementStage.setScene(new Scene(root));
            statementStage.setTitle("Statement");
            
            statementStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
