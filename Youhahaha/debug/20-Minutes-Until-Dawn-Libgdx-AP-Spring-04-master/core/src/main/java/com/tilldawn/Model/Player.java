package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Main;
import com.tilldawn.View.GameView;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final Vector2 direction = new Vector2(1, 0);
    private float stateTime = 0f;
    private float speed;
    private Weapon weapon;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> walkAnimation;
    private TextureRegion currentFrame;
    private boolean facingRight = true;
    private List<Ability> abilities = new ArrayList<>();


    private String folderName;
    private boolean hasWalkAnim;

    private Texture playerTexture;
    private Sprite playerSprite;
    private CollisionRect rect;
    private float time = 0;
    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();
    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = false;
    private boolean isAlive;
    private int lives;
    private final HeroType heroType;
    private int enemyKilled = 0;
    private int level = 1;
    private GameView view;
    private User user;

    public Player(HeroType heroType,GameView view,User user) {
        this.heroType = heroType;
        this.lives =  heroType.getHp() * 50;
        this.speed = heroType.getSpeed();
        this.playerTexture = GameAssetManager.getInstance().getPlayerIdleTexture();
        this.playerSprite = new Sprite(playerTexture);
        this.view = view;
        this.user = user;

        position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        playerSprite.setPosition(position.x, position.y);
        playerSprite.setSize(playerTexture.getWidth() , playerTexture.getHeight());
        rect = new CollisionRect(position.x, position.y, playerSprite.getWidth(), playerSprite.getHeight());
        this.isAlive = true;
        loadAnimations();
    }

    private void loadAnimations() {
        String basePath = "Heros/" + heroType.getDisplayName();

        idleAnimation = loadAnimation(basePath + "/idle", "Idle_", 6, 0.1f);
        runAnimation = loadAnimation(basePath + "/run", "Run_", 4, 0.1f);

        if (heroType.hasWalkAnim()) {
            walkAnimation = loadAnimation(basePath + "/walk", "Walk_", 8, 0.1f);
        }
    }

    private Animation<TextureRegion> loadAnimation(String folderPath, String filePrefix, int frameCount, float frameDuration) {
        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = folderPath + "/" + filePrefix + i + ".png";
            frames[i] = new TextureRegion(new Texture(Gdx.files.internal(path)));
        }
        return new Animation<>(frameDuration, frames);
    }
    public void setLives(int lives) {
        this.lives = lives;
    }

    public void takeDamage(int damage) {
        lives -= damage;

        if (lives <= 0) {
            this.isAlive = false;
        }
    }

    public int getLives() {
        return lives;
    }


    public void update(float deltaTime) {

        handleInput();
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);

        playerSprite.setPosition(position.x, position.y);
        rect.move(position.x, position.y);

        stateTime += deltaTime;

        if (isPlayerRunning) currentFrame = runAnimation.getKeyFrame(stateTime, true);
        else currentFrame = idleAnimation.getKeyFrame(stateTime, true);

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

    public void goBoseFight() {
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        position.set(centerX, centerY);
        playerSprite.setPosition(centerX, centerY);
        rect.move(centerX, centerY);
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
        System.out.println("player");

        if (isPlayerRunning) {
            currentFrame = runAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = idleAnimation.getKeyFrame(stateTime, true);
        }

        Main.getBatch().draw(currentFrame, position.x, position.y,
            currentFrame.getRegionWidth() * 2,
            currentFrame.getRegionHeight() * 2);

    }


    public boolean isAlive() {
        return isAlive;
    }

    public int getEnemyKilled() {
        return enemyKilled;
    }

    public void setEnemyKilled() {
        this.enemyKilled++;
        if ((enemyKilled * 3) >= ((level+1) * 20)){
            setLevel(level+1);
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        levelUp();
        this.level = level;
    }

    public void addAbility(Ability ability) {
        abilities.add(ability);
        applyAbilityEffect(ability);
    }

    private void applyAbilityEffect(Ability ability) {
        switch (ability.getType()) {
            case VITALITY:
                lives += 50;
                break;
            case DAMAGER:
                if (weapon != null) {
                    weapon.setDamageMultiplier();
                }
                break;
            case PROCREASE:
                if (weapon != null) {
                    weapon.increaseProjectileCount(1);
                }
                break;
            case AMOCREASE:
                if (weapon != null) {
                    weapon.increaseMaxAmmo(5);
                }
                break;
            case SPEEDY:
                speed *= 2;
                break;
        }
    }

    public void levelUp() {
        System.out.println("level up");
        level++;
        view.pauseForLevelUp();
    }

    public float getSpeed() {
        return speed;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }


}
