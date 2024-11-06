package com.atm.OOP;

import java.io.IOException;
import java.util.Optional;

public interface BankAccountRepository {
    Optional<BankAccount> findByAccountNumberAndPin(String accountNumber, String pin) throws IOException;
    boolean isAccountNumberExists(String accountNumber) throws IOException;
    void save(BankAccount account) throws IOException;
}