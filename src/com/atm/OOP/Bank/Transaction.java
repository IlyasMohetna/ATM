package com.atm.OOP.Bank; // Déclaration du package

import java.util.Date; // Importation de la classe Date pour gérer les dates

/**
 * Représente une transaction bancaire avec un type, un montant et une date.
 */
public class Transaction {
    private String type; // Type de la transaction (par exemple, dépôt, retrait)
    private double amount; // Montant de la transaction
    private Date date; // Date de la transaction

    /**
     * Construit une nouvelle Transaction.
     *
     * @param type le type de la transaction (par exemple, dépôt, retrait)
     * @param amount le montant de la transaction
     * @param date la date de la transaction
     */
    public Transaction(String type, double amount, Date date) {
        this.type = type; // Initialisation du type de transaction
        this.amount = amount; // Initialisation du montant de la transaction
        this.date = date; // Initialisation de la date de la transaction
    }

    /**
     * Obtient le type de la transaction.
     *
     * @return le type de la transaction
     */
    public String getType() { return type; } // Retourne le type de la transaction

    /**
     * Obtient le montant de la transaction.
     *
     * @return le montant de la transaction
     */
    public double getAmount() { return amount; } // Retourne le montant de la transaction

    /**
     * Obtient la date de la transaction.
     *
     * @return la date de la transaction
     */
    public Date getDate() { return date; } // Retourne la date de la transaction
}
