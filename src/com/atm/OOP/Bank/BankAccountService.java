package com.atm.OOP.Bank;

import java.io.IOException;
import java.util.Date;

/**
 * Service class for managing bank accounts.
 */
public class BankAccountService {
    private BankAccount bankAccount;
    private BankAccountRepository repository;

    /**
     * Constructor for BankAccountService.
     * 
     * @param repository the repository to interact with bank accounts
     */
    public BankAccountService(BankAccountRepository repository) {
        this.repository = repository;
    }

    /**
     * Gets the current bank account.
     * 
     * @return the current bank account
     */
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    /**
     * Authenticates a bank account using account number and PIN.
     * 
     * @param accountNumber the account number
     * @param pin the PIN code
     * @throws IOException if an I/O error occurs
     */
    public void authenticate(String accountNumber, String pin) throws IOException {
        bankAccount = repository.findByAccountNumberAndPin(accountNumber, pin)
                .orElseThrow(() -> new IllegalArgumentException("Numéro de compte ou code PIN incorrect"));
    }

    /**
     * Deposits a specified amount into the bank account.
     * 
     * @param amount the amount to deposit
     * @throws IOException if an I/O error occurs
     * @throws IllegalArgumentException if the deposit amount is invalid
     */
    public void deposit(double amount) throws IOException {
        if (amount <= 0) throw new IllegalArgumentException("Le montant du dépot est invalide");
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccount.addTransaction(new Transaction("deposit", amount, new Date()));
        repository.save(bankAccount);
    }

    /**
     * Withdraws a specified amount from the bank account.
     * 
     * @param amount the amount to withdraw
     * @throws IOException if an I/O error occurs
     * @throws IllegalArgumentException if the withdrawal amount is invalid or insufficient funds
     */
    public void withdraw(double amount) throws IOException {
        if (amount <= 0) throw new IllegalArgumentException("Le montant du retrait est invalide");
        if (amount > bankAccount.getBalance()) throw new IllegalArgumentException("Solde insuffisant");
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccount.addTransaction(new Transaction("withdraw", amount, new Date()));
        repository.save(bankAccount);
    }

    /**
     * Changes the PIN of the bank account.
     * 
     * @param currentPin the current PIN
     * @param newPin the new PIN
     * @param confirmPin the confirmation of the new PIN
     * @throws IOException if an I/O error occurs
     * @throws IllegalArgumentException if the new PIN is invalid or does not match the confirmation
     */
    public void changePin(String currentPin, String newPin, String confirmPin) throws IOException {
        if (newPin.length() != 4) throw new IllegalArgumentException("Le nouveau code PIN doit contenir 4 chiffres.");
        if (currentPin.equals(newPin)) throw new IllegalArgumentException("Le nouveau code PIN doit être différent de l'ancien.");
        if (!bankAccount.getPin().equals(currentPin)) throw new IllegalArgumentException("Le code PIN actuel est incorrect.");
        if (!newPin.equals(confirmPin)) throw new IllegalArgumentException("Les nouveaux codes PIN ne correspondent pas.");
        bankAccount.setPin(newPin);
        repository.save(bankAccount);
    }
}
