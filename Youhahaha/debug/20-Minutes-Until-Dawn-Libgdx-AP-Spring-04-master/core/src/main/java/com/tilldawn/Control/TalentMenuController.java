package com.tilldawn.Control;

import com.badlogic.gdx.utils.Array;
import com.tilldawn.Model.Ability;
import com.tilldawn.Model.AbilityType;
import com.tilldawn.Model.GameSettings;
import com.tilldawn.Model.HeroType;

public class TalentMenuController {
    private final GameSettings settings;

    public TalentMenuController(GameSettings settings) {
        this.settings = settings;
    }

    public Array<String> getHeroGuides() {
        Array<String> guides = new Array<>();
        for (HeroType ht : HeroType.values()) {
            guides.add(ht.getDisplayName() + ": (hp)" + ht.getHp());
            if (guides.size >= 3) break;
        }
        return guides;
    }

    public Array<String> getActiveKeys() {
        Array<String> keys = new Array<>();
        for (String key : settings.getKeyBindings()) {
            keys.add(key);
        }
        return keys;
    }

    public Array<String> getCheats() {
        Array<String> cheats = new Array<>();
        for (String code : settings.getCheatCodes()) {
            cheats.add(code + " → " + lookupCheatEffect(code));
        }
        return cheats;
    }

    private String lookupCheatEffect(String code) {
        switch (code) {
            case "l": return "less 1 minute";
            case "u": return "increase 1 level";
            case "j": return "increase lives";
            case "b": return "go to Boss fight";
            case "k": return "increase danger";
            default:      return "Unknown Effect";
        }
    }

    public Array<String> getAbilities() {
        Array<String> abs = new Array<>();
        for (AbilityType type : AbilityType.values()) {
            Ability ability = new Ability(type);
            abs.add(ability.toString());
        }
        return abs;
    }

}
