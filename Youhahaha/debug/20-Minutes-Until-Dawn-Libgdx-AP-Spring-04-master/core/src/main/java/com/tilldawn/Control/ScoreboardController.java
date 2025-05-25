package com.tilldawn.Control;

import com.tilldawn.Model.ScoreEntry;
import com.tilldawn.Model.ScoreboardManager;

import java.util.Comparator;
import java.util.List;

public class ScoreboardController {
    private ScoreboardManager scoreboardManager;

    public ScoreboardController() {
        this.scoreboardManager = ScoreboardManager.getInstance();
    }

    public List<ScoreEntry> getSortedByScore() {
        return scoreboardManager.getTopScores(10);
    }

    public List<ScoreEntry> getSortedByUsername() {
        List<ScoreEntry> scores = scoreboardManager.loadScores();
        scores.sort(Comparator.comparing(ScoreEntry::getUsername));
        return scores.subList(0, Math.min(10, scores.size()));
    }

    public List<ScoreEntry> getSortedByKills() {
        List<ScoreEntry> scores = scoreboardManager.loadScores();
        scores.sort(Comparator.comparingInt(ScoreEntry::getEnemiesKilled).reversed());
        return scores.subList(0, Math.min(10, scores.size()));
    }

    public List<ScoreEntry> getSortedBySurvivalTime() {
        List<ScoreEntry> scores = scoreboardManager.loadScores();
        scores.sort(Comparator.comparingDouble(ScoreEntry::getSurvivedTime).reversed());
        return scores.subList(0, Math.min(10, scores.size()));
    }

    public String formatSurvivalTime(float seconds) {
        int min = (int) seconds / 60;
        int sec = (int) seconds % 60;
        return min + "m " + sec + "s";
    }
}
