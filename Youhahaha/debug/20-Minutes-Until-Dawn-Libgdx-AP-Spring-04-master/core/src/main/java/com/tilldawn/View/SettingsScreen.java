package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.SettingsController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.MusicManager;
import com.tilldawn.Model.SettingsManager;
import com.tilldawn.Model.UserManager;


public class SettingsScreen extends ScreenAdapter {
    private Stage stage;
    private SettingsController controller;
    private Main game;
    private Label messageLabel;

    public SettingsScreen(Main game, UserManager userManager, SettingsManager settingsManager) {
        this.game = game;
        this.controller = new SettingsController(settingsManager);
        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label musicVolumeLabel = new Label("Music Volume", GameAssetManager.getInstance().getSkin());
        final Slider musicVolumeSlider = new Slider(0, 1, 0.01f, false, GameAssetManager.getInstance().getSkin());
        musicVolumeSlider.setValue(controller.getMusicVolume());

        Label currentMusicLabel = new Label("Current Music Track", GameAssetManager.getInstance().getSkin());
        final SelectBox<String> musicSelectBox = new SelectBox<>(GameAssetManager.getInstance().getSkin());
        musicSelectBox.setItems("track1.mp3", "track2.mp3", "track3.mp3","track4.mp3");
        musicSelectBox.setSelected(controller.getCurrentMusic());

        final CheckBox sfxCheckBox = new CheckBox("Enable SFX", GameAssetManager.getInstance().getSkin());
        sfxCheckBox.setChecked(controller.isSfxEnabled());

        final CheckBox reloadAutoCheckBox = new CheckBox("Reload Auto", GameAssetManager.getInstance().getSkin());
        reloadAutoCheckBox.setChecked(controller.isReloadAutoEnabled());

        final CheckBox grayscaleCheckBox = new CheckBox("Grayscale Mode", GameAssetManager.getInstance().getSkin());
        grayscaleCheckBox.setChecked(controller.isGrayscaleEnabled());

        Label keyUpLabel = new Label("Key Up: " + Input.Keys.toString(controller.getKeyUp()), GameAssetManager.getInstance().getSkin());
        TextButton changeKeyUpButton = new TextButton("Change Key Up", GameAssetManager.getInstance().getSkin());

        changeKeyUpButton.addListener(new ChangeListener() {
            boolean waitingForKey = false;

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!waitingForKey) {
                    messageLabel.setText("Press a key to assign for 'Key Up'...");
                    waitingForKey = true;
                    stage.addListener(new InputListener() {
                        @Override
                        public boolean keyDown(InputEvent event, int keycode) {
                            controller.setKeyUp(keycode);
                            keyUpLabel.setText("Key Up: " + Input.Keys.toString(keycode));
                            messageLabel.setText("Key assigned!");
                            waitingForKey = false;
                            stage.removeListener(this);
                            return true;
                        }
                    });
                }
            }
        });

        messageLabel = new Label("", GameAssetManager.getInstance().getSkin());

        TextButton saveButton = new TextButton("Save Settings", GameAssetManager.getInstance().getSkin());
        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.setMusicVolume(musicVolumeSlider.getValue());
                String selectedMusic = musicSelectBox.getSelected();
                controller.setCurrentMusic(selectedMusic);
                controller.setSfxEnabled(sfxCheckBox.isChecked());
                controller.setReloadAutoEnabled(reloadAutoCheckBox.isChecked());
                controller.setGrayscaleEnabled(grayscaleCheckBox.isChecked());
                controller.saveSettings();
                messageLabel.setText("Settings saved!");

                MusicManager.getInstance().playMusic(selectedMusic);
                MusicManager.getInstance().setVolume(controller.getMusicVolume());
            }
        });

        TextButton backButton = new TextButton("Back", GameAssetManager.getInstance().getSkin());
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MainMenuController controller = new MainMenuController(game, userManager);
                game.setScreen(new MainMenuView(controller,GameAssetManager.getInstance().getSkin(), userManager.getCurrentUser(), false));
            }
        });

        table.add(musicVolumeLabel).left().pad(5);
        table.add(musicVolumeSlider).width(400).pad(5).row();

        table.add(currentMusicLabel).left().pad(5);
        table.add(musicSelectBox).width(400).pad(5).row();

        table.add(sfxCheckBox).colspan(2).left().pad(5).row();
        table.add(reloadAutoCheckBox).colspan(2).left().pad(5).row();
        table.add(grayscaleCheckBox).colspan(2).left().pad(5).row();

        table.add(keyUpLabel).left().pad(5);
        table.add(changeKeyUpButton).width(400).pad(5).row();

        table.add(messageLabel).colspan(2).pad(5).row();

        table.add(saveButton).width(400).pad(5);
        table.add(backButton).width(150).pad(5);

        SettingsManager settings = SettingsManager.getInstance();
        MusicManager.getInstance().playMusic(settings.getCurrentMusic());
        MusicManager.getInstance().setVolume(settings.getMusicVolume());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
