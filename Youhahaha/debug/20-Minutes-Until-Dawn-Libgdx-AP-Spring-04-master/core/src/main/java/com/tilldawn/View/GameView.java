package com.tilldawn.View;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tilldawn.Control.CheatManager;
import com.tilldawn.Control.GameController;
import com.tilldawn.Control.WeaponController;
import com.tilldawn.Main;
import com.tilldawn.Model.Ability;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameSettings;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.tilldawn.Model.Player;
import com.tilldawn.Control.AbilityPool;

import java.util.List;


public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private GameController controller;
    private Label livesLabel;
    private Label arrowsLabel;
    private Label levelLabel;
    private Label xpLabel;
    private Table hudTable;
    private OrthographicCamera gameCamera;
    private Viewport gameViewport;
    private final GameSettings settings;
    private float remainingTimeInSeconds;
    private Label timerLabel;
    private Table uiTable;
    private TextButton pauseButton;
    private boolean isPausedForLevelUp = false;
    private AbilitySelectionUI abilitySelectionUI;
    private InputProcessor previousInputProcessor;
    private CheatManager cheatManager;
    private ProgressBar xpProgressBar;




    public GameView(GameController controller, Skin skin, GameSettings settings) {

            gameCamera = new OrthographicCamera();
            gameViewport = new FitViewport(2000, 1000, gameCamera);
            this.controller = controller;
            this.settings = settings;
            controller.setView(this, settings);
           cheatManager = new CheatManager(settings,controller.getPlayerController().getPlayer(),this,controller);

    }


    public OrthographicCamera getGameCamera() {
        return this.gameCamera;
    }
    @Override
    public void show() {
        try {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, this));
        abilitySelectionUI = new AbilitySelectionUI(GameAssetManager.getInstance().getSkin());
        AbilityPool.initialize();


            if (controller != null && controller.getPlayerController() != null) {
            gameCamera.position.set(
                controller.getPlayerController().getPlayer().getX(),
                controller.getPlayerController().getPlayer().getY(),
                0
            );
        } else {
            gameCamera.position.set(gameViewport.getWorldWidth()/2, gameViewport.getWorldHeight()/2, 0);
        }
        gameCamera.update();
        hudTable = new Table();
        hudTable.top().left();
        hudTable.setFillParent(true);

        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

            xpProgressBar = new ProgressBar(0, 100, 1, false, GameAssetManager.getInstance().getSkin());
            xpProgressBar.setAnimateDuration(0.25f);
            xpProgressBar.setWidth(200);
            xpProgressBar.setHeight(15);
            xpProgressBar.setAnimateInterpolation(Interpolation.linear);

        livesLabel = new Label("Lives: 0", labelStyle);
        arrowsLabel = new Label("Arrows: 0", labelStyle);
        levelLabel = new Label("Level: 1", labelStyle);
        xpLabel = new Label("XP: 0 / 0", labelStyle);
        timerLabel = new Label("Time: 00:00", labelStyle);

        hudTable.add(livesLabel).left().pad(5);
        hudTable.row();
        hudTable.add(arrowsLabel).left().pad(5);
        hudTable.row();
        hudTable.add(levelLabel).left().pad(5);
        hudTable.row();
        hudTable.add(xpLabel).left().pad(5);
        hudTable.row();
        hudTable.add(timerLabel).left().pad(5);
        hudTable.add(xpProgressBar).width(300).height(15).padTop(5).row();


            stage.addActor(hudTable);
        this.remainingTimeInSeconds = settings.getDurationMinutes();

        uiTable = new Table();
        uiTable.top().right();
        uiTable.setFillParent(true);

        pauseButton = new TextButton("⏸️", GameAssetManager.getInstance().getSkin());
            pauseButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                   settings.setDurationMinutes(getRemainingTimeInSeconds());
                    Main.getMain().setScreen(
                        new PauseMenuScreen(Main.getMain(),controller.getPlayerController().getPlayer() , controller)
                    );
                }
            });


            uiTable.add(pauseButton).pad(10).size(80, 80);
            stage.addActor(uiTable);




            InputMultiplexer multiplexer = new InputMultiplexer();
            multiplexer.addProcessor(stage);
            multiplexer.addProcessor(this);
            multiplexer.addProcessor(new InputAdapter() {
                @Override
                public boolean keyDown(int keycode) {
                    String keyName = Input.Keys.toString(keycode).toLowerCase();
                    if (keyName.length() == 1) {
                        char pressedKey = keyName.charAt(0);
                        cheatManager.handleCheat(pressedKey);
                    }
                    return true;
                }
            });
            Gdx.input.setInputProcessor(multiplexer);



        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(float delta) {
        if (isPausedForLevelUp) {
            ScreenUtils.clear(0, 0, 0, 1);
            abilitySelectionUI.render();
            return;
        }

        if (controller != null && !controller.isPaused()) {
            controller.updateGame(delta);
        }

        if (controller != null && controller.getPlayerController() != null) {
            gameCamera.position.set(
                controller.getPlayerController().getPlayer().getX(),
                controller.getPlayerController().getPlayer().getY(),
                0
            );
        }
        gameCamera.update();

        ScreenUtils.clear(0, 0, 0, 1);

        Main.getBatch().setProjectionMatrix(gameCamera.combined);
        if (controller != null) {
            controller.renderGame();
        }

        updateTimer(delta);

        stage.getViewport().apply();

        if (controller != null && controller.getPlayerController() != null) {
            Player player = controller.getPlayerController().getPlayer();
            WeaponController weapon = controller.getWeaponController();

            livesLabel.setText(" Lives: " + player.getLives());
            arrowsLabel.setText("Arrows: " + weapon.getCurrentAmmo());
            levelLabel.setText("Level: " + player.getLevel());

            int currentXp = player.getEnemyKilled() * 3;
            int neededXp= (player.getLevel() + 1) * 20;
            xpLabel.setText("XP: " + currentXp + " / " + neededXp);

            float progress = (float) currentXp / neededXp * 100f;
            xpProgressBar.setValue(progress);

        }

        stage.act(Math.min(delta, 1/30f));
        stage.draw();
    }



    public void updateTimer(float delta) {
        if (remainingTimeInSeconds <= settings.getTotalTimeInSeconds() / 2f) {
            controller.getEnemyController().getElderController().activate();
        }

        if (remainingTimeInSeconds > 0) {
            remainingTimeInSeconds -= delta;
            if (remainingTimeInSeconds < 0) {
                remainingTimeInSeconds = 0;
                controller.handleGameEnd("You won");
            }
        }
        timerLabel.setText("Time: " + formatTime((int) remainingTimeInSeconds));
    }
    public float getRemainingTimeInSeconds() {
        return remainingTimeInSeconds;
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        controller.getWeaponController().handleWeaponShoot(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


    public void pauseForLevelUp() {
        try {
            settings.setDurationMinutes(getRemainingTimeInSeconds());

            List<Ability> randomAbilities = AbilityPool.getRandomAbilities(3);

            AbilitySelectionScreen selectionScreen = new AbilitySelectionScreen(
                Main.getMain(),
                randomAbilities,
                selectedAbility -> {
                   controller.getPlayerController().getPlayer().addAbility(selectedAbility);
                },
                controller,
                GameAssetManager.getInstance().getSkin()

            );

            Main.getMain().setScreen(selectionScreen);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
