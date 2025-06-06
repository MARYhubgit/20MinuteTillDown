package com.tilldawn.Control;

import com.tilldawn.Model.GameSettings;

public class DurationMenuController {
    private final GameSettings settings;

    public DurationMenuController(GameSettings settings) {
        this.settings = settings;
    }

    public void selectDuration(int minutes) {
        settings.setDurationMinutes(minutes);
        System.out.println("Duration selected: " + minutes + " minutes");
    }

    public GameSettings getSettings() {
        return settings;
    }
}
