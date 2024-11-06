package com.atm.Client;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;

import com.atm.OOP.Bank.BankAccountService;
import com.atm.Utils.NavigationService;
import com.atm.Utils.SoundService;

public abstract class BaseController {

    @FXML
    private Parent root;

    public static BankAccountService bankAccountService;

    public static void setBankAccountService(BankAccountService service) {
        bankAccountService = service;
    }

    public static BankAccountService getBankAccountService() {
        return bankAccountService;
    }

    @FXML
    public void initialize() {
        SoundService.initializeSound();
        addSoundToButtons(root);
    }

    protected Stage getStage() {
        return root != null ? (Stage) root.getScene().getWindow() : null;
    }

    private void addSoundToButtons(Node node) {

        if (node instanceof Button) {
            Button button = (Button) node;
    
            button.addEventHandler(ActionEvent.ACTION, event -> {
                SoundService.playButtonSound();
            });
        }
    
        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            for (Node child : parent.getChildrenUnmodifiable()) {
                addSoundToButtons(child);
            }
        }
    }

    protected void handleAction(String action) {
        switch (action) {
            case "menu":
                NavigationService.navigateTo(getStage(), NavigationService.MENU);
                break;
            case "deposit":
                NavigationService.navigateTo(getStage(), NavigationService.DEPOSIT);
                break;
            case "changePin":
                NavigationService.navigateTo(getStage(), NavigationService.CHANGE_PIN);
                break;
            case "logout":
                NavigationService.navigateTo(getStage(), NavigationService.LOGIN);
                break;
            case "withdraw":
                NavigationService.navigateTo(getStage(), NavigationService.WITHDRAW);
                break;
            case "statement":
                NavigationService.openModal(getStage(), NavigationService.STATEMENT);
                break;
            case "balance":
                NavigationService.navigateTo(getStage(), NavigationService.BALANCE);
                break;
            default:
                System.err.println("Action inconnue");
        }
    }
    
}
