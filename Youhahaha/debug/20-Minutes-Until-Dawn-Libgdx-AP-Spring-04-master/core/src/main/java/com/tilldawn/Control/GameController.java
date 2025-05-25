package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.View.GameOverScreen;
import com.tilldawn.View.GameView;

public class GameController {
    private GameView view;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;
    private EnemyController enemyController;
    private final GameSettings settings;
    private boolean isGameOverTriggered = false;
    private boolean isPaused = false;

    public GameController(GameSettings settings) {
        this.settings = settings;

    }

    public void setView(GameView view, GameSettings settings) {
        try {
            this.view = view;
            this.playerController = new PlayerController(new Player(settings.getSelectedHero(), view, Main.getUserManager().getCurrentUser()));
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


            playerController.getPlayer().setWeapon(chosen);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renderGame() {
        if (!Main.getBatch().isDrawing()) {
            Main.getBatch().begin();
        }

        try {
            worldController.render();
            playerController.getPlayer().render();
            weaponController.render();
            enemyController.render();


        } finally {
            if (Main.getBatch().isDrawing()) {
                Main.getBatch().end();
            }
        }

    }

    public void updateGame(float delta) {
        try {
        if (view != null) {

            worldController.update(delta);
            playerController.update(delta);
            weaponController.update(delta);
            enemyController.update(delta);


            if (!playerController.getPlayer().isAlive()) {
                handleGameEnd("You Died");
            }

        }
    } catch (Exception e) {
            System.out.println(e.getMessage());
        }}

    public int getXp(){
        return enemyController.getDeadEnemyCount();
    }

    public void handleGameEnd(String reason) {
        if (!isGameOverTriggered) {
            isGameOverTriggered = true;

            Gdx.app.postRunnable(() -> {
                Main.getMain().setScreen(
                    new GameOverScreen(
                        playerController.getPlayer(),
                        Main.getMain(),
                        Main.getUserManager(),
                        reason,
                        view.getRemainingTimeInSeconds(),
                        playerController.getPlayer().getEnemyKilled(),
                        playerController.getPlayer().getEnemyKilled() * 3,
                        (int)(view.getRemainingTimeInSeconds()/60 )* playerController.getPlayer().getEnemyKilled()
                        ));
            });
        }
    }



    public PlayerController getPlayerController() {
        return playerController;
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }

    public void pauseGame() {
        isPaused = true;
    }

    public void resumeGame() {
        isPaused = false;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public GameView getGameView() {
        return view;
    }

    public EnemyController getEnemyController(){
        return enemyController;
    }

}
