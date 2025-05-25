package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.*;
import com.tilldawn.Main;
import com.tilldawn.Model.*;



public class PreGameMenuView implements Screen {
    private Stage stage;
    private final Label title;
    private final SelectBox<HeroType>   heroBox;
    private final SelectBox<WeaponType> weaponBox;
    private final SelectBox<Integer>    durationBox;
    private final TextButton backButton, startButton;
    private final PreGameMenuController heroCtrl;
    private final WeaponMenuController weaponCtrl;
    private final DurationMenuController durationCtrl;
    private final GameStartController startCtrl;
    private final Skin skin;
    private final Main game;
    private final UserManager userManager;
    private final GameSettings settings;

    public PreGameMenuView(Main game, GameSettings settings, Skin skin, UserManager userManager) {
        this.game        = game;
        this.skin        = skin;
        this.userManager = userManager;
        this.title       = new Label("Pre-Game Setup", skin);
        this.heroBox     = new SelectBox<>(skin);
        this.weaponBox   = new SelectBox<>(skin);
        this.durationBox = new SelectBox<>(skin);
        this.backButton  = new TextButton("← Back", skin);
        this.startButton = new TextButton("Start Game", skin);

        this.heroCtrl     = new PreGameMenuController(settings);
        this.weaponCtrl   = new WeaponMenuController(settings);
        this.durationCtrl = new DurationMenuController(settings);
        this.startCtrl    = new GameStartController(settings);
        this.settings = settings;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        heroBox.setItems(new Array<>(HeroType.values()));
        weaponBox.setItems(new Array<>(WeaponType.values()));
        durationBox.setItems(new Array<>(new Integer[]{2, 5, 10, 20}));

        float boxWidth = Gdx.graphics.getWidth() * 0.7f;
        heroBox.setWidth(boxWidth);
        weaponBox.setWidth(boxWidth);
        durationBox.setWidth(boxWidth);

        Table table = new Table(skin);
        table.setFillParent(true);
        table.center().top().padTop(50);

        table.add(title).colspan(2).padBottom(20).row();
        table.add(new Label("Hero:", skin)).left().pad(10);
        table.add(heroBox).pad(10).row();
        table.add(new Label("Weapon:", skin)).left().pad(10);
        table.add(weaponBox).pad(10).row();
        table.add(new Label("Duration:", skin)).left().pad(10);
        table.add(durationBox).pad(10).row();
        table.add(backButton).width(150).pad(20);
        table.add(startButton).width(300).pad(20).row();

        stage.addActor(table);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainMenuController controller = new MainMenuController(game, userManager);
                game.setScreen(new MainMenuView(controller,
                    GameAssetManager.getInstance().getSkin(),
                    userManager.getCurrentUser(),
                    false));
            }
        });

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    HeroType heroType = heroBox.getSelected() != null ?
                        heroBox.getSelected() : HeroType.SHANA;
                    heroCtrl.selectHero(heroType);

                    WeaponType weaponType = weaponBox.getSelected() != null ?
                        weaponBox.getSelected() : WeaponType.SMG_DUAL;
                    weaponCtrl.selectWeapon(new Weapon(weaponType));


                    int duration = durationBox.getSelected() != null ?
                        durationBox.getSelected() : 5;
                    durationCtrl.selectDuration(duration);
                    durationCtrl.setTotalMinutes(duration);


                    GameController gameController = new GameController(settings);
                    game.setScreen(new GameView(
                        gameController,
                        skin,
                        settings
                    ));
                    System.out.println("GameView _ before calling setView");

                } catch (Exception e) {

                    MainMenuController mainMenuController = new MainMenuController(game, userManager);
                    Skin skin = GameAssetManager.getInstance().getSkin();
                    game.setScreen(new MainMenuView(
                        mainMenuController,
                        skin,
                        userManager.getCurrentUser(),
                        false // hasSave
                    ));
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
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

    public TextButton getStartGameButton() {
        return startButton;
    }
}
