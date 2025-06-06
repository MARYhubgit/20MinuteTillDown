package com.tilldawn.Control;

import com.tilldawn.Model.User;
import com.tilldawn.Model.UserManager;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreboardController {
    private final List<User> allUsers;

    public ScoreboardController() {
        this.allUsers = UserManager.getInstance().getTopUsers();
    }

    public List<User> getSortedByScore() {
        return allUsers.stream()
            .sorted(Comparator.comparingInt(User::getScore).reversed())
            .limit(10)
            .collect(Collectors.toList());
    }

    public List<User> getSortedByUsername() {
        return allUsers.stream()
            .sorted(Comparator.comparing(User::getUsername))
            .limit(10)
            .collect(Collectors.toList());
    }

    public List<User> getSortedByKills() {
        return allUsers.stream()
            .sorted(Comparator.comparingInt(User::getKills).reversed())
            .limit(10)
            .collect(Collectors.toList());
    }

    public List<User> getSortedBySurvivalTime() {
        return allUsers.stream()
            .sorted(Comparator.comparingInt(User::getSurvivalTimeInSeconds).reversed())
            .limit(10)
            .collect(Collectors.toList());
    }

    public String formatSurvivalTime(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return min + "m " + sec + "s";
    }
}
