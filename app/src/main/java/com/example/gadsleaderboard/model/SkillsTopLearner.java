package com.example.gadsleaderboard.model;

public class SkillsTopLearner extends TopLearner {
    private int score;

    public SkillsTopLearner(String name, String country, int score) {
        super(name, country);
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
