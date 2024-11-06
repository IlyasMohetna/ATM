package com.atm.OOP.Admin;

import com.atm.OOP.Bank.BankAccount;
import com.atm.OOP.Bank.BankAccountRepository;
import com.atm.OOP.Bank.Transaction;

import java.io.IOException;
import java.util.List;

public class BankAnalytics {

    private List<BankAccount> accounts;
    private int depositCount;
    private int withdrawCount;
    private double totalBalance;

    private BankAccountRepository repository;

    public BankAnalytics(BankAccountRepository repository) throws IOException {
        this.repository = repository;
        this.accounts = repository.getAllAccounts();
        calculateStatistics();
    }

    private void calculateStatistics() {
        totalBalance = 0;
        depositCount = 0;
        withdrawCount = 0;

        for (BankAccount account : accounts) {
            totalBalance += account.getBalance();

            for (Transaction transaction : account.getTransactions()) {
                if ("deposit".equalsIgnoreCase(transaction.getType())) {
                    depositCount++;
                } else if ("withdraw".equalsIgnoreCase(transaction.getType())) {
                    withdrawCount++;
                }
            }
        }
    }

    public int getUserCount() {
        return accounts.size();
    }

    public int getDepositCount() {
        return depositCount;
    }

    public int getWithdrawCount() {
        return withdrawCount;
    }

    public double getTotalBalance() {
        return totalBalance;
    }
}
