package com.atm.OOP.Bank;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;

public class BankAccountRepositoryImpl implements BankAccountRepository {
    private static final String DATA_PATH = "data/user.json";

    @Override
    public List<BankAccount> getAllAccounts() throws IOException {
        
        List<BankAccount> accounts = new ArrayList<>();
        
        JSONArray users = readUsersFromFile();

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

            accounts.add(account);
        }

        return accounts;
    }

    @Override
    public Optional<BankAccount> findByAccountNumberAndPin(String accountNumber, String pin) throws IOException {
        JSONArray users = readUsersFromFile();

        for (int i = 0; i < users.length(); i++) {
            JSONObject userJson = users.getJSONObject(i);
            if (userJson.getString("account_number").equals(accountNumber) && userJson.getString("pin").equals(pin)) {
                try {
                    BankAccount account = parseBankAccountFromJson(userJson);
                    return Optional.of(account);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Optional.empty();
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean isAccountNumberExists(String accountNumber) {
        try{
            JSONArray users = readUsersFromFile();

            for (int i = 0; i < users.length(); i++) {
                JSONObject userJson = users.getJSONObject(i);
                if (userJson.getString("account_number").equals(accountNumber)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void save(BankAccount account) throws IOException {
        JSONArray users = readUsersFromFile();
        JSONObject accountJson = serializeBankAccountToJson(account);
        boolean accountExists = false;

        for (int i = 0; i < users.length(); i++) {
            JSONObject userJson = users.getJSONObject(i);
            if (userJson.getString("account_number").equals(account.getAccountNumber())) {
                users.put(i, accountJson);
                accountExists = true;
                break;
            }
        }

        if (!accountExists) {
            users.put(accountJson);
        }

        writeUsersToFile(users);
    }

    // Helper methods
    private JSONArray readUsersFromFile() throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(DATA_PATH)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: data/user.json");
            }
            String content = new String(is.readAllBytes());
            return new JSONArray(content);
        }
    }
        

    private void writeUsersToFile(JSONArray users) throws IOException {
        Files.write(Paths.get(DATA_PATH), users.toString(4).getBytes());
    }

    private BankAccount parseBankAccountFromJson(JSONObject userJson) throws Exception {
        String accountNumber = userJson.getString("account_number");
        String pin = userJson.getString("pin");
        String firstname = userJson.getString("firstname");
        String lastname = userJson.getString("lastname");
        double balance = userJson.getDouble("balance");
        String creationDateString = userJson.getString("creation_date");

        Date creationDate = BankAccount.DATE_FORMAT.parse(creationDateString);

        List<Transaction> transactions = new ArrayList<>();
        if (userJson.has("transactions")) {
            JSONArray transactionsArray = userJson.getJSONArray("transactions");
            for (int j = 0; j < transactionsArray.length(); j++) {
                JSONObject transactionJson = transactionsArray.getJSONObject(j);
                String type = transactionJson.getString("type");
                double amount = transactionJson.getDouble("amount");

                String dateString = transactionJson.getString("date");
                Date date = BankAccount.DATE_FORMAT.parse(dateString);

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
    
        String creationDateString = BankAccount.DATE_FORMAT.format(account.getCreationDate());
        userJson.put("creation_date", creationDateString);
    
        JSONArray transactionsArray = new JSONArray();
        for (Transaction transaction : account.getTransactions()) {
            JSONObject transactionJson = new JSONObject();
            transactionJson.put("type", transaction.getType());
            transactionJson.put("amount", transaction.getAmount());
    
            String dateString = BankAccount.DATE_FORMAT.format(transaction.getDate());
            transactionJson.put("date", dateString);
    
            transactionsArray.put(transactionJson);
        }
        userJson.put("transactions", transactionsArray);
    
        return userJson;
    }

}
