package com.atm.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.atm.OOP.BankAccount;
import com.atm.OOP.BankAccountRepository;
import com.atm.OOP.BankAccountRepositoryImpl;

public class UsersController extends AdminBaseController {
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
        super.initialize();
        // Set up columns to map properties
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        accountNumberColumn.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        pinColumn.setCellValueFactory(new PropertyValueFactory<>("pin"));
        soldeColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

        creationDateColumn.setCellValueFactory(cellData -> {
            Date date = cellData.getValue().getCreationDate();
            String dateString = BankAccount.DATE_FORMAT.format(date);
            return new SimpleStringProperty(dateString);
        });

        // Load users from AccountManager
        loadUsers();
        usersTable.setItems(userList);
    }

    private void loadUsers() {
        BankAccountRepository repository = new BankAccountRepositoryImpl();
        try {
            List<BankAccount> accounts = repository.getAllAccounts();
            userList.setAll(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
