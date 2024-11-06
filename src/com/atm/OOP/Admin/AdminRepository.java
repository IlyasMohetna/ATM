package com.atm.OOP.Admin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class AdminRepository {
    private static final String DATA_PATH = "data/admin.json";

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
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(DATA_PATH)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: data/admin.json");
            }
            String content = new String(is.readAllBytes());
            return new JSONArray(content);
        }
    }

    private Admin parseAdminAccount(JSONObject adminJson) {
        String email = adminJson.getString("email");
        String password = adminJson.getString("password");
        String firstname = adminJson.getString("firstname");
        String lastname = adminJson.getString("lastname");
        String avatar = adminJson.getString("avatar");

        return new Admin(email, password, firstname, lastname, avatar);
    }
}
