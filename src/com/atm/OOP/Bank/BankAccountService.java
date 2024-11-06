package com.atm.OOP.Bank;

import java.io.IOException;
import java.util.Date;

public class BankAccountService {
    private BankAccount bankAccount;
    private BankAccountRepository repository;

    public BankAccountService(BankAccountRepository repository) {
        this.repository = repository;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void authenticate(String accountNumber, String pin) throws IOException {
        bankAccount = repository.findByAccountNumberAndPin(accountNumber, pin)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    }

    public void deposit(double amount) throws IOException {
        if (amount <= 0) throw new IllegalArgumentException("Le montant du dépot est invalide");
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccount.addTransaction(new Transaction("deposit", amount, new Date()));
        repository.save(bankAccount);
    }

    public void withdraw(double amount) throws IOException {
        if (amount <= 0) throw new IllegalArgumentException("Le montant du retrait est invalide");
        if (amount > bankAccount.getBalance()) throw new IllegalArgumentException("Solde insuffisant");
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccount.addTransaction(new Transaction("withdraw", amount, new Date()));
        repository.save(bankAccount);
    }

    public void changePin(String currentPin, String newPin, String confirmPin) throws IOException {
        if (newPin.length() != 4) throw new IllegalArgumentException("Le nouveau code PIN doit contenir 4 chiffres.");
        if (currentPin.equals(newPin)) throw new IllegalArgumentException("Le nouveau code PIN doit être différent de l'ancien.");
        if (!bankAccount.getPin().equals(currentPin)) throw new IllegalArgumentException("Le code PIN actuel est incorrect.");
        if (!newPin.equals(confirmPin)) throw new IllegalArgumentException("Les nouveaux codes PIN ne correspondent pas.");
        bankAccount.setPin(newPin);
        repository.save(bankAccount);
    }
}
