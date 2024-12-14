package com.cryptography.dto;

import java.util.Date;

public class UserInfoDto {
   private String username;
   private String password;
   private String role;
   private int userBankCode;
   private int branchCode;
   private Date userCreationDate;
   private boolean isActive;
   private Date lastLoggedIn;
   private boolean firstLoginPasswordChanged;

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

    public int getUserBankCode() {
        return userBankCode;
    }

    public void setUserBankCode(int userBankCode) {
        this.userBankCode = userBankCode;
    }

    public int getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(int branchCode) {
        this.branchCode = branchCode;
    }

    public Date getUserCreationDate() {
        return userCreationDate;
    }

    public void setUserCreationDate(Date userCreationDate) {
        this.userCreationDate = userCreationDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(Date lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public boolean isFirstLoginPasswordChanged() {
        return firstLoginPasswordChanged;
    }

    public void setFirstLoginPasswordChanged(boolean firstLoginPasswordChanged) {
        this.firstLoginPasswordChanged = firstLoginPasswordChanged;
    }
}
