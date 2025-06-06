package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.MusicManager;
import com.tilldawn.Model.SettingsManager;
import com.tilldawn.Model.User;

public class MainMenuView implements Screen {
    private Stage stage;

    private final TextButton exitButton;
    private final TextButton continueButton;
    private final TextButton settingsButton;
    private final TextButton profileButton;
    private final TextButton pregameButton;
    private final TextButton scoreboardButton;
    private final TextButton talentButton;
    private final TextButton logoutButton;

    private final Image avatarImage;
    private final Label usernameLabel;
    private final Label scoreLabel;
    private final TextField field;
    public Table table;
    private final MainMenuController controller;

    public MainMenuView(MainMenuController controller, Skin skin, User user, boolean hasSave) {
        this.controller = controller;
        controller.setView(this);

        this.continueButton  = new TextButton("Continue",   skin);
        this.settingsButton  = new TextButton("Settings",   skin);
        this.profileButton   = new TextButton("Profile",    skin);
        this.pregameButton   = new TextButton("Pre-Game",   skin);
        this.scoreboardButton= new TextButton("Scoreboard", skin);
        this.talentButton    = new TextButton("Talent Hint",skin);
        this.logoutButton    = new TextButton("Logout",     skin);
        this.exitButton      = new TextButton("Exit", skin);
        this.field = new TextField("Welcome to Game", skin);
        this.table = new Table();

        continueButton.setDisabled(!hasSave);

        Texture avatarTex = GameAssetManager.getInstance().get(user.getAvatarPath(), Texture.class);
        avatarImage = new Image(new TextureRegionDrawable(new TextureRegion(avatarTex)));
        usernameLabel = new Label(user.getUsername(), skin);
        scoreLabel    = new Label("Score: " + user.getScore(), skin);

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();

        table.add(avatarImage).size(64,64).pad(10);
        table.add(usernameLabel).pad(10);
        table.add(scoreLabel).pad(10);
        table.row();

        table.add(field).width(600);
        table.row().pad(10, 0 , 10 , 0);
        table.add(logoutButton).width(300).pad(5);
        table.add(continueButton).width(300).pad(5);
        table.row();
        table.add(settingsButton).width(300).pad(5);
        table.add(profileButton).width(300).pad(5);
        table.row();
        table.add(pregameButton).width(300).pad(5);
        table.add(scoreboardButton).width(300).pad(5);
        table.row();
        table.add(talentButton).width(300).pad(5);
        table.add(exitButton).width(300).pad(5);

        stage.addActor(table);
        SettingsManager settings = SettingsManager.getInstance();
        MusicManager.getInstance().playMusic(settings.getCurrentMusic());
        MusicManager.getInstance().setVolume(settings.getMusicVolume());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleMainMenuButtons();
    }

    @Override
    public void resize(int width, int height) {

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

    public TextField getField() {
        return field;
    }

    public TextButton getContinueButton()   { return continueButton; }
    public TextButton getSettingsButton()   { return settingsButton; }
    public TextButton getProfileButton()    { return profileButton; }
    public TextButton getPregameButton()    { return pregameButton; }
    public TextButton getScoreboardButton() { return scoreboardButton; }
    public TextButton getTalentButton()     { return talentButton; }
    public TextButton getLogoutButton()     { return logoutButton; }
    public TextButton getExitButton() {
        return exitButton;
    }
}
