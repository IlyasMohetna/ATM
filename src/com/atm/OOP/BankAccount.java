package com.atm.OOP;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankAccount {
    private String accountNumber;
    private String pin;
    private String firstname;
    private String lastname;
    private double balance;
    private Date creationDate;
    private List<Transaction> transactions;

    public BankAccount(String accountNumber, String pin, String firstname, String lastname, double balance, Date creationDate, List<Transaction> transactions) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.firstname = firstname;
        this.lastname = lastname;
        this.balance = balance;
        this.creationDate = creationDate;
        this.transactions = transactions != null ? transactions : new ArrayList<>();
    }

    public BankAccount(String accountNumber, String pin, String firstname, String lastname, double balance) {
        this(accountNumber, pin, firstname, lastname, balance, new Date(), new ArrayList<>());
    }

    public String getAccountNumber() { return accountNumber; }
    public String getPin() { return pin; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public double getBalance() { return balance; }
    public Date getCreationDate() { return creationDate; }
    public List<Transaction> getTransactions() { return transactions; }
    
    public void setPin(String pin) { this.pin = pin; }
    public void setBalance(double balance) { this.balance = balance; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }

    public void addTransaction(Transaction transaction) { this.transactions.add(transaction); }
}
