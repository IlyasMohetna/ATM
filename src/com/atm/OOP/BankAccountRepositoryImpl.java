package com.atm.OOP;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BankAccountRepositoryImpl implements BankAccountRepository {
    private static final String DATA_PATH = "src/data/user.json";

    @Override
    public Optional<BankAccount> findByAccountNumberAndPin(String accountNumber, String pin) throws IOException {
        JSONArray users = readUsersFromFile();

        for (int i = 0; i < users.length(); i++) {
            JSONObject userJson = users.getJSONObject(i);
            if (userJson.getString("account_number").equals(accountNumber) && userJson.getString("pin").equals(pin)) {
                BankAccount account = parseBankAccountFromJson(userJson);
                return Optional.of(account);
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean isAccountNumberExists(String accountNumber) throws IOException {
        JSONArray users = readUsersFromFile();

        for (int i = 0; i < users.length(); i++) {
            JSONObject userJson = users.getJSONObject(i);
            if (userJson.getString("account_number").equals(accountNumber)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void save(BankAccount account) throws IOException {
        JSONArray users = readUsersFromFile();
        JSONObject accountJson = serializeBankAccountToJson(account);
        boolean accountExists = false;

        // Update existing account
        for (int i = 0; i < users.length(); i++) {
            JSONObject userJson = users.getJSONObject(i);
            if (userJson.getString("account_number").equals(account.getAccountNumber())) {
                users.put(i, accountJson);
                accountExists = true;
                break;
            }
        }

        // Add new account if it doesn't exist
        if (!accountExists) {
            users.put(accountJson);
        }

        writeUsersToFile(users);
    }

    // Helper methods
    private JSONArray readUsersFromFile() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(DATA_PATH)));
        return new JSONArray(content);
    }

    private void writeUsersToFile(JSONArray users) throws IOException {
        Files.write(Paths.get(DATA_PATH), users.toString(4).getBytes());
    }

    private BankAccount parseBankAccountFromJson(JSONObject userJson) {
        String accountNumber = userJson.getString("account_number");
        String pin = userJson.getString("pin");
        String firstname = userJson.getString("firstname");
        String lastname = userJson.getString("lastname");
        double balance = userJson.getDouble("balance");

        // Parse the creation date
        Date creationDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String creationDateString = userJson.getString("creation_date");
        try {
            creationDate = dateFormat.parse(creationDateString);
        } catch (ParseException e) {
            e.printStackTrace();
            creationDate = new Date(); // Default to current date if parsing fails
        }

        List<Transaction> transactions = new ArrayList<>();
        if (userJson.has("transactions")) {
            JSONArray transactionsArray = userJson.getJSONArray("transactions");
            for (int j = 0; j < transactionsArray.length(); j++) {
                JSONObject transactionJson = transactionsArray.getJSONObject(j);
                String type = transactionJson.getString("type");
                double amount = transactionJson.getDouble("amount");

                // Parse the transaction date
                Date date = null;
                String dateString = transactionJson.getString("date");
                try {
                    date = dateFormat.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                    date = new Date(); // Default to current date if parsing fails
                }

                Transaction transaction = new Transaction(type, amount, date);
                transactions.add(transaction);
            }
        }

        return new BankAccount(accountNumber, pin, firstname, lastname, balance, creationDate, transactions);
    }

    private JSONObject serializeBankAccountToJson(BankAccount account) {
        JSONObject userJson = new JSONObject();
        userJson.put("account_number", account.getAccountNumber());
        userJson.put("pin", account.getPin());
        userJson.put("firstname", account.getFirstname());
        userJson.put("lastname", account.getLastname());
        userJson.put("balance", account.getBalance());
    
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String creationDateString = dateFormat.format(account.getCreationDate());
        userJson.put("creation_date", creationDateString);
    
        JSONArray transactionsArray = new JSONArray();
        for (Transaction transaction : account.getTransactions()) {
            JSONObject transactionJson = new JSONObject();
            transactionJson.put("type", transaction.getType());
            transactionJson.put("amount", transaction.getAmount());
    
            String dateString = dateFormat.format(transaction.getDate());
            transactionJson.put("date", dateString);
    
            transactionsArray.put(transactionJson);
        }
        userJson.put("transactions", transactionsArray);
    
        return userJson;
    }    
}
