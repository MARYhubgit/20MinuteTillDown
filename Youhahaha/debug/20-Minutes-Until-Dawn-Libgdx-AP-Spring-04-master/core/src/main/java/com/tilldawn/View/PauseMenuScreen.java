package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.*;

public class PauseMenuScreen extends ScreenAdapter {
    private Main game;
    private Stage stage;
    private Player player;

    public PauseMenuScreen(Main game, Player player, GameController gameController) {
        this.game = game;
        this.player = player;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label pauseLabel = new Label("Paused", GameAssetManager.getInstance().getSkin());

        TextButton resumeButton = new TextButton("Resume", GameAssetManager.getInstance().getSkin());
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

         game.setScreen(gameController.getGameView());
            }
        });

        TextButton showCodesButton = new TextButton("Show Cheat Codes", GameAssetManager.getInstance().getSkin());
        showCodesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showCheatCodes();
            }
        });

        TextButton showAbilitiesButton = new TextButton("Show Unlocked Abilities", GameAssetManager.getInstance().getSkin());
        showAbilitiesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showUnlockedAbilities();
            }
        });


        TextButton giveUpButton = new TextButton("Give Up ", GameAssetManager.getInstance().getSkin());
        giveUpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

               gameController.handleGameEnd("Dead");
            }
        });

        TextButton saveButton = new TextButton("Save & Exit", GameAssetManager.getInstance().getSkin());
        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

//                                game.saveAndExit();
            }
        });

        table.add(pauseLabel).pad(10).row();
        table.add(resumeButton).width(300).pad(10).row();
        table.add(showCodesButton).width(700).pad(10).row();
        table.add(showAbilitiesButton).width(700).pad(10).row();
        table.add(giveUpButton).width(700).pad(10).row();
        table.add(saveButton).width(700).pad(10).row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.7f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    private void showUnlockedAbilities() {
        Dialog dialog = new Dialog("Unlocked Abilities", GameAssetManager.getInstance().getSkin());

        Table contentTable = new Table();

        contentTable.add(new Label("Abilities:", GameAssetManager.getInstance().getSkin())).left().pad(5).row();

        com.badlogic.gdx.scenes.scene2d.ui.List<String> abilityList = new com.badlogic.gdx.scenes.scene2d.ui.List<>(GameAssetManager.getInstance().getSkin());


        com.badlogic.gdx.utils.Array<String> abilityNames = new com.badlogic.gdx.utils.Array<>();
        for (Ability ability : player.getAbilities()) {
            abilityNames.add(ability.getName());
        }

        abilityList.setItems(abilityNames);
        abilityList.getStyle().font.getData().setScale(0.85f);

        ScrollPane scrollPane = new ScrollPane(abilityList, GameAssetManager.getInstance().getSkin());
        scrollPane.setFadeScrollBars(false);

        contentTable.add(scrollPane).width(600).height(300).pad(10).row();

        dialog.getContentTable().add(contentTable);

        dialog.button("Close", true);
        dialog.show(stage);
    }

    private void showCheatCodes() {
        Dialog dialog = new Dialog("Cheat Codes", GameAssetManager.getInstance().getSkin());

        Table contentTable = new Table();


        com.badlogic.gdx.scenes.scene2d.ui.List<String> cheatList = new com.badlogic.gdx.scenes.scene2d.ui.List<>(GameAssetManager.getInstance().getSkin());

        com.badlogic.gdx.utils.Array<String> cheatCodes = new com.badlogic.gdx.utils.Array<>();

        cheatCodes.add("l : decrease 1 min");
        cheatCodes.add("u : increase 1 level");
        cheatCodes.add("j : increase lives");
        cheatCodes.add("b : go to Boss fight");
        cheatCodes.add("k : increase danger");

        cheatList.setItems(cheatCodes);
        cheatList.getStyle().font.getData().setScale(0.85f);

        ScrollPane scrollPane = new ScrollPane(cheatList, GameAssetManager.getInstance().getSkin());
        scrollPane.setFadeScrollBars(false);

        contentTable.add(scrollPane).width(600).height(300).pad(10).row();

        dialog.getContentTable().add(contentTable);

        dialog.button("Close", true);
        dialog.show(stage);
    }




}
