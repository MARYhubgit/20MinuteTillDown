package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Main;

public class Player {
    private Texture playerTexture;
    private Sprite playerSprite;
    private float playerHealth = 100;
    private CollisionRect rect;
    private float time = 0;
    private float speed = 300f;
    private Weapon weapon;
    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();
    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = false;
    private boolean isAlive;
    private int lives;

    public Player() {
        this.lives = 5;
        this.playerTexture = GameAssetManager.getInstance().getPlayerIdleTexture();
        this.playerSprite = new Sprite(playerTexture);

        position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        playerSprite.setPosition(position.x, position.y);
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        rect = new CollisionRect(position.x, position.y, playerSprite.getWidth(), playerSprite.getHeight());
        this.isAlive = true;
    }

    public void takeDamage(int damage) {
        lives -= damage;

        if (lives <= 0) {
            this.isAlive = false;
        }
    }

    public void update(float deltaTime) {

        handleInput();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);

        playerSprite.setPosition(position.x, position.y);
        rect.move(position.x, position.y);

        Gdx.app.log("PLAYER_UPDATE",
            String.format("Pos: (%.1f,%.1f) | Vel: (%.1f,%.1f)",
                position.x, position.y, velocity.x, velocity.y));
    }


    public void handleInput() {
        velocity.set(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) velocity.y = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) velocity.y = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) velocity.x = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) velocity.x = 1;

        if (!velocity.isZero()) {
            velocity.nor().scl(speed);
            isPlayerIdle = false;
            isPlayerRunning = true;
        } else {
            isPlayerIdle = true;
            isPlayerRunning = false;
        }
    }

    public void setPosX(float x) {
        position.x = x;
    }

    public void setPosY(float y) {
        position.y = y;
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public float getPosX() {
        return position.x;
    }

    public float getPosY() {
        return position.y;
    }


    public CollisionRect getBounds() {
        return rect;
    }

    public void setWeapon(Weapon weapon){
        this.weapon = weapon;
    }
    public Weapon getWeapon(){
        return weapon;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public void render() {
        playerSprite.draw(Main.getBatch());

    }

    public boolean isAlive() {
        return isAlive;
    }

}
