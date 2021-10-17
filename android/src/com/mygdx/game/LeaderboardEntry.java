package com.mygdx.game;


public class LeaderboardEntry implements Comparable<LeaderboardEntry>{
    private String name;
    private int score;

    public LeaderboardEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public LeaderboardEntry() {
    }

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

    //descending order
    @Override
    public int compareTo(LeaderboardEntry o) {
        return o.score -this.score;
    }
}
