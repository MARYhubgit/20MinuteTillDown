package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Hero {
    private HeroType type;
    private Texture   texture;
    private Sprite    sprite;

    public Hero(HeroType type) {
        this.type = type;

        this.texture = null;
        this.sprite  = null;

        if (Gdx.app != null) {
            this.texture = new Texture(type.getTexturePath());
            this.sprite  = new Sprite(texture);
            this.sprite.setSize(64, 64);
        }
    }


    public String getName() {
        return type.getDisplayName();
    }

    @Override
    public String toString() {
        return getName();
    }
}
