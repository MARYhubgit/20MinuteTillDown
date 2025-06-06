package com.tilldawn.Model;

public enum HeroType {
    WITCH("Witch", "High firepower", "heroes/witch.png"),
    HUNTER("Hunter", "Long-range attacks", "heroes/hunter.png"),
    VAMPIRE("Vampire", "Life-stealing", "heroes/vampire.png");

    private final String displayName;
    private final String description;
    private final String texturePath;

    HeroType(String displayName, String description, String texturePath) {
        this.displayName = displayName;
        this.description = description;
        this.texturePath = texturePath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getTexturePath() {
        return texturePath;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
