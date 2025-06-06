package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Main;

public class TentacleEnemy extends Enemy {
    private Sprite sprite;
    private Animation<TextureRegion> walkAnimation;
    private float stateTime = 0;
    private Player target;

    public TentacleEnemy(float x, float y, Player player) {

        super(x, y, 25, 55f, 45, 65);

        this.target = player;

        this.walkAnimation = GameAssetManager.getInstance().getTentacleWalkAnim();

        TextureRegion firstFrame = walkAnimation.getKeyFrame(0);
        this.sprite = new Sprite(firstFrame);
        this.sprite.setSize(width, height);
        this.sprite.setPosition(x, y);
    }

    @Override
    public void update(float deltaTime) {
        stateTime += deltaTime;

        if (target != null && target.isAlive()) {
            Vector2 direction = new Vector2(
                target.getX() - this.x,
                target.getY() - this.y
            ).nor();

            x += direction.x * speed * deltaTime;
            y += direction.y * speed * deltaTime;
        }

        sprite.setPosition(x, y);
        sprite.setRegion(walkAnimation.getKeyFrame(stateTime, true));
    }

    @Override
    public void render() {
        if (isAlive()) {
            sprite.draw(Main.getBatch());
        }
    }

    public boolean shouldRemove() {
        return !isAlive() || isOutOfBounds();
    }

    private boolean isOutOfBounds() {
        return x < -100 || x > Gdx.graphics.getWidth() + 100 ||
            y < -100 || y > Gdx.graphics.getHeight() + 100;
    }
}
