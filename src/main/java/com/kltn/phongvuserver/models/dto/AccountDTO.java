package com.kltn.phongvuserver.models.dto;

import com.kltn.phongvuserver.models.AccountPermission;

public class AccountDTO {
    private String username;
    private String password;
    private boolean active;
    private AccountPermission permission;
    private UserDTO user;

    public AccountDTO() {
    }

    public AccountDTO(String username, String password, boolean active, AccountPermission permission, UserDTO user) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.permission = permission;
        this.user = user;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public AccountPermission getPermission() {
        return permission;
    }

    public void setPermission(AccountPermission permission) {
        this.permission = permission;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
