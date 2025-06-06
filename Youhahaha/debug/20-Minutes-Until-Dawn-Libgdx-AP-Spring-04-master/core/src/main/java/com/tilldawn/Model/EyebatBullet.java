package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Main;

public class EyebatBullet {
    private Sprite sprite;
    private Vector2 position;
    private Vector2 velocity;
    private float speed = 300f;
    private boolean active = true;

    public EyebatBullet(float x, float y, float dirX, float dirY, Texture texture) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(dirX, dirY).nor().scl(speed);
        this.sprite = new Sprite(texture);
        this.sprite.setSize(20, 20);
    }

    public void update(float delta) {
        position.mulAdd(velocity, delta);
        sprite.setPosition(position.x, position.y);

        if (position.x < -100 || position.x > Gdx.graphics.getWidth() + 100 ||
            position.y < -100 || position.y > Gdx.graphics.getHeight() + 100) {
            active = false;
        }
    }

    public void render() {
        if (active) {
            sprite.draw(Main.getBatch());
        }
    }

    public CollisionRect getBounds() {
        return new CollisionRect(
            position.x,
            position.y,
            sprite.getWidth(),
            sprite.getHeight()
        );
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
