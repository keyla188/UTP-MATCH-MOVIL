package com.key.utpmatch.models.match;

public class MatchData {  // Cambié 'class MatchData' a 'public class MatchData'
    private String match_id;
    private UserInfo transmitter;
    private UserInfo receiver;

    public MatchData(String match_id, UserInfo receiver) {
        this.match_id = match_id;
        this.transmitter = null;
        this.receiver = receiver;
    }

    // Getters y Setters
    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public UserInfo getTransmitter() {
        return transmitter;
    }

    public void setTransmitter(UserInfo transmitter) {
        this.transmitter = transmitter;
    }

    public UserInfo getReceiver() {
        return receiver;
    }

    public void setReceiver(UserInfo receiver) {
        this.receiver = receiver;
    }
}
