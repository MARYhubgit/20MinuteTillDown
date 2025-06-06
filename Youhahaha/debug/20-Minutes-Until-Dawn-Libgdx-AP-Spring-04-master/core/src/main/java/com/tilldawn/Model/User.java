package com.tilldawn.Model;

public class User {
    private String username;
    private String avatarPath;
    private int score;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    private int kills;
    private int survivalTimeInSeconds;

    public User() {
    }
    public User(String username, String avatarPath, String password, String securityQuestion, String securityAnswer) {
        this.username = username;
        this.avatarPath = avatarPath;
        this.score = 0;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }


    public String getUsername() {
        return username;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public int getScore() {
        return score;
    }


    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSecurityQuestion() {
        return securityQuestion;
    }
    public String getSecurityAnswer() {
        return securityAnswer;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public int getKills() {
        return kills;
    }

    public int getSurvivalTimeInSeconds() {
        return survivalTimeInSeconds;
    }
}
