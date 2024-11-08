package com.atm.OOP.Bank;

import java.util.Date;

/**
 * Represents a bank transaction with a type, amount, and date.
 */
public class Transaction {
    private String type;
    private double amount;
    private Date date;

    /**
     * Constructs a new Transaction.
     *
     * @param type the type of the transaction (e.g., deposit, withdrawal)
     * @param amount the amount of the transaction
     * @param date the date of the transaction
     */
    public Transaction(String type, double amount, Date date) {
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    /**
     * Gets the type of the transaction.
     *
     * @return the type of the transaction
     */
    public String getType() { return type; }

    /**
     * Gets the amount of the transaction.
     *
     * @return the amount of the transaction
     */
    public double getAmount() { return amount; }

    /**
     * Gets the date of the transaction.
     *
     * @return the date of the transaction
     */
    public Date getDate() { return date; }
}
