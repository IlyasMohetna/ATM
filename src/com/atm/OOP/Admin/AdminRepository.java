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
    // Chemin du fichier source des données des administrateurs
    private static final String SRC_ADMIN_DATA_PATH = "data/admin.json";
    // Chemin local du fichier des données des administrateurs
    private static final String LOCAL_ADMIN_DATA_PATH = System.getProperty("user.home") + "/ATMApp/admin.json";

    // Constructeur de la classe AdminRepository
    public AdminRepository() {
        try {
            // Initialisation du fichier de données
            initializeDataFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour trouver un administrateur par email et mot de passe
    public Optional<Admin> findByEmailAndPassword(String email, String password) throws IOException {
        // Lecture des utilisateurs à partir du fichier
        JSONArray users = readUsersFromFile();

        // Parcours de la liste des utilisateurs
        for (int i = 0; i < users.length(); i++) {
            JSONObject adminJson = users.getJSONObject(i);
            // Vérification de l'email et du mot de passe
            if (adminJson.getString("email").equals(email) && adminJson.getString("password").equals(password)) {
                // Conversion du JSON en objet Admin
                Admin admin = parseAdminAccount(adminJson);
                return Optional.of(admin);
            }
        }

        // Retourne un Optional vide si aucun administrateur n'est trouvé
        return Optional.empty();
    }

    // Méthode pour lire les utilisateurs à partir du fichier
    private JSONArray readUsersFromFile() throws IOException {
        // Lecture du contenu du fichier en tant que chaîne de caractères
        String content = new String(Files.readAllBytes(Paths.get(LOCAL_ADMIN_DATA_PATH)));
        // Conversion du contenu en JSONArray
        return new JSONArray(content);
    }

    // Méthode pour convertir un JSONObject en objet Admin
    private Admin parseAdminAccount(JSONObject adminJson) {
        String email = adminJson.getString("email");
        String password = adminJson.getString("password");
        String firstname = adminJson.getString("firstname");
        String lastname = adminJson.getString("lastname");
        String avatar = adminJson.getString("avatar");

        // Création et retour d'un nouvel objet Admin
        return new Admin(email, password, firstname, lastname, avatar);
    }

    // Méthode pour initialiser le fichier de données
    private void initializeDataFile() throws IOException {
        java.nio.file.Path localPath = Paths.get(LOCAL_ADMIN_DATA_PATH);

        // Vérification si le fichier n'existe pas
        if (!Files.exists(localPath)) {
            // Création des répertoires nécessaires
            Files.createDirectories(localPath.getParent());
            // Lecture du fichier source depuis les ressources
            try (InputStream is = getClass().getClassLoader().getResourceAsStream(SRC_ADMIN_DATA_PATH)) {
                if (is == null) throw new FileNotFoundException("Resource not found: " + SRC_ADMIN_DATA_PATH);
                // Copie du fichier source vers le chemin local
                Files.copy(is, localPath);
            }
        }
    }
}
