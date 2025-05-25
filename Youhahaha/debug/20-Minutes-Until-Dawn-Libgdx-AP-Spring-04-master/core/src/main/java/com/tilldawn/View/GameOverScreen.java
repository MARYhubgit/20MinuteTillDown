package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Model.*;

public class GameOverScreen extends ScreenAdapter {
    private Main game;
    private Stage stage;
    private ScoreboardManager scoreboardManager;
    private Player player;

    public GameOverScreen(Player player, Main game, UserManager userManager,String reason, float survivedTime, int enemiesKilled, int xpEarned, int score) {
        this.game = game;
        this.player = player;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        ScoreEntry entry = new ScoreEntry(
            userManager.getCurrentUser().getUsername(),
            score,
            survivedTime,
            enemiesKilled,
            xpEarned
        );
        scoreboardManager = ScoreboardManager.getInstance();
        scoreboardManager.addEntry(entry);
        game.getUserManager().getCurrentUser().setScore(score);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label titleLabel = new Label("Game Over", GameAssetManager.getInstance().getSkin());
        Label reasonLabel = new Label(reason, GameAssetManager.getInstance().getSkin());


        Label usernameLabel = new Label("Username : " + userManager.getCurrentUser().getUsername(), GameAssetManager.getInstance().getSkin());
        Label timeLabel = new Label("Survived Time: " + survivedTime + " seconds", GameAssetManager.getInstance().getSkin());
        Label enemiesLabel = new Label("Enemies Killed: " + enemiesKilled, GameAssetManager.getInstance().getSkin());
        Label xpLabel = new Label("XP Earned: " + xpEarned, GameAssetManager.getInstance().getSkin());
        Label waveLabel = new Label("Score : " + score, GameAssetManager.getInstance().getSkin());

        TextButton retryButton = new TextButton("Retry", GameAssetManager.getInstance().getSkin());
        retryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PreGameMenuView(game,
                    GameSettings.getInstance(),
                    GameAssetManager.getInstance().getSkin(),userManager));
            }
        });

        TextButton mainMenuButton = new TextButton("Back to Main Menu", GameAssetManager.getInstance().getSkin());
        mainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MainMenuController controller = new MainMenuController(game, userManager);
                game.setScreen(new MainMenuView(controller, GameAssetManager.getInstance().getSkin(), userManager.getCurrentUser(), false));
            }
        });

        table.add(titleLabel).pad(10).row();
        table.add(reasonLabel).pad(5).row();
        table.add(usernameLabel).pad(5).row();
        table.add(timeLabel).pad(5).row();
        table.add(enemiesLabel).pad(5).row();
        table.add(xpLabel).pad(5).row();
        table.add(waveLabel).pad(5).row();
        table.add(retryButton).width(200).pad(10).row();
        table.add(mainMenuButton).width(500).pad(10).row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
