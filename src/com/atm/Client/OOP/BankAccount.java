package com.atm.Client.OOP;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankAccount {
    private String accountNumber;
    private String pin;
    private String firstname;
    private String lastname;
    private double balance;
    private List<Transaction> transactions;

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

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add(new Transaction("deposit", amount, new Date()));
        } else {
            System.out.println("Invalid deposit amount");
        }
    }

    // Method to withdraw money
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.add(new Transaction("withdrawal", amount, new Date()));
            return true;
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance");
            return false;
        }
    }

    // Method to get balance
    public double checkBalance() {
        return balance;
    }

    // Method to print account details
    public void printAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Name: " + firstname + " " + lastname);
        System.out.println("Balance: " + balance);
    }
}