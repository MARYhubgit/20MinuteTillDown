package com.tilldawn.Model;

import java.util.ArrayList;
import java.util.List;

public class GameSettings {
    private HeroType selectedHero;
    private Weapon selectedWeapon;
    private static GameSettings instance;
    private float durationMinutes;
    private float totalTimeInSeconds;
    private boolean blackAndWhiteMode;
    private final List<Ability> acquiredAbilities = new ArrayList<>();
    private final List<String> cheatCodes = new ArrayList<>();
    private final List<String> keyBindings = new ArrayList<>();

    private GameSettings() {
        keyBindings.add("W - Move Up");
        keyBindings.add("A - Move Left");
        keyBindings.add("S - Move Down");
        keyBindings.add("D - Move Right");

        cheatCodes.add("l");
        cheatCodes.add("u");
        cheatCodes.add("j");
        cheatCodes.add("b");
        cheatCodes.add("k");

    }

    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }


    public HeroType getSelectedHero() {
        return selectedHero;
    }

    public void setSelectedHero(HeroType selectedHero) {
        this.selectedHero = selectedHero;
    }


    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }
    public void setSelectedWeapon(Weapon selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }


    public float getDurationMinutes() {
        return durationMinutes;
    }
    public void setDurationMinutes(float durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
    public void setTotalTimeInSeconds(float totalTimeInSeconds) {
        this.totalTimeInSeconds = totalTimeInSeconds;
    }
    public float getTotalTimeInSeconds() {
        return totalTimeInSeconds;
    }


    public boolean isBlackAndWhiteMode() {
        return blackAndWhiteMode;
    }
    public void setBlackAndWhiteMode(boolean blackAndWhiteMode) {
        this.blackAndWhiteMode = blackAndWhiteMode;
    }


    public List<Ability> getAcquiredAbilities() {
        return acquiredAbilities;
    }
    public void addAbility(Ability a) {
        acquiredAbilities.add(a);
    }

    public List<String> getCheatCodes() {
        return cheatCodes;
    }
    public void addCheatCode(String code) {
        cheatCodes.add(code);
    }

    public List<String> getKeyBindings() {
        return keyBindings;
    }


    public void addKeyBinding(String key) {
        keyBindings.add(key);
    }

}
