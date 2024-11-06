package com.atm.OOP.Admin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class AdminRepository {
    private static final String SRC_ADMIN_DATA_PATH = "data/admin.json";
    private static final String LOCAL_ADMIN_DATA_PATH = System.getProperty("user.home") + "/ATMApp/admin.json";

    public AdminRepository() {
        try {
            initializeDataFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Admin> findByEmailAndPassword(String email, String password) throws IOException {
        JSONArray users = readUsersFromFile();

        for (int i = 0; i < users.length(); i++) {
            JSONObject adminJson = users.getJSONObject(i);
            if (adminJson.getString("email").equals(email) && adminJson.getString("password").equals(password)) {
                Admin admin = parseAdminAccount(adminJson);
                return Optional.of(admin);
            }
        }

        return Optional.empty();
    }

    private JSONArray readUsersFromFile() throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(LOCAL_ADMIN_DATA_PATH)));
        return new JSONArray(content);
    }

    private Admin parseAdminAccount(JSONObject adminJson) {
        String email = adminJson.getString("email");
        String password = adminJson.getString("password");
        String firstname = adminJson.getString("firstname");
        String lastname = adminJson.getString("lastname");
        String avatar = adminJson.getString("avatar");

        return new Admin(email, password, firstname, lastname, avatar);
    }

    private void initializeDataFile() throws IOException {
        java.nio.file.Path localPath = Paths.get(LOCAL_ADMIN_DATA_PATH);
    
        if (!Files.exists(localPath)) {
            Files.createDirectories(localPath.getParent());
            try (InputStream is = getClass().getClassLoader().getResourceAsStream(SRC_ADMIN_DATA_PATH)) {
                if (is == null) throw new FileNotFoundException("Resource not found: " + SRC_ADMIN_DATA_PATH);
                Files.copy(is, localPath);
            }
        }
    }
}
