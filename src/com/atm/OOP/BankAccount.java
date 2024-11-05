package com.atm.OOP;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private Date creation_date;
    private List<Transaction> transactions;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRENCH);
    public static final String DATA_PATH = "src/data/user.json";

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

    public Date getCreationDate() {
        return creation_date;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public void setPin(String pin) {
        this.pin = pin;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    public void setCreationDate(Date creation_date) {
        this.creation_date = creation_date;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    // Functions 

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
    
    public String deposit(double amount) throws IOException {
        if (amount <= 0) {
            return "Montant du dépôt invalide. Le montant doit être supérieur à 0.";
        }
        balance += amount;
        transactions.add(new Transaction("deposit", amount, new Date()));
        updateAccountInJson();
        return "Dépôt réussi";
    }

    public String withdraw(double amount) throws IOException {
        if (amount <= 0) {
            return "Montant de retrait invalide.";
        }
        if (amount > balance) {
            return "Solde insuffisant pour effectuer le retrait.";
        }
        
        balance -= amount;
        transactions.add(new Transaction("withdraw", amount, new Date()));
        updateAccountInJson();
        return "Retrait effectué avec succès.";
    }

    public String changePin(String actualPin, String newPin, String confirmNewPin) throws IOException {
        if (!this.pin.equals(actualPin)) {
            return "Le PIN actuel est incorrect.";
        }
        if (!newPin.equals(confirmNewPin)) {
            return "Le nouveau PIN et la confirmation ne correspondent pas.";
        }

        this.pin = newPin;
        updatePinInJson();
        return "Votre PIN a été mis à jour avec succès.";
    }

    private void updatePinInJson() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(DATA_PATH)));
        JSONArray users = new JSONArray(content);

        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("account_number").equals(this.accountNumber)) {
                user.put("pin", this.pin);
                break;
            }
        }

        // Write the updated JSON back to the file
        Files.write(Paths.get(DATA_PATH), users.toString(4).getBytes());
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

    public static String createAccount(String accountNumber, String pin, String firstname, String lastname, double balance) throws IOException {
        // Validate inputs
        if (accountNumber == null || accountNumber.isEmpty() || pin == null || pin.length() != 4) {
            return "Numéro de compte ou PIN invalide.";
        }
        if (firstname == null || firstname.isEmpty() || lastname == null || lastname.isEmpty()) {
            return "Le prénom et le nom sont requis.";
        }
        if (balance < 0) {
            return "La balance initiale doit être positive.";
        }

        // Check if account number is unique
        if (isAccountNumberExists(accountNumber)) {
            return "Le numéro de compte existe déjà. Veuillez en choisir un autre.";
        }

        // Create the new account
        BankAccount newAccount = new BankAccount(accountNumber, pin, firstname, lastname, balance);
        newAccount.creation_date = new Date();

        // Save to JSON
        addAccountToJson(newAccount);

        return "Compte créé avec succès.";
    }

    private static boolean isAccountNumberExists(String accountNumber) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(DATA_PATH)));
        JSONArray users = new JSONArray(content);

        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("account_number").equals(accountNumber)) {
                return true;
            }
        }
        return false;
    }

    private static void addAccountToJson(BankAccount account) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(DATA_PATH)));
        JSONArray users = new JSONArray(content);

        JSONObject newUser = new JSONObject();
        newUser.put("account_number", account.getAccountNumber());
        newUser.put("pin", account.getPin());
        newUser.put("firstname", account.getFirstname());
        newUser.put("lastname", account.getLastname());
        newUser.put("balance", account.getBalance());
        newUser.put("creation_date", DATE_FORMAT.format(account.getCreationDate()));

        // Adding transactions as an empty array for a new account
        newUser.put("transactions", new JSONArray());

        users.put(newUser);

        // Write updated JSON back to file
        Files.write(Paths.get(DATA_PATH), users.toString(4).getBytes());
    }

}