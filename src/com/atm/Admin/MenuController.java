package com.atm.Admin;

import com.atm.OOP.Admin.AdminService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuController extends AdminBaseController {

    @FXML
    private Label fullnameLabel;

    @FXML
    private ImageView avatarImageView;

    @FXML
    private Button analyticsButton;
    
    @FXML
    private Button usersButton;

    @FXML
    private Button addUserButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button btnSignout;

    @FXML
    public void initialize() {
        super.initialize();

        analyticsButton.setOnAction(event -> handleAction("analytics"));
        usersButton.setOnAction(event -> handleAction("users"));
        addUserButton.setOnAction(event -> handleAction("addUser"));
        // settingsButton.setOnAction(event -> handleAction("settings"));
        btnSignout.setOnAction(event -> handleAction("logout"));

        loadUserDetails();
    }

    private void loadUserDetails() {
        AdminService service = getAdminService();
        if (service != null && service.getAdmin() != null) {
            fullnameLabel.setText(service.getAdmin().getFirstname()+ " " + service.getAdmin().getLastname());
            String avatarPath = service.getAdmin().getAvatar();
            if (avatarPath != null && !avatarPath.isEmpty()) {
                Image avatarImage = new Image(getClass().getResourceAsStream(avatarPath));
                avatarImageView.setImage(avatarImage);
            }
        } else {
            fullnameLabel.setText("Unknown User");
        }
    }
}
