package com.atm.Client.OOP;

import java.util.Date;

import org.json.JSONObject;

public class Transaction {
    private String type;
    private double amount;
    private Date date;

    public Transaction(String type, double amount, Date date) {
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    // Getters
    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public String getLocalType() {
        return type.equalsIgnoreCase("deposit") ? "Dépôt" : "Retrait";
    }

    public JSONObject toJSON() {
        JSONObject transaction = new JSONObject();
        transaction.put("type", type);
        transaction.put("amount", amount);
        transaction.put("date", BankAccount.DATE_FORMAT.format(date));
        return transaction;
    }
}