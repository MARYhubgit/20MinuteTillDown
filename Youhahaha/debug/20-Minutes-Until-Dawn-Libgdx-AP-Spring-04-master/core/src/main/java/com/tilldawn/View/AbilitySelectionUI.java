package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Model.Ability;

import java.util.List;
import java.util.function.Consumer;

public class AbilitySelectionUI {
    private Stage stage;
    private Skin skin;
    private Table table;
    private Label label;

    public AbilitySelectionUI(Skin sharedSkin) {
        this.skin = sharedSkin;
        stage = new Stage(new ScreenViewport());
        table = new Table(skin);
        table.setFillParent(true);
        label = new Label("Select One Ability", skin);
        table.add(label).padBottom(20).row();
        stage.addActor(table);
    }


    public void show(List<Ability> abilities, Consumer<Ability> onSelected) {
        table.clearChildren();
        table.add(label).padBottom(20).row();

        for (Ability ability : abilities) {
            TextButton button = new TextButton(ability.getName(), skin);
            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    onSelected.accept(ability);
                    stage.clear();
                    Gdx.input.setInputProcessor(null);
                }
            });
            table.add(button).pad(10);
        }

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }

}
