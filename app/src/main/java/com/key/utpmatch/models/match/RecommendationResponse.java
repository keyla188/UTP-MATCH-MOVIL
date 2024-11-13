package com.key.utpmatch.models.match;

import java.util.List;

public class RecommendationResponse {
    private String message;
    private List<UserRecomendation> data;

    // Getters y setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserRecomendation> getData() {
        return data;
    }

    public void setData(List<UserRecomendation> data) {
        this.data = data;
    }
}
