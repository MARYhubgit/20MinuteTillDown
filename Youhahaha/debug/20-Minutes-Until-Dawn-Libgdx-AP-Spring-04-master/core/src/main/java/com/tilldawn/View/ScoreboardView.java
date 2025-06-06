package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.ScoreboardController;
import com.tilldawn.Main;
import com.tilldawn.Model.User;
import com.tilldawn.Model.UserManager;

import java.util.List;

public class ScoreboardView implements Screen {
    private final Stage stage;
    private final ScoreboardController controller;
    private final Skin skin;
    private Table table;
    private final TextButton backButton;
    private final UserManager userManager;

    public ScoreboardView(Skin skin, UserManager userManager) {
        this.skin = skin;
        this.controller = new ScoreboardController();
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.backButton = new TextButton("← Back", skin);
        this.userManager = userManager;


    }

    @Override
    public void show() {
        table = new Table(skin);
        table.setFillParent(true);
        table.setFillParent(true);
        table.padTop(20).top();


        Label title = new Label("Scoreboard", skin);
        table.add(title).colspan(5).padTop(20).row();

        TextButton sortScore = new TextButton("Sort by Score", skin);
        TextButton sortName  = new TextButton("Sort by Username", skin);
        TextButton sortKills = new TextButton("Sort by Kills", skin);
        TextButton sortTime  = new TextButton("Sort by Survival", skin);

        table.add(sortScore).pad(5);
        table.add(sortName).pad(5);
        table.add(sortKills).pad(5);
        table.add(sortTime).pad(5).row();
        // ستون‌ها
        table.add("Username").pad(5);
        table.add("Score").pad(5);
        table.add("Kills").pad(5);
        table.add("Survival").pad(5).row();

        displayUsers(controller.getSortedByScore());


        sortScore.addListener(e -> { refresh(controller.getSortedByScore()); return true; });
        sortName.addListener(e -> { refresh(controller.getSortedByUsername()); return true; });
        sortKills.addListener(e -> { refresh(controller.getSortedByKills()); return true; });
        sortTime.addListener(e -> { refresh(controller.getSortedBySurvivalTime()); return true; });

        stage.addActor(table);
        table.add(backButton).colspan(4).padTop(50).bottom();

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main game = (Main) Main.getMain();
                game.setScreen(new MainMenuView(
                    new MainMenuController(game,userManager),
                    skin,
                    userManager.getCurrentUser(),
                    false
                ));
            }
        });

    }

    private void displayUsers(List<User> users) {
        String current = "";
        if (UserManager.getInstance().getCurrentUser() != null) {
            current = UserManager.getInstance().getCurrentUser().getUsername();
        }

        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            Label name  = new Label(u.getUsername(), skin);
            Label score = new Label(String.valueOf(u.getScore()), skin);
            Label kills = new Label(String.valueOf(u.getKills()), skin);
            Label time  = new Label(controller.formatSurvivalTime(u.getSurvivalTimeInSeconds()), skin);


            if (i == 0) name.setColor(Color.GOLD);
            if (i == 1) name.setColor(new Color(0.75f, 0.75f, 0.75f, 1f)); // Silver
            if (i == 2) name.setColor(new Color(0.8f, 0.5f, 0.2f, 1f));

            if (u.getUsername().equals(current)) {
                name.setColor(Color.CYAN);
                score.setColor(Color.CYAN);
                kills.setColor(Color.CYAN);
                time.setColor(Color.CYAN);
            }

            table.add(name).pad(5);
            table.add(score).pad(5);
            table.add(kills).pad(5);
            table.add(time).pad(5).row();
        }
    }

    private void refresh(List<User> sorted) {
        table.clearChildren(); // فقط محتوا، نه کل table
        show(); // جدول رو از نو می‌سازه
    }

    @Override public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }


    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}
