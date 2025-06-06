package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tilldawn.Control.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameSettings;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private GameController controller;
    private OrthographicCamera gameCamera;
    private Viewport gameViewport;
    private final GameSettings settings;


    public GameView(GameController controller, Skin skin, GameSettings settings) {
        gameCamera = new OrthographicCamera(); // اول دوربین را بسازید
        gameViewport = new FitViewport(2000, 1000, gameCamera);
        this.controller = controller;
        this.settings = settings;
        controller.setView(this,settings);
    }

    public OrthographicCamera getGameCamera() {
        return this.gameCamera;
    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

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
    }

    @Override
    public void render(float delta) {

        if (controller != null) {
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

        stage.getViewport().apply();
        stage.act(Math.min(delta, 1/30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height); // تطبیق ویوپورت با اندازه جدید پنجره
        stage.getViewport().update(width, height, true); // برای UI
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
}
