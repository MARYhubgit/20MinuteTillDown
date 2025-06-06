package com.tilldawn.Control;

import com.tilldawn.Model.GameSettings;
import com.tilldawn.Model.Weapon;

public class WeaponMenuController {
    private final GameSettings settings;

    public WeaponMenuController(GameSettings settings) {
        this.settings = settings;
    }


    public void selectWeapon(Weapon weapon) {
        settings.setSelectedWeapon(weapon);
    }

    public GameSettings getSettings() {
        return settings;
    }
}
