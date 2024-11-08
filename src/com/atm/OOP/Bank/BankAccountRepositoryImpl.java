package com.atm.OOP.Bank;

import org.json.JSONArray;
import org.json.JSONObject;

import com.atm.Utils.UIAlert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;

/**
 * Implémentation du repository des comptes bancaires.
 */
public class BankAccountRepositoryImpl implements BankAccountRepository {
    private static final String SRC_USER_DATA_PATH = "data/user.json";
    public static final String LOCAL_USER_DATA_PATH = System.getProperty("user.home") + "/ATMApp/user.json";

    /**
     * Constructeur qui initialise le fichier de données.
     */
    public BankAccountRepositoryImpl() {
        try {
            initializeDataFile();
        } catch (IOException e) {
            UIAlert.showError("Erreur", "Erreur lors de l'initialisation du fichier de données", true);
        }
    }

    /**
     * Récupère tous les comptes bancaires.
     * 
     * @return une liste de tous les comptes bancaires.
     * @throws IOException si une erreur d'entrée/sortie se produit.
     */
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

            // Charger les transactions si elles existent
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

    /**
     * Trouve un compte bancaire par numéro de compte et PIN.
     * 
     * @param accountNumber le numéro de compte.
     * @param pin le code PIN.
     * @return un Optional contenant le compte bancaire s'il est trouvé, sinon un Optional vide.
     * @throws IOException si une erreur d'entrée/sortie se produit.
     */
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

    /**
     * Vérifie si un numéro de compte existe.
     * 
     * @param accountNumber le numéro de compte.
     * @return true si le numéro de compte existe, sinon false.
     */
    @Override
    public boolean isAccountNumberExists(String accountNumber) {
        try {
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

    /**
     * Sauvegarde un compte bancaire.
     * 
     * @param account le compte bancaire à sauvegarder.
     * @throws IOException si une erreur d'entrée/sortie se produit.
     */
    @Override
    public void save(BankAccount account) throws IOException {
        JSONArray users = readUsersFromFile();
        JSONObject accountJson = serializeBankAccountToJson(account);
        boolean accountExists = false;

        for (int i = 0; i < users.length(); i++) {
            JSONObject userJson = users.getJSONObject(i);
            if (userJson.getString("account_number").equals(account.getAccountNumber())) {
                // Mettre à jour le compte existant
                users.put(i, accountJson);
                accountExists = true;
                break;
            }
        }

        if (!accountExists) {
            // Ajouter un nouveau compte
            users.put(accountJson);
        }

        writeUsersToFile(users);
    }

    /**
     * Lit les utilisateurs à partir du fichier.
     * 
     * @return un JSONArray contenant les utilisateurs.
     * @throws IOException si une erreur d'entrée/sortie se produit.
     */
    private JSONArray readUsersFromFile() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(LOCAL_USER_DATA_PATH)));
        return new JSONArray(content);
    }

    /**
     * Écrit les utilisateurs dans le fichier.
     * 
     * @param users le JSONArray contenant les utilisateurs.
     * @throws IOException si une erreur d'entrée/sortie se produit.
     */
    private void writeUsersToFile(JSONArray users) throws IOException {
        Files.write(Paths.get(LOCAL_USER_DATA_PATH), users.toString(4).getBytes());
    }

    /**
     * Parse un compte bancaire à partir d'un objet JSON.
     * 
     * @param userJson l'objet JSON représentant un utilisateur.
     * @return le compte bancaire parsé.
     * @throws Exception si une erreur de parsing se produit.
     */
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

    /**
     * Sérialise un compte bancaire en objet JSON.
     * 
     * @param account le compte bancaire à sérialiser.
     * @return l'objet JSON représentant le compte bancaire.
     */
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

    /**
     * Initialise le fichier de données.
     * 
     * @throws IOException si une erreur d'entrée/sortie se produit.
     */
    public static void initializeDataFile() throws IOException {
        java.nio.file.Path localPath = Paths.get(LOCAL_USER_DATA_PATH);

        if (!Files.exists(localPath)) {
            Files.createDirectories(localPath.getParent());
            try (InputStream is = BankAccountRepositoryImpl.class.getClassLoader().getResourceAsStream(SRC_USER_DATA_PATH)) {
                if (is == null) throw new FileNotFoundException("Resource not found: " + SRC_USER_DATA_PATH);
                Files.copy(is, localPath);
            }
        }
    }
}
