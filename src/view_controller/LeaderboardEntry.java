package view_controller;

import java.io.Serializable;

public class LeaderboardEntry implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes

    private String name;
    private int score;

    public LeaderboardEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return name + ": " + score;
    }
}

