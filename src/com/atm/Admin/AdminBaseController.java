package com.atm.Admin;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import com.atm.OOP.NavigationService;
import com.atm.OOP.Admin.AdminService;

public abstract class AdminBaseController {
    @FXML
    private Parent root;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button usersButton;

    @FXML
    private Button addUserButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button btnSignout;

    public static AdminService adminService;

    @FXML
    public void initialize() {
        // addMenuEventHandlers();
    }

    public static void setAdminService(AdminService service) {
        adminService = service;
    }

    public static AdminService getAdminService() {
        return adminService;
    }

    protected void handleAction(String action) {
        switch (action) {
            case "analytics":
                NavigationService.navigateTo(getStage(), NavigationService.ANALYTICS);
                break;
            case "users":
                NavigationService.navigateTo(getStage(), NavigationService.USERS);
                break;
            case "addUser":
                NavigationService.navigateTo(getStage(), NavigationService.ADD_USER);
                break;
            case "logout":
                NavigationService.navigateTo(getStage(), NavigationService.LOGIN_ADMIN);
                break;
            default:
                System.err.println("Action inconnue");
        }
    }
    
    protected Stage getStage() {
        return root != null ? (Stage) root.getScene().getWindow() : null;
    }
}
