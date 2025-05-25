
package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class GameAssetManager {
    private static GameAssetManager instance;
    private final Skin skin;
    private Animation<TextureRegion> eyebatFlyAnim;
    private Animation<TextureRegion> elderFlyAnim;
    private Animation<TextureRegion> tentacleWalkAnim;
    private Animation<TextureRegion> characterIdleAnim;
    private Animation<TextureRegion> characterRunAnim;
    private Texture playerIdleTexture;
    private Texture playerRunTexture;
    private Animation<TextureRegion> playerIdleAnim;
    private Texture smgTexture;
    private Texture bulletTexture;
    private Animation<TextureRegion> treeIdleAnim;
    private Animation<TextureRegion> treeWalkAnim;
    private Texture[] treePowerupTexture = new Texture[2];
    private Texture elderSingleTexture;
    private Array<TextureRegion> managedTextures = new Array<>();

    private GameAssetManager() {
        skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
        loadTextures();
    }

    public static synchronized GameAssetManager getInstance() {
        if (instance == null) {
            instance = new GameAssetManager();
        }
        return instance;
    }

    public void loadTextures() {
        loadEyebatAnimations();
        loadTentacleAnimations();
        loadElderAnimations();

        Array<TextureRegion> playerFrames = new Array<>();
        for (int i = 0; i < 6; i++) {
            playerFrames.add(new TextureRegion(new Texture("1/Idle_" + i + ".png")));
        }
        playerIdleAnim = new Animation<>(0.1f, playerFrames);
        playerIdleTexture = new Texture("1/Idle_0.png");
        playerRunTexture = new Texture("1/characters/Run_0.png");
        smgTexture = new Texture("smg/SMGStill.png");
        bulletTexture = new Texture("bullet.png");
        loadTreeAssets();
        loadCharacterAnimations();
    }

    private void loadEyebatAnimations() {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(new Texture("eyebat/T_EyeBat_" + i + ".png")));
        }
        eyebatFlyAnim = new Animation<>(0.1f, frames);
    }

//    private void loadElderAnimations() {
//        TextureRegion frame = new TextureRegion(new Texture("elder/T_elder_1.png"));
//        elderFlyAnim = new Animation<>(0.1f, frame);
//    }

    private void loadElderAnimations() {
        elderSingleTexture = new Texture(Gdx.files.internal("elder/T_elder_1.png"));
        Array<TextureRegion> singleFrame = new Array<>();
        singleFrame.add(new TextureRegion(elderSingleTexture));
        elderFlyAnim = new Animation<>(0.1f, singleFrame);


        System.out.println("Elder assets loaded (single-frame animation)");
    }





    private void loadTentacleAnimations() {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(new Texture("tentacle/walk_" + i + ".png")));
        }
        tentacleWalkAnim = new Animation<>(0.1f, frames);
    }



    public void loadCharacterAnimations() {
        Array<TextureRegion> idleFrames = new Array<>();
        for (int i = 0; i < 6; i++) {
            idleFrames.add(new TextureRegion(new Texture("1/Idle_" + i + ".png")));
        }
        characterIdleAnim = new Animation<>(0.1f, idleFrames);

        Array<TextureRegion> runFrames = new Array<>();

        runFrames.add(new TextureRegion(new Texture("1/characters/Run_0.png")));
        runFrames.add(new TextureRegion(new Texture("1/characters/Run_1.png")));
        runFrames.add(new TextureRegion(new Texture("1/characters/Run_2.png")));
        runFrames.add(new TextureRegion(new Texture("1/characters/Run_3.png")));
        characterRunAnim = new Animation<>(0.08f, runFrames);
    }

    public Texture getPlayerIdleTexture() {
        return playerIdleTexture;
    }

    public Texture getPlayerRunTexture() {
        return playerRunTexture;
    }

    public Animation<TextureRegion> getTentacleWalkAnim() {
        return new Animation<>(0.1f,
            new TextureRegion(new Texture("tentacle/walk_0.png")),
            new TextureRegion(new Texture("tentacle/walk_1.png")),
            new TextureRegion(new Texture("tentacle/walk_2.png")),
            new TextureRegion(new Texture("tentacle/walk_3.png"))
        );
    }

    private void loadTreeAssets() {
        Array<TextureRegion> treeIdleFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            treeIdleFrames.add(new TextureRegion(new Texture("TreeMonster/T_TreeMonster_" + i + ".png")));
        }
        treeIdleAnim = new Animation<>(0.15f, treeIdleFrames);

        Texture walkSheet = new Texture("TreeMonster/T_TreeMonsterWalking.png");
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/4, walkSheet.getHeight());
        Array<TextureRegion> walkFrames = new Array<>();
        for (int i = 0; i < 4; i++) {
            walkFrames.add(tmp[0][i]);
        }
        treeWalkAnim = new Animation<>(0.1f, walkFrames);

        for (int i = 0; i < 2; i++) {
            treePowerupTexture[i] = new Texture("TreeMonster/T_PowerupTreeArrows_" + i + ".png");
        }
    }

    public void dispose() {
        skin.dispose();
        smgTexture.dispose();
        bulletTexture.dispose();
        playerIdleTexture.dispose();
        playerRunTexture.dispose();
        elderSingleTexture.dispose();


        for (Object obj : eyebatFlyAnim.getKeyFrames()) {
            TextureRegion region = (TextureRegion) obj;
            region.getTexture().dispose();
        }

        for (Object obj : tentacleWalkAnim.getKeyFrames()) {
            TextureRegion region = (TextureRegion) obj;
            region.getTexture().dispose();
        }


        for (Texture tex : treePowerupTexture) {
            if (tex != null) tex.dispose();
        }
    }

    public Texture get(String path, Class<Texture> type) {
        if (type == Texture.class) {
            return new Texture(Gdx.files.internal(path));
        }
        return null;
    }

    public Animation<TextureRegion> getEyebatFlyAnim() {
        return eyebatFlyAnim;
    }

    public Texture getTexture() {
        return smgTexture;
    }

    public Skin getSkin() { return skin; }
    public Animation<TextureRegion> getTreeIdleAnim() { return treeIdleAnim; }
    public Animation<TextureRegion> getCharacterIdleAnimation() { return characterIdleAnim; }
    public Animation<TextureRegion> getCharacterRunAnimation() { return characterRunAnim; }
    public Animation<TextureRegion> getElderFlyAnim() {
        return elderFlyAnim;
    }
    public Texture getElderSingleTexture() {
        return elderSingleTexture;
    }


}
