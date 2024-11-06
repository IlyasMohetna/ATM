package com.atm.OOP;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BankAccountRepository {
    Optional<BankAccount> findByAccountNumberAndPin(String accountNumber, String pin) throws IOException;
    boolean isAccountNumberExists(String accountNumber);
    void save(BankAccount account) throws IOException;
    List<BankAccount> getAllAccounts() throws IOException;
}