package com.project.Backend.VOs;

/**
 * Helper class to pass error reason or user info back to frontEnd on user login
 */
public class LoginVO {
    private boolean status;
    private String token;
    private String error;
    private String role;
    private String username;

    public LoginVO(boolean status, String token, String error, String role, String username) {
        this.status = status;
        this.token = token;
        this.error = error;
        this.role = role;
        this.username = username;
    }

    public LoginVO() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
