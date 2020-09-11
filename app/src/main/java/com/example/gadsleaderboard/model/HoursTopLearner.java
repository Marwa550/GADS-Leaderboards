package com.example.gadsleaderboard.model;

public class HoursTopLearner extends TopLearner {
    private int hours;

    public HoursTopLearner(String name, String country, int hours) {
        super(name, country);
        this.hours = hours;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
