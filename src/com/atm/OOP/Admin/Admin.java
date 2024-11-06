package com.atm.OOP.Admin;

public class Admin {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String avatar;

    public Admin(String email, String password, String firstname, String lastname, String avatar) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.avatar = avatar;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getAvatar() { return avatar; }
}