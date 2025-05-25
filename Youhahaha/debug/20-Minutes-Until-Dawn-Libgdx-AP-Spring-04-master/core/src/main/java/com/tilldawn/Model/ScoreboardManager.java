package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreboardManager {
    private static final String SCOREBOARD_FILE = "scoreboard.json";
    private static ScoreboardManager instance;
    private List<ScoreEntry> entries;

    private ScoreboardManager() {
        load();
    }

    public static ScoreboardManager getInstance() {
        if (instance == null) {
            instance = new ScoreboardManager();
        }
        return instance;
    }

    private void load() {
        FileHandle file = Gdx.files.local(SCOREBOARD_FILE);
        if (file.exists()) {
            Json json = new Json();
            entries = json.fromJson(ArrayList.class, ScoreEntry.class, file.readString());
            if (entries == null) {
                entries = new ArrayList<>();
            }
        } else {
            entries = new ArrayList<>();
        }
    }

    public void save() {
        Json json = new Json();
        FileHandle file = Gdx.files.local(SCOREBOARD_FILE);
        file.writeString(json.prettyPrint(entries), false);
    }

    public void addEntry(ScoreEntry entry) {
        entries.add(entry);
        save();
    }

    public List<ScoreEntry> getEntries() {
        return entries;
    }

    public List<ScoreEntry> getTopScores(int limit) {
        entries.sort(Comparator.comparingInt(ScoreEntry::getScore).reversed());
        if (entries.size() > limit) {
            return entries.subList(0, limit);
        }
        return new ArrayList<>(entries);
    }

    public List<ScoreEntry> loadScores() {
        load();
        return entries;
    }
}
