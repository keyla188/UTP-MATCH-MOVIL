package com.key.utpmatch.models.auth;

import java.util.Date;

public class UserData {
    private String user_id;
    private String email;
    private String password;
    private boolean terms_conditions;
    private boolean is_active;
    private Date createdAt;
    private Date updatedAt;

    // Getters y setters
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTerms_conditions() {
        return terms_conditions;
    }

    public void setTerms_conditions(boolean terms_conditions) {
        this.terms_conditions = terms_conditions;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
