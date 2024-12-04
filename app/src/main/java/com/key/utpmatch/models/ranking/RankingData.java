package com.key.utpmatch.models.ranking;

public class RankingData {

    private String user_id;
    private String name;
    private String lastname;
    private String photo_url;
    private int total_matches;

    public RankingData(String user_id, String name, String lastname, String photo_url, int total_matches) {
        this.user_id = user_id;
        this.name = name;
        this.lastname = lastname;
        this.photo_url = photo_url;
        this.total_matches = total_matches;
    }

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

    public String getFullName() {
        return name + " " + lastname;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public int getTotal_matches() {
        return total_matches;
    }

    public void setTotal_matches(int total_matches) {
        this.total_matches = total_matches;
    }
}
