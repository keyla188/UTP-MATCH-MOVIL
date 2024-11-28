package com.key.utpmatch.models.match;

import java.util.List;

public class MyMatchResponse {
    private int page;
    private int limit;
    private String message;
    private List<MatchData> data;

    // Getters y Setters
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MatchData> getData() {
        return data;
    }

    public void setData(List<MatchData> data) {
        this.data = data;
    }
}

