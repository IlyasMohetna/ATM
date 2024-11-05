package com.atm.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Pos;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.atm.OOP.BankAccount;
import com.atm.OOP.Transaction;
public class StatementController {

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

    private BankAccount bankAccount;

    private static final SimpleDateFormat DISPLAY_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        setUserDetails();
        loadTransactions();
    }

    private void setUserDetails() {
        balanceText.setText(String.format("%.2f €", bankAccount.getBalance()));
        accountNumberText.setText(bankAccount.getAccountNumber());
        accountHolderText.setText(bankAccount.getFirstname() + " " + bankAccount.getLastname());
    }

    private void loadTransactions() {
        List<Transaction> transactions = bankAccount.getTransactions();
        
        Collections.sort(transactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t1, Transaction t2) {
                return t2.getDate().compareTo(t1.getDate());
            }    
        });

        for (Transaction transaction : bankAccount.getTransactions()) {
            // Create a new HBox for each transaction to arrange date, type, and amount
            HBox transactionRow = new HBox();
            transactionRow.setPrefWidth(400); // Set width for consistent alignment
            transactionRow.setAlignment(Pos.CENTER_LEFT);
            transactionRow.setSpacing(10);
    
            // Format date, type, and amount
            String date = DISPLAY_DATE_FORMAT.format(transaction.getDate());
            String type = transaction.getLocalType();
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
