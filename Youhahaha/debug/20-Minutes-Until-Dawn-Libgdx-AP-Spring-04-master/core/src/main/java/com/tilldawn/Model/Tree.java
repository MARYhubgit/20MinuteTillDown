package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tilldawn.Main;

public class Tree extends Enemy {
    private Animation<TextureRegion> currentAnim;
    private float stateTime;
    private Player target;

    public Tree(float x, float y, Player target) {
        super(x, y, 50, 65f, 50, 100);
        this.currentAnim = GameAssetManager.getInstance().getTreeIdleAnim();
        this.stateTime = 0;
        this.target = target;
    }

    @Override
    public void update(float deltaTime) {
        if (speed > 0 && target != null) {
            float dx = target.getX() - x;
            float dy = target.getY() - y;
            float distance = (float)Math.sqrt(dx*dx + dy*dy);

            if (distance > 5f) {
                x += (dx/distance) * speed * deltaTime;
                y += (dy/distance) * speed * deltaTime;
            }

            else if (alive) {
                alive = false;
                Gdx.app.log("TREE", "Destroyed on collision with player");
            }
        }
    }

    @Override
    public void render() {
        if (alive) {
            stateTime += Gdx.graphics.getDeltaTime();
            TextureRegion currentFrame = currentAnim.getKeyFrame(stateTime, true);
            Main.getBatch().draw(currentFrame, x, y, width, height);
        }
    }

    public void dispose() {
    }
}
