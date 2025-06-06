package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.View.GameView;
import com.tilldawn.View.PreGameMenuView;

import java.awt.event.InputEvent;

public class PreGameMenuController {
    private PreGameMenuView view;
    private Pregame pregame;
    private final GameSettings settings;

    public PreGameMenuController(GameSettings settings) {
        this.settings = settings;

        settings.addKeyBinding("W");
        settings.addKeyBinding("A");
        settings.addKeyBinding("S");
        settings.addKeyBinding("D");

        settings.addCheatCode("FKFK");
        settings.addCheatCode("7f8f");
    }

    public void selectHero(Hero hero) {
        settings.setSelectedHero(hero);
    }

    public GameSettings getSettings() {
        return settings;
    }


    public void setView(PreGameMenuView view) {
        this.view = view;
        this.pregame = new Pregame();
    }

    public void handlePreGameMenuButtons() {
        view.getStartGameButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                if (settings.getSelectedHero() == null) {
                    Gdx.app.log("PreGame", "Please select a hero first!");
                    return;
                }

                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(
                    new GameView(
                        new GameController(settings),
                        GameAssetManager.getInstance().getSkin(),
                        settings
                    )
                );
            }
        });
    }

}
