package com.atm.OOP;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountAnalytics {

    private List<BankAccount> accounts;
    private int depositCount;
    private int withdrawCount;
    private double totalBalance;

    public AccountAnalytics() {
        this.accounts = new ArrayList<>();
        loadAccounts();
        calculateStatistics();
    }

    // Load all accounts from the JSON file
    private void loadAccounts() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(BankAccount.DATA_PATH)));
            JSONArray users = new JSONArray(content);

            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                String accountNumber = user.getString("account_number");
                String pin = user.getString("pin");
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
                            date = BankAccount.DATE_FORMAT.parse(transactionJSON.getString("date"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            continue;
                        }
                        account.getTransactions().add(new Transaction(type, amount, date));
                    }
                }

                this.accounts.add(account);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Calculate deposit count, withdraw count, and total balance
    private void calculateStatistics() {
        for (BankAccount account : accounts) {
            totalBalance += account.getBalance();

            for (Transaction transaction : account.getTransactions()) {
                if ("deposit".equalsIgnoreCase(transaction.getType())) {
                    depositCount++;
                } else if ("withdraw".equalsIgnoreCase(transaction.getType())) {
                    withdrawCount++;
                }
            }
        }
    }

    public int getUserCount() {
        return accounts.size();
    }

    public int getDepositCount() {
        return depositCount;
    }

    public int getWithdrawCount() {
        return withdrawCount;
    }

    public double getTotalBalance() {
        return totalBalance;
    }
}
