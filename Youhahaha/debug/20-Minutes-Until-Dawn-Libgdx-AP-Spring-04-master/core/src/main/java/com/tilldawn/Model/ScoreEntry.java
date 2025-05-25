package com.tilldawn.Model;

public class ScoreEntry {
    private String username;
    private int score;
    private float survivedTime;
    private int enemiesKilled;
    private int xpEarned;

    public ScoreEntry() {}

    public ScoreEntry(String username, int score, float survivedTime, int enemiesKilled, int xpEarned) {
        this.username = username;
        this.score = score;
        this.survivedTime = survivedTime;
        this.enemiesKilled = enemiesKilled;
        this.xpEarned = xpEarned;
    }

    public String getUsername() { return username; }
    public int getScore() { return score; }
    public float getSurvivedTime() { return survivedTime; }
    public int getEnemiesKilled() { return enemiesKilled; }
    public int getXpEarned() { return xpEarned; }
}
