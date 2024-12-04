package com.key.utpmatch.models.ranking;

import java.util.List;

public class RankingResponse {
    private String message;
    private List<RankingData> data;

    public RankingResponse(String message, List<RankingData> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RankingData> getData() {
        return data;
    }

    public void setData(List<RankingData> data) {
        this.data = data;
    }
}
