package com.josema.alienelysium2d.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.josema.alienelysium2d.MyGdxGame;
import com.josema.alienelysium2d.tools.HealthBar;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private Integer worldTimer;
    private float timeCount;
    private Integer score;
    private SpriteBatch sb;
    public  HealthBar healthBar;



    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label playerLabel;


    public Hud (SpriteBatch sb){
        worldTimer=300;
        timeCount=0;
        score=0;
        this.sb=sb;
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        viewport=new FillViewport(MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT, new OrthographicCamera());

        stage= new Stage(viewport,sb);

        Table table= new Table();
        table.top();
        table.padTop(500/MyGdxGame.PPM).padLeft(1000/MyGdxGame.PPM).padRight(1000/MyGdxGame.PPM);
        table.setFillParent(true);

        countdownLabel= new Label(String.format("%03d",worldTimer),new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));
        scoreLabel= new Label(String.format("%06d",score),new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));
        healthBar = new HealthBar((int) (100/MyGdxGame.PPM), (int) (300/MyGdxGame.PPM));
        timeLabel= new Label("TIME",new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));
        levelLabel= new Label("1-1",new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));
        worldLabel= new Label("SECTOR",new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));
        playerLabel= new Label("PLAYER",new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));
    playerLabel.setFontScale(0.7f);
        worldLabel.setFontScale(0.7f);
        levelLabel.setFontScale(0.7f);
        timeLabel.setFontScale(0.7f);
        countdownLabel.setFontScale(0.7f);
        table.add(playerLabel).expandX().padTop(10).left();
        table.add(worldLabel).expandX().padTop(10).center();
        table.add(timeLabel).expandX().padTop(10).right();
        table.row();
        table.add(healthBar).expandX().left();
        table.add(levelLabel).expandX().center();
        table.add(countdownLabel).expandX().right();
        table.debugAll();



        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
