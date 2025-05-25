package com.tilldawn.Control;

import com.tilldawn.Model.GameSettings;

public class DurationMenuController {
    private final GameSettings settings;

    public DurationMenuController(GameSettings settings) {
        this.settings = settings;
    }

    public void selectDuration(int minutes) {
        settings.setDurationMinutes(minutes  * 60f);
    }
    public void setTotalMinutes(int totalMinutes) {
        settings.setTotalTimeInSeconds(totalMinutes * 60f);
    }


    public GameSettings getSettings() {
        return settings;
    }
}
