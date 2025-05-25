
package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Main;
import com.tilldawn.Model.Elder;
import com.tilldawn.Model.Player;

public class ElderController {
    private Elder elder;
    private Player player;
    private ShapeRenderer shapeRenderer;

    private Rectangle barrierBounds;
    private float shrinkSpeed = 10f;
    private boolean active = false;
    private float initialWidth = 1000;
    private float initialHeight = 1000;

    public ElderController(PlayerController playerController) {
        try {
            this.player = playerController.getPlayer();
            this.shapeRenderer = new ShapeRenderer();

            this.elder = new Elder(
                player.getX() + 200,
                player.getY() + 200,
                player
            );

            this.barrierBounds = new Rectangle(
                player.getPosX() - initialWidth / 2f,
                player.getPosY() - initialHeight / 2f,
                initialWidth,
                initialHeight
            );


        } catch (NullPointerException e) {
            Gdx.app.error("ElderController", "Initialization error", e);
            throw new RuntimeException("ElderController initialization failed", e);
        }
    }

    public void update(float delta) {
        if (!player.isAlive() || !active) return;

        elder.update(delta);

        if (elder.isAlive()) {
            updateBarrier(delta);
            checkPlayerInBarrier();
        } else {
            barrierBounds.set(0, 0, 0, 0);
        }
    }

    private void updateBarrier(float delta) {

        barrierBounds.width = Math.max(0, barrierBounds.width - shrinkSpeed * delta);
        barrierBounds.height = Math.max(0, barrierBounds.height - shrinkSpeed * delta);

        barrierBounds.x = player.getPosX() - barrierBounds.width / 2f;
        barrierBounds.y = player.getPosY() - barrierBounds.height / 2f;
    }

    private void checkPlayerInBarrier() {
        if (!barrierBounds.contains(player.getPosX(), player.getPosY())) {
            player.takeDamage(1);
            Gdx.app.log("ElderController", "Player took barrier damage");
        }
    }
    public void render() {
        try {

            if (!active || !player.isAlive()) return;

            if(!Main.getBatch().isDrawing()) {
                Main.getBatch().begin();
            }
            elder.render();

            if (elder.isAlive()) {
                renderBarrier();
            }
        }catch (NullPointerException e) {
            System.out.println("ElderController render error"+ e.getMessage());
        }
    }


    private void renderBarrier() {
        if(Main.getBatch().isDrawing()) {
            Main.getBatch().end();
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 0.7f);
        shapeRenderer.rect(
            barrierBounds.x,
            barrierBounds.y,
            barrierBounds.width,
            barrierBounds.height
        );
        shapeRenderer.end();
    }

    public void activate() {
        if (!active) {
            active = true;

            barrierBounds.set(
                player.getPosX() - initialWidth / 2f,
                player.getPosY() - initialHeight / 2f,
                initialWidth,
                initialHeight
            );
            Gdx.app.log("ElderController", "Activated with barrier size: "
                + barrierBounds.width + "x" + barrierBounds.height);
        }
    }

    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        if (elder != null) {
            elder.dispose();
        }
        Gdx.app.log("ElderController", "Resources disposed");
    }

    public Elder getElder() { return elder; }
    public boolean isActive() { return active; }
    public boolean isAlive() { return elder != null && elder.isAlive(); }
    public Rectangle getBarrierBounds() { return barrierBounds; }
}
