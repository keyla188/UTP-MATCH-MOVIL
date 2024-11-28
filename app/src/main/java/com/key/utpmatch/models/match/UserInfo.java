package com.key.utpmatch.models.match;

public class UserInfo {  // Cambié 'class UserInfo' a 'public class UserInfo'
    private String user_id;
    private String name;
    private String lastname;
    private String description;
    private String birthdate;
    private String contact_phone;
    private String createdAt;
    private String updatedAt;

    public UserInfo(String user_id, String name, String lastname, String description, String birthdate, String contact_phone, String createdAt, String updatedAt) {
        this.user_id = user_id;
        this.name = name;
        this.lastname = lastname;
        this.description = description;
        this.birthdate = birthdate;
        this.contact_phone = contact_phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters y Setters
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
