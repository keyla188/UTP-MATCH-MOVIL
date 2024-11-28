package com.key.utpmatch.models.user;

public class UserResponse {
    private String message;
    private UserDetail data;

    // Getters y setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDetail getData() {
        return data;
    }

    public void setData(UserDetail data) {
        this.data = data;
    }
}
