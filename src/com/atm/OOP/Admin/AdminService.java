package com.atm.OOP.Admin; // Déclaration du package

import java.io.IOException; // Importation de la classe IOException

public class AdminService { // Déclaration de la classe AdminService
    private Admin admin; // Déclaration d'un champ privé de type Admin
    private AdminRepository repository; // Déclaration d'un champ privé de type AdminRepository

    // Constructeur de la classe AdminService qui initialise le repository
    public AdminService(AdminRepository repository) {
        this.repository = repository; // Initialisation du champ repository avec l'argument passé
    }

    // Méthode pour obtenir l'admin actuel
    public Admin getAdmin() {
        return admin; // Retourne l'admin actuel
    }

    // Méthode pour authentifier un admin avec email et mot de passe
    public void authenticate(String email, String password) throws IOException {
        // Recherche un admin par email et mot de passe dans le repository
        // Si aucun admin n'est trouvé, une exception IllegalArgumentException est levée
        admin = repository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    }

}
