package com.bookexchange.model;

public class User {
    private String username;
    private String password;
    private String role;

    // Add constructor
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Add getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
