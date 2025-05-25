package com.example.model;

public abstract class User {

    protected int userId;
    protected String username;
    protected String password;
    protected String role;

    public User() {}

    public User(
        int userId,
        String username,
        String password,
        String role) {
            this.userId = userId;
            this.username = username;
            this.password = password;
            this.role = role;
    }

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    
    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
}

