package com.atm.OOP.Bank;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Interface représentant un dépôt de comptes bancaires.
 * Fournit des méthodes pour interagir avec les comptes bancaires.
 */
public interface BankAccountRepository {

    /**
     * Trouve un compte bancaire par son numéro de compte et son code PIN.
     *
     * @param accountNumber Le numéro de compte bancaire.
     * @param pin Le code PIN associé au compte bancaire.
     * @return Un Optional contenant le compte bancaire s'il est trouvé, sinon un Optional vide.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    Optional<BankAccount> findByAccountNumberAndPin(String accountNumber, String pin) throws IOException;

    /**
     * Vérifie si un numéro de compte bancaire existe déjà.
     *
     * @param accountNumber Le numéro de compte bancaire à vérifier.
     * @return true si le numéro de compte existe, sinon false.
     */
    boolean isAccountNumberExists(String accountNumber);

    /**
     * Sauvegarde un compte bancaire.
     *
     * @param account Le compte bancaire à sauvegarder.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    void save(BankAccount account) throws IOException;

    /**
     * Récupère tous les comptes bancaires.
     *
     * @return Une liste de tous les comptes bancaires.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    List<BankAccount> getAllAccounts() throws IOException;
}