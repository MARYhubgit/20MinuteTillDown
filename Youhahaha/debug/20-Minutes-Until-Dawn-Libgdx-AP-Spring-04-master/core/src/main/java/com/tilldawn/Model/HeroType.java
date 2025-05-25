package com.tilldawn.Model;

public enum HeroType {
    SHANA("Shana", 4, 4, true),
    DIAMOND("Diamond", 1, 7, true),
    SCARLET("Scarlet", 5, 3, false),
    LILITH("Lilith", 3, 5, true),
    DASHER("Dasher", 10, 2, false);

    private final String folderName;
    private final int speed;
    private final int hp;
    private final boolean hasWalkAnim;

    HeroType(String folderName, int speed, int hp, boolean hasWalkAnim) {
        this.folderName = folderName;
        this.speed = speed;
        this.hp = hp;
        this.hasWalkAnim = hasWalkAnim;
    }

    public String getDisplayName() {
        return folderName;    }

    public int getSpeed() { return speed; }

    public int getHp() { return hp; }

    public boolean hasWalkAnim() { return hasWalkAnim; }

    public static HeroType fromName(String name) {
        for (HeroType h : values()) {
            if (h.name().equalsIgnoreCase(name)) return h;
        }
        return null;
    }
}
