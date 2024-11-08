package com.atm.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Pos;

import java.util.List;
import java.util.Comparator;

import com.atm.OOP.Bank.BankAccount;
import com.atm.OOP.Bank.Transaction;

/**
 * Classe StatementController : Contrôleur pour la vue de relevé bancaire.
 * Cette classe gère l'affichage des informations de compte (solde, numéro, titulaire)
 * ainsi que la liste des transactions associées.
 */
public class StatementController extends BaseController {

    @FXML
    private VBox transactionContainer; // Conteneur pour afficher chaque transaction

    @FXML
    private Button exitButton; // Bouton de sortie de l'interface

    @FXML
    private Text balanceText; // Texte affichant le solde du compte

    @FXML 
    private Text accountNumberText; // Texte affichant le numéro de compte

    @FXML
    private Text accountHolderText; // Texte affichant le nom du titulaire du compte

    /**
     * Méthode initialize() : Initialise les composants et les données du contrôleur.
     * Cette méthode est appelée automatiquement après le chargement du fichier FXML.
     */
    @FXML
    public void initialize() {
        super.initialize(); // Appelle l'initialisation de la classe parente

        setUserDetails(); // Définit les détails de l'utilisateur
        loadTransactions(); // Charge et affiche les transactions
    }

    /**
     * Méthode setUserDetails() : Affiche les informations de base du compte utilisateur,
     * y compris le solde, le numéro de compte et le nom du titulaire.
     */
    private void setUserDetails() {
        // Récupère les informations du compte bancaire via le service
        BankAccount bankAccount = getBankAccountService().getBankAccount();
        // Formate et affiche le solde du compte
        balanceText.setText(String.format("%.2f €", bankAccount.getBalance()));
        // Affiche le numéro de compte
        accountNumberText.setText(bankAccount.getAccountNumber());
        // Affiche le nom complet du titulaire (prénom + nom de famille)
        accountHolderText.setText(bankAccount.getFirstname() + " " + bankAccount.getLastname());
    }

    /**
     * Méthode loadTransactions() : Charge et affiche la liste des transactions
     * du compte bancaire dans le conteneur 'transactionContainer'.
     * Chaque transaction est affichée avec la date, le type (dépôt ou retrait)
     * et le montant.
     */
    private void loadTransactions() {
        BankAccount bankAccount = getBankAccountService().getBankAccount();
        List<Transaction> transactions = bankAccount.getTransactions();

        // Trie les transactions par date dans l'ordre décroissant
        transactions.sort(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t1, Transaction t2) {
                return t2.getDate().compareTo(t1.getDate()); // Compare pour obtenir l'ordre décroissant
            }    
        });

        // Alternativement, on peut utiliser une expression lambda :
        // transactions.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));

        for (Transaction transaction : transactions) {
            // Crée une nouvelle HBox pour chaque transaction pour organiser la date, le type, et le montant
            HBox transactionRow = new HBox();
            transactionRow.setPrefWidth(400); // Définit une largeur fixe pour l'alignement
            transactionRow.setAlignment(Pos.CENTER_LEFT); // Aligne le contenu à gauche
            transactionRow.setSpacing(10); // Espace entre les éléments

            // Formatage des informations de la transaction : date, type, montant
            String date = BankAccount.DATE_FORMAT.format(transaction.getDate()); // Formate la date
            String type = transaction.getType(); // Type de transaction (dépôt/retrait)
            String amount = String.format("%,.2f €", transaction.getAmount()); // Formate le montant

            // Crée des labels pour chaque information avec une largeur spécifique
            Label dateLabel = new Label(date);
            Label typeLabel = new Label(type);
            Label amountLabel = new Label(amount);

            // Définit des largeurs fixes pour contrôler l'alignement
            dateLabel.setPrefWidth(120); // Ajuste la largeur pour la date
            typeLabel.setPrefWidth(150); // Ajuste la largeur pour le type
            typeLabel.setAlignment(Pos.CENTER); // Centre le texte du type
            amountLabel.setPrefWidth(100); // Largeur pour le montant
            amountLabel.setAlignment(Pos.CENTER_RIGHT); // Alignement du montant à droite

            // Style pour le montant : vert pour un dépôt, rouge pour un retrait
            amountLabel.setStyle("-fx-text-fill: " + (transaction.getType().equalsIgnoreCase("deposit") ? "green;" : "red;"));

            // Ajoute les labels au 'transactionRow' (ligne de transaction)
            transactionRow.getChildren().addAll(dateLabel, typeLabel, amountLabel);

            // Ajoute la ligne de transaction au conteneur principal
            transactionContainer.getChildren().add(transactionRow);
        }
    }
}