package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.tilldawn.Model.GameSettings;
import com.tilldawn.Model.Player;
import com.tilldawn.View.GameView;

public class CheatManager {
    private final GameSettings settings;
    private final Player player;
    private GameView gameView;
    private GameController controller;

    public CheatManager(GameSettings settings, Player player, GameView gameView, GameController controller) {
        this.settings = settings;
        this.player = player;
        this.gameView = gameView;
        this.controller = controller;
    }

    public boolean handleCheat(char keyChar) {
        switch (keyChar) {
            case 'l':
               gameView.updateTimer(gameView.getRemainingTimeInSeconds() - 60f);
                return true;
            case 'u':
                player.setLevel(player.getLevel() + 1);
                return true;
            case 'j':
                if (player.getLives() <= 20) {
                    player.setLives(player.getLives() + 100);
                }
                return true;
            case 'b':
                controller.getEnemyController().getElderController().activate();
                return true;
            case 'k':
                player.getWeapon().setDamageMultiplier();
                return true;
            default:
                return false;
        }
    }
}
