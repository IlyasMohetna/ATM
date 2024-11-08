package com.atm.OOP.Admin;

// Importation des classes nécessaires
import com.atm.OOP.Bank.BankAccount;
import com.atm.OOP.Bank.BankAccountRepository;
import com.atm.OOP.Bank.Transaction;

import java.io.IOException;
import java.util.List;

public class BankAnalytics {

    // Liste des comptes bancaires
    private List<BankAccount> accounts;
    // Compteur de dépôts
    private int depositCount;
    // Compteur de retraits
    private int withdrawCount;
    // Solde total de tous les comptes
    private double totalBalance;

    // Référence au dépôt de comptes bancaires
    private BankAccountRepository repository;

    // Constructeur de la classe qui initialise les variables et calcule les statistiques
    public BankAnalytics(BankAccountRepository repository) throws IOException {
        this.repository = repository;
        // Récupération de tous les comptes bancaires depuis le dépôt
        this.accounts = repository.getAllAccounts();
        // Calcul des statistiques initiales
        calculateStatistics();
    }

    // Méthode privée pour calculer les statistiques des comptes bancaires
    private void calculateStatistics() {
        // Initialisation des variables
        totalBalance = 0;
        depositCount = 0;
        withdrawCount = 0;

        // Parcours de tous les comptes bancaires
        for (BankAccount account : accounts) {
            // Ajout du solde du compte au solde total
            totalBalance += account.getBalance();

            // Parcours de toutes les transactions du compte
            for (Transaction transaction : account.getTransactions()) {
                // Incrémentation du compteur de dépôts si le type de transaction est un dépôt
                if ("deposit".equalsIgnoreCase(transaction.getType())) {
                    depositCount++;
                // Incrémentation du compteur de retraits si le type de transaction est un retrait
                } else if ("withdraw".equalsIgnoreCase(transaction.getType())) {
                    withdrawCount++;
                }
            }
        }
    }

    // Méthode pour obtenir le nombre total d'utilisateurs (comptes bancaires)
    public int getUserCount() {
        return accounts.size();
    }

    // Méthode pour obtenir le nombre total de dépôts
    public int getDepositCount() {
        return depositCount;
    }

    // Méthode pour obtenir le nombre total de retraits
    public int getWithdrawCount() {
        return withdrawCount;
    }

    // Méthode pour obtenir le solde total de tous les comptes bancaires
    public double getTotalBalance() {
        return totalBalance;
    }
}
