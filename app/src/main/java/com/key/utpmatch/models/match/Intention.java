package com.key.utpmatch.models.match;

public class Intention {
    private int intention_id;
    private String name;
    private boolean is_active;

    // Getters y setters

    public int getIntention_id() {
        return intention_id;
    }

    public void setIntention_id(int intention_id) {
        this.intention_id = intention_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}
