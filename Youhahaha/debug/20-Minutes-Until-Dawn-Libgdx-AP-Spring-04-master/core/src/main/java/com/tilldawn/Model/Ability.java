package com.tilldawn.Model;

public class Ability {
    private AbilityType abilityType;

    public Ability(AbilityType abilityType) {
        this.abilityType = abilityType;
    }

    public AbilityType getType() {
        return abilityType;
    }

    public String getName() {
        return abilityType.getDisplayName();
    }

    public String getEffect() {
        return abilityType.getDescription();
    }

    @Override
    public String toString() {
        return getName() + ": " + getEffect();
    }
}
