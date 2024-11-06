package com.atm.OOP.Admin;

import java.io.IOException;

public class AdminService {
    private Admin admin;
    private AdminRepository repository;

    public AdminService(AdminRepository repository) {
        this.repository = repository;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void authenticate(String email, String password) throws IOException {
        admin = repository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    }

}
