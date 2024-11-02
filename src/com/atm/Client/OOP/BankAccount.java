package com.atm.Client.OOP;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONObject;

public class BankAccount {
    private String accountNumber;
    private String pin;
    private String firstname;
    private String lastname;
    private double balance;
    private List<Transaction> transactions;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRENCH);
    private static final String DATA_PATH = "src/data/user.json";

    // Constructor to initialize BankAccount from JSON data
    public BankAccount(String accountNumber, String pin, String firstname, String lastname, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.firstname = firstname;
        this.lastname = lastname;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public static BankAccount authenticate(String accountNumber, String pin) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(DATA_PATH)));
        JSONArray users = new JSONArray(content);

        // Search for account with matching account number and pin
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("account_number").equals(accountNumber) && user.getString("pin").equals(pin)) {
                // Found matching user, create and return BankAccount instance
                String firstname = user.getString("firstname");
                String lastname = user.getString("lastname");
                double balance = user.getDouble("balance");

                BankAccount account = new BankAccount(accountNumber, pin, firstname, lastname, balance);

                // Load transactions if they exist
                if (user.has("transactions")) {
                    JSONArray transactionsArray = user.getJSONArray("transactions");
                    for (int j = 0; j < transactionsArray.length(); j++) {
                        JSONObject transactionJSON = transactionsArray.getJSONObject(j);
                        String type = transactionJSON.getString("type");
                        double amount = transactionJSON.getDouble("amount");
                        Date date;
                        try {
                            date = DATE_FORMAT.parse(transactionJSON.getString("date"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            continue; // Skip this transaction if date parsing fails
                        }
                        account.transactions.add(new Transaction(type, amount, date));
                    }
                }

                return account;
            }
        }

        // Return null if no matching account found
        return null;
    }

    
    public boolean deposit(double amount) throws IOException {
        if (amount > 0) {
            balance += amount;
            transactions.add(new Transaction("deposit", amount, new Date()));
            updateAccountInJson();
            return true;
        } else {
            return false;
        }
    }

    public boolean withdraw(double amount) throws IOException {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.add(new Transaction("withdrawal", amount, new Date()));
            updateAccountInJson();
            return true;
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance");
            return false;
        }
    }

    public double checkBalance() {
        return balance;
    }

    private void updateAccountInJson() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(DATA_PATH)));
        JSONArray users = new JSONArray(content);

        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("account_number").equals(this.accountNumber)) {
                user.put("balance", this.balance);

                JSONArray transactionsArray = new JSONArray();
                for (Transaction transaction : this.transactions) {
                    transactionsArray.put(transaction.toJSON());
                }
                user.put("transactions", transactionsArray);

                break;
            }
        }

        // Write updated JSON back to file
        Files.write(Paths.get(DATA_PATH), users.toString(4).getBytes());
    }
}