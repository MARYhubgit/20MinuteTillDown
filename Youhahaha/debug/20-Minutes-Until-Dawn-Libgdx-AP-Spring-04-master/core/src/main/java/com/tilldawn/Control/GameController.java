package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.tilldawn.Main;
import com.tilldawn.Model.GameSettings;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.Weapon;
import com.tilldawn.Model.WeaponType;
import com.tilldawn.View.GameView;

public class GameController {
    private GameView view;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;
    private EnemyController enemyController;
    private final GameSettings settings;

    public GameController(GameSettings settings) {
        this.settings = settings;
    }

    public void setView(GameView view, GameSettings settings) {
        this.view = view;
        this.playerController = new PlayerController(new Player());
        this.worldController = new WorldController(playerController, view.getGameCamera());

        Weapon chosen = settings.getSelectedWeapon();
        if (chosen == null) {
            chosen = new Weapon(WeaponType.SMG_DUAL);
        }

        this.enemyController = new EnemyController(playerController);

        this.weaponController = new WeaponController(
            chosen,
            playerController.getPlayer(),
            view.getGameCamera(),
            settings,
            enemyController
        );
    }

    public void renderGame() {

        if (!Main.getBatch().isDrawing()) {
            Main.getBatch().begin();
        }

        try {
            worldController.render();
            playerController.getPlayer().render();
            enemyController.render();
            weaponController.render();
        } finally {
            if (Main.getBatch().isDrawing()) {
                Main.getBatch().end();
            }
        }
    }

    public void updateGame(float delta) {
        if (view != null) {

            playerController.update(delta);
            weaponController.update(delta);
            enemyController.update(delta);
            worldController.update(delta);
        }
    }


    public PlayerController getPlayerController() {
        return playerController;
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }
}
