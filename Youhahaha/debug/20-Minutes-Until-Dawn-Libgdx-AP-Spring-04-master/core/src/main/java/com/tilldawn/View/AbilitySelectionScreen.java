package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.Ability;

import java.util.List;
import java.util.function.Consumer;

public class AbilitySelectionScreen implements Screen {

    private Stage stage;
    private Skin skin;
    private Table table;
    private Label label;
    private List<Ability> abilities;
    private Consumer<Ability> onSelected;
    private GameController gameController;

    private Main main;

    public AbilitySelectionScreen(Main main, List<Ability> abilities, Consumer<Ability> onSelected, GameController gameController, Skin skin) {
        this.main = main;
        this.abilities = abilities;
        this.onSelected = onSelected;
        this.gameController = gameController;
        this.skin = skin ;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        table = new Table(skin);
        table.setFillParent(true);
        label = new Label("Select One Ability", skin);
        table.add(label).padBottom(20).row();

        for (Ability ability : abilities) {
            TextButton button = new TextButton(ability.getName(), skin);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    onSelected.accept(ability);
                    Main.getMain().setScreen(gameController.getGameView());
                }
            });
            table.add(button).pad(10).row();
        }

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(gameController.getGameView());
            }
        });
        table.add(backButton).padTop(20);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
