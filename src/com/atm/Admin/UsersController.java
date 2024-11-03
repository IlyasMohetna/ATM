package com.atm.Admin;

import com.atm.Client.OOP.AccountManager;
import com.atm.Client.OOP.BankAccount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import java.io.IOException;
import java.util.Date;

public class UsersController {

    @FXML
    private Label fullnameLabel;

    @FXML
    private ImageView avatarImageView;

    @FXML
    private Button btnSignout;

    @FXML
    private TableView<BankAccount> usersTable;

    @FXML
    private TableColumn<BankAccount, String> firstnameColumn;

    @FXML
    private TableColumn<BankAccount, String> lastnameColumn;

    @FXML
    private TableColumn<BankAccount, String> accountNumberColumn;

    @FXML
    private TableColumn<BankAccount, String> pinColumn;

    @FXML
    private TableColumn<BankAccount, Double> soldeColumn;

    @FXML
    private TableColumn<BankAccount, String> creationDateColumn;

    private ObservableList<BankAccount> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        btnSignout.setOnAction(event -> handleLogout());

        // Set up columns to map properties
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        accountNumberColumn.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        pinColumn.setCellValueFactory(new PropertyValueFactory<>("pin"));
        soldeColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        // Format the creation date to "dd/MM/yyyy HH:mm:ss"
        creationDateColumn.setCellValueFactory(cellData -> {
            Date date = cellData.getValue().getCreationDate();
            return new SimpleStringProperty(BankAccount.DATE_FORMAT.format(date));
        });

        // Load users from AccountManager
        loadUsers();
        usersTable.setItems(userList);
    }

    public void setUserDetails(String fullname, String avatarPath) {
        fullnameLabel.setText(fullname);
        Image avatarImage = new Image(getClass().getResourceAsStream(avatarPath));
        avatarImageView.setImage(avatarImage);
    }

    private void loadUsers() {
        AccountManager accountManager = new AccountManager();
        userList.setAll(accountManager.getAccounts()); // Populate userList with accounts from AccountManager
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
