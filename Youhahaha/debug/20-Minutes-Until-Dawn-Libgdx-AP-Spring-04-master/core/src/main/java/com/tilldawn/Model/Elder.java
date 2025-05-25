
package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Main;

public class Elder extends Enemy {
    private Sprite sprite;
    public Player player;
    private Vector2 targetPosition = new Vector2();
    private Vector2 finalDirection = new Vector2();
    private float attackCooldown = 0;
    private static final float ATTACK_DELAY = 1.5f;

    public Elder(float x, float y, Player player) {
        super(x, y, 400, 50f, 80, 80);
        this.player = player;

        Texture staticTexture = new Texture(Gdx.files.internal("elder/T_elder_1.png"));
        this.sprite = new Sprite(staticTexture);
        this.sprite.setSize(width, height);
    }

    @Override
    public void update(float deltaTime) {
        if (!alive) return;
        attackCooldown -= deltaTime;

        if (player == null || !player.isAlive()) return;

        if (attackCooldown <= 0) {
            calculateNewTarget();
            attackCooldown = ATTACK_DELAY;
        }

        Vector2 toPlayer = new Vector2(
            player.getX() - x,
            player.getY() - y
        ).nor();

        Vector2 toTarget = new Vector2(
            targetPosition.x - x,
            targetPosition.y - y
        ).nor();

        finalDirection.set(toPlayer).scl(0.7f).add(toTarget.scl(0.3f)).nor();

        x += finalDirection.x * speed * deltaTime;
        y += finalDirection.y * speed * deltaTime;
        sprite.setPosition(x - width / 2, y - height / 2);
    }

    @Override
    public void render() {
        if (isAlive()) {
            sprite.draw(Main.getBatch());
            System.out.println("Rendering Elder at: " + x + ", " + y);
        }
    }

    @Override
    protected void onDeath() {
        super.onDeath();
        System.out.println("Elder Boss Killed!");
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x - width / 2, y - height / 2, width, height);
    }

    public CollisionRect getCollision() {
        return new CollisionRect(x - width / 2, y - height / 2, width, height);
    }

    public boolean shouldRemove() {
        return !isAlive();
    }

    private void calculateNewTarget() {
        float angle = MathUtils.random(0, 2 * MathUtils.PI);
        float distance = MathUtils.random(30f, 50f);
        targetPosition.set(
            player.getX() + MathUtils.cos(angle) * distance,
            player.getY() + MathUtils.sin(angle) * distance
        );
    }

    public void dispose() {
        sprite = null;
    }

}
