package com.atm.OOP.Admin;

/**
 * La classe Admin représente un administrateur avec un email, un mot de passe, un prénom, un nom de famille et un avatar.
 * Cette classe permet de créer un objet Admin avec des informations personnelles et de récupérer ces informations via des méthodes getter.
 */
public class Admin {
    // Déclaration des variables d'instance pour stocker les informations de l'administrateur
    private String email; // L'email de l'administrateur
    private String password; // Le mot de passe de l'administrateur
    private String firstname; // Le prénom de l'administrateur
    private String lastname; // Le nom de famille de l'administrateur
    private String avatar; // L'avatar de l'administrateur

    /**
     * Construit un nouvel Admin avec les détails spécifiés.
     *
     * @param email l'email de l'administrateur
     * @param password le mot de passe de l'administrateur
     * @param firstname le prénom de l'administrateur
     * @param lastname le nom de famille de l'administrateur
     * @param avatar l'avatar de l'administrateur
     */
    public Admin(String email, String password, String firstname, String lastname, String avatar) {
        // Initialisation des variables d'instance avec les valeurs fournies en paramètres
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.avatar = avatar;
    }

    /**
     * Retourne l'email de l'administrateur.
     *
     * @return l'email de l'administrateur
     */
    public String getEmail() { return email; }

    /**
     * Retourne le mot de passe de l'administrateur.
     *
     * @return le mot de passe de l'administrateur
     */
    public String getPassword() { return password; }

    /**
     * Retourne le prénom de l'administrateur.
     *
     * @return le prénom de l'administrateur
     */
    public String getFirstname() { return firstname; }

    /**
     * Retourne le nom de famille de l'administrateur.
     *
     * @return le nom de famille de l'administrateur
     */
    public String getLastname() { return lastname; }

    /**
     * Retourne l'avatar de l'administrateur.
     *
     * @return l'avatar de l'administrateur
     */
    public String getAvatar() { return avatar; }
}