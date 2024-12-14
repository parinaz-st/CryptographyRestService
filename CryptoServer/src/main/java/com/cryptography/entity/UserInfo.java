package com.cryptography.entity;

import javax.persistence.*;
import java.util.Date;
@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    private Integer userBankCode;
    @Column(nullable = false)
    private Integer userBranchCode;
    @Column(nullable = false)
    private Date userCreationDate;
    @Column(nullable = false)
    private boolean isActive;
    @Column(nullable = false)
    private Date lastLoggedIn;
    @Column(nullable = false)
    private boolean firstLogin;

    public UserInfo(String username, String password, String role, Integer userBankCode, Integer userBranchCode, Date userCreationDate, boolean isActive, Date lastLoggedIn, boolean firstLogin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.userBankCode = userBankCode;
        this.userBranchCode = userBranchCode;
        this.userCreationDate = userCreationDate;
        this.isActive = isActive;
        this.lastLoggedIn = lastLoggedIn;
        this.firstLogin = firstLogin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getUserBankCode() {
        return userBankCode;
    }

    public void setUserBankCode(Integer userBankCode) {
        this.userBankCode = userBankCode;
    }

    public Integer getUserBranchCode() {
        return userBranchCode;
    }

    public void setUserBranchCode(Integer userBranchCode) {
        this.userBranchCode = userBranchCode;
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

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public UserInfo() {
    }

}
