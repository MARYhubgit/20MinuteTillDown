package com.tilldawn.Control;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tilldawn.Model.Enemy;

public class EnemyController {
    private PlayerController playerController;
    private TreeController treeController;
    private TentacleController tentacleController;
    private EyebatController eyebatController;
    private ElderController elderController;


    public EnemyController(PlayerController playerController) {
        this.playerController = playerController;
        treeController = new TreeController(playerController);
        tentacleController = new TentacleController(playerController);
        eyebatController = new EyebatController(playerController.getPlayer());
        try {
            elderController = new ElderController(playerController);
        }catch(NullPointerException e){
            System.out.println("Elder controller is null!"+ e.getMessage());
        }
    }

    public Array<Enemy> getAllEnemies() {
        Array<Enemy> allEnemies = new Array<>();

        if (treeController.getTrees() != null) {
            allEnemies.addAll(treeController.getTrees());
        }

        if (tentacleController.getTentacles() != null) {
            allEnemies.addAll(tentacleController.getTentacles());
        }

        if (eyebatController.getBats() != null) {
            allEnemies.addAll(eyebatController.getBats());
        }

        return allEnemies;
    }

    public void update(float deltaTime) {
        Vector2 playerPos = new Vector2(
            playerController.getPlayer().getPosX(),
            playerController.getPlayer().getPosY()
        );

        treeController.update(deltaTime);
        tentacleController.update(deltaTime);
        eyebatController.update(deltaTime);
        elderController.update(deltaTime);
    }

    public void render() {
        treeController.render();
        tentacleController.render();
        eyebatController.render();
        elderController.render();
    }

    public int getAliveEnemyCount() {
        int count = 0;
        for (Enemy enemy : getAllEnemies()) {
            if (enemy.isAlive()) {
                count++;
            }
        }
        return count;
    }

    public int getDeadEnemyCount() {
        int deadCount = getAllEnemies().size - getAliveEnemyCount();
        return deadCount;
    }

    public ElderController getElderController() {
        return elderController;
    }



}
