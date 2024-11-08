package com.atm.OOP.Bank;

import java.io.IOException;
import java.util.Date;

/**
 * Service class pour la gestion des comptes bancaires.
 */
public class BankAccountService {
    private BankAccount bankAccount; // Compte bancaire actuel
    private BankAccountRepository repository; // Référentiel pour interagir avec les comptes bancaires

    /**
     * Constructeur pour BankAccountService.
     * 
     * @param repository le référentiel pour interagir avec les comptes bancaires
     */
    public BankAccountService(BankAccountRepository repository) {
        this.repository = repository;
    }

    /**
     * Obtient le compte bancaire actuel.
     * 
     * @return le compte bancaire actuel
     */
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    /**
     * Authentifie un compte bancaire en utilisant le numéro de compte et le code PIN.
     * 
     * @param accountNumber le numéro de compte
     * @param pin le code PIN
     * @throws IOException si une erreur d'I/O se produit
     */
    public void authenticate(String accountNumber, String pin) throws IOException {
        // Recherche le compte bancaire par numéro de compte et code PIN
        bankAccount = repository.findByAccountNumberAndPin(accountNumber, pin)
                .orElseThrow(() -> new IllegalArgumentException("Numéro de compte ou code PIN incorrect"));
    }

    /**
     * Dépose un montant spécifié dans le compte bancaire.
     * 
     * @param amount le montant à déposer
     * @throws IOException si une erreur d'I/O se produit
     * @throws IllegalArgumentException si le montant du dépôt est invalide
     */
    public void deposit(double amount) throws IOException {
        // Vérifie si le montant est valide
        if (amount <= 0) throw new IllegalArgumentException("Le montant du dépot est invalide");
        // Met à jour le solde du compte bancaire
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        // Ajoute une transaction de dépôt
        bankAccount.addTransaction(new Transaction("deposit", amount, new Date()));
        // Sauvegarde le compte bancaire mis à jour
        repository.save(bankAccount);
    }

    /**
     * Retire un montant spécifié du compte bancaire.
     * 
     * @param amount le montant à retirer
     * @throws IOException si une erreur d'I/O se produit
     * @throws IllegalArgumentException si le montant du retrait est invalide ou si les fonds sont insuffisants
     */
    public void withdraw(double amount) throws IOException {
        // Vérifie si le montant est valide
        if (amount <= 0) throw new IllegalArgumentException("Le montant du retrait est invalide");
        // Vérifie si le solde est suffisant
        if (amount > bankAccount.getBalance()) throw new IllegalArgumentException("Solde insuffisant");
        // Met à jour le solde du compte bancaire
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        // Ajoute une transaction de retrait
        bankAccount.addTransaction(new Transaction("withdraw", amount, new Date()));
        // Sauvegarde le compte bancaire mis à jour
        repository.save(bankAccount);
    }

    /**
     * Change le code PIN du compte bancaire.
     * 
     * @param currentPin le code PIN actuel
     * @param newPin le nouveau code PIN
     * @param confirmPin la confirmation du nouveau code PIN
     * @throws IOException si une erreur d'I/O se produit
     * @throws IllegalArgumentException si le nouveau code PIN est invalide ou ne correspond pas à la confirmation
     */
    public void changePin(String currentPin, String newPin, String confirmPin) throws IOException {
        // Vérifie si le nouveau code PIN contient 4 chiffres
        if (newPin.length() != 4) throw new IllegalArgumentException("Le nouveau code PIN doit contenir 4 chiffres.");
        // Vérifie si le nouveau code PIN est différent de l'ancien
        if (currentPin.equals(newPin)) throw new IllegalArgumentException("Le nouveau code PIN doit être différent de l'ancien.");
        // Vérifie si le code PIN actuel est correct
        if (!bankAccount.getPin().equals(currentPin)) throw new IllegalArgumentException("Le code PIN actuel est incorrect.");
        // Vérifie si les nouveaux codes PIN correspondent
        if (!newPin.equals(confirmPin)) throw new IllegalArgumentException("Les nouveaux codes PIN ne correspondent pas.");
        // Met à jour le code PIN du compte bancaire
        bankAccount.setPin(newPin);
        // Sauvegarde le compte bancaire mis à jour
        repository.save(bankAccount);
    }
}
