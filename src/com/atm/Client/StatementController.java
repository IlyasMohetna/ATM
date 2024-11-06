package com.atm.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Pos;

import java.util.List;
import java.util.Comparator;

import com.atm.OOP.Bank.BankAccount;
import com.atm.OOP.Bank.Transaction;

public class StatementController extends BaseController {

    @FXML
    private VBox transactionContainer;

    @FXML
    private Button exitButton;

    @FXML
    private Text balanceText;

    @FXML 
    private Text accountNumberText;

    @FXML
    private Text accountHolderText;

    @FXML
    public void initialize() {
        super.initialize();

        setUserDetails();
        loadTransactions();
    }

    private void setUserDetails() {
        BankAccount bankAccount = getBankAccountService().getBankAccount();
        balanceText.setText(String.format("%.2f €", bankAccount.getBalance()));
        accountNumberText.setText(bankAccount.getAccountNumber());
        accountHolderText.setText(bankAccount.getFirstname() + " " + bankAccount.getLastname());
    }

    private void loadTransactions() {
        BankAccount bankAccount = getBankAccountService().getBankAccount();
        List<Transaction> transactions = bankAccount.getTransactions();

        // Sort transactions by date in descending order
        transactions.sort(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t1, Transaction t2) {
                return t2.getDate().compareTo(t1.getDate());
            }    
        });

        // Alternatively, using a lambda expression:
        // transactions.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));

        for (Transaction transaction : transactions) {
            // Create a new HBox for each transaction to arrange date, type, and amount
            HBox transactionRow = new HBox();
            transactionRow.setPrefWidth(400); // Set width for consistent alignment
            transactionRow.setAlignment(Pos.CENTER_LEFT);
            transactionRow.setSpacing(10);

            // Format date, type, and amount
            String date = BankAccount.DATE_FORMAT.format(transaction.getDate());
            String type = transaction.getType();
            String amount = String.format("%,.2f €", transaction.getAmount());

            // Create labels for each part with specific widths
            Label dateLabel = new Label(date);
            Label typeLabel = new Label(type);
            Label amountLabel = new Label(amount);

            // Set fixed width to control alignment
            dateLabel.setPrefWidth(120); // Adjust width as needed
            typeLabel.setPrefWidth(150); // Adjust to create space between date and amount
            typeLabel.setAlignment(Pos.CENTER);
            amountLabel.setPrefWidth(100);
            amountLabel.setAlignment(Pos.CENTER_RIGHT);

            // Style the amount label to color it based on the transaction type
            amountLabel.setStyle("-fx-text-fill: " + (transaction.getType().equalsIgnoreCase("deposit") ? "green;" : "red;"));

            // Add labels to the transaction row
            transactionRow.getChildren().addAll(dateLabel, typeLabel, amountLabel);

            // Add transaction row to the container
            transactionContainer.getChildren().add(transactionRow);
        }
    }
}
