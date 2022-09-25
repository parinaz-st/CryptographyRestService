package com.cryptography.dto;

public class UserResDo {

    private String username;
    private String password;
    private String role;
    private boolean active;
    private String status;

    public UserResDo() {
    }

    public UserResDo(String username, String password, String role, boolean active, String status) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = active;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
