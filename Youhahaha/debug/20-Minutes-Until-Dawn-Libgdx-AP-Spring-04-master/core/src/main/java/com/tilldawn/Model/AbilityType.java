package com.tilldawn.Model;

public enum AbilityType {
    VITALITY("Vitality", "Increases maximum HP"),
    DAMAGER("Damager", "Increases your base damage"),
    PROCREASE("Procrease", "Boosts projectile speed"),
    AMOCREASE("Amocrease", "Increases maximum ammo"),
    SPEEDY("Speedy", "Boosts movement speed");

    private final String displayName;
    private final String description;

    AbilityType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}
