package com.atm.OOP.Bank;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe représentant un compte bancaire.
 */
public class BankAccount {
    private String accountNumber; // Numéro de compte
    private String pin; // Code PIN du compte
    private String firstname; // Prénom du titulaire du compte
    private String lastname; // Nom de famille du titulaire du compte
    private double balance; // Solde du compte
    private Date creationDate; // Date de création du compte
    private List<Transaction> transactions; // Liste des transactions associées au compte
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // Format de date pour les transactions

    /**
     * Constructeur avec tous les paramètres.
     * 
     * @param accountNumber Numéro de compte
     * @param pin Code PIN du compte
     * @param firstname Prénom du titulaire du compte
     * @param lastname Nom de famille du titulaire du compte
     * @param balance Solde du compte
     * @param creationDate Date de création du compte
     * @param transactions Liste des transactions associées au compte
     */
    public BankAccount(String accountNumber, String pin, String firstname, String lastname, double balance, Date creationDate, List<Transaction> transactions) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.firstname = firstname;
        this.lastname = lastname;
        this.balance = balance;
        this.creationDate = creationDate;
        this.transactions = transactions != null ? transactions : new ArrayList<>();
    }

    /**
     * Constructeur avec paramètres de base.
     * 
     * @param accountNumber Numéro de compte
     * @param pin Code PIN du compte
     * @param firstname Prénom du titulaire du compte
     * @param lastname Nom de famille du titulaire du compte
     * @param balance Solde du compte
     */
    public BankAccount(String accountNumber, String pin, String firstname, String lastname, double balance) {
        this(accountNumber, pin, firstname, lastname, balance, new Date(), new ArrayList<>());
    }

    // Getters
    public String getAccountNumber() { return accountNumber; }
    public String getPin() { return pin; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public double getBalance() { return balance; }
    public Date getCreationDate() { return creationDate; }
    public List<Transaction> getTransactions() { return transactions; }
    
    // Setters
    public void setPin(String pin) { this.pin = pin; }
    public void setBalance(double balance) { this.balance = balance; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }

    /**
     * Ajoute une transaction à la liste des transactions du compte.
     * 
     * @param transaction La transaction à ajouter
     */
    public void addTransaction(Transaction transaction) { this.transactions.add(transaction); }
}
