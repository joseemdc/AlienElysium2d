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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.josema.alienelysium2d.MyGdxGame;
import com.josema.alienelysium2d.tools.HealthBar;

/**
 * Clase que representa el Hud del juego
 */
public class Hud implements Disposable {
    /**
     * Stage donde se dibujan los elementos
     */
    public Stage stage;
    /**
     * Viewport del Hud
     */
    private Viewport viewport;
    /**
     * Contador de tiempo
     */
    public Integer worldTimer;
    /**
     * Tiempo que ha pasado entre los frames
     */
    private float timeCount;
    /**
     * SpriteBatch del juego
     */

    private SpriteBatch sb;
    /**
     * Barra de vida del personaje
     */
    public  HealthBar healthBar;



    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label playerLabel;

    /**
     *Crea un Hud que contiene una barra de vida, un indicador de nivel y un contador en la parte superior de la pantalla
     * @param sb SpriteBatch del juego
     */
    public Hud (SpriteBatch sb){
        worldTimer=0;
        timeCount=0;

        this.sb=sb;
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        viewport=new FitViewport(MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT, new OrthographicCamera());
        //viewport= new StretchViewport(MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT, new OrthographicCamera());

//viewport=new FitViewport((float) MyGdxGame.V_WIDTH / 0.5f, (float) MyGdxGame.V_HEIGHT /0.5f, new OrthographicCamera());


        stage= new Stage(viewport,sb);

        Table table= new Table();
        table.top();
        table.setFillParent(true);
        table.setWidth(MyGdxGame.V_WIDTH);
        table.padTop(500/MyGdxGame.PPM).padLeft(3000/MyGdxGame.PPM).padRight(3000/MyGdxGame.PPM);



        countdownLabel= new Label(String.format("%03d",worldTimer),new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));

        healthBar = new HealthBar((int) (100/MyGdxGame.PPM), (int) (300/MyGdxGame.PPM));
        timeLabel= new Label("TIME",new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));
        levelLabel= new Label("1-1",new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));
        worldLabel= new Label("SECTOR",new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));
        playerLabel= new Label(MyGdxGame.myBundle.get("health"),new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));
    playerLabel.setFontScale(0.7f);
        worldLabel.setFontScale(0.7f);
        levelLabel.setFontScale(0.7f);
        timeLabel.setFontScale(0.7f);
        countdownLabel.setFontScale(0.7f);
        table.add(playerLabel).expandX().padTop(10).left();
        table.add(worldLabel).expandX().padTop(10).center();
        table.add(timeLabel).expandX().padTop(10).right();
        table.row();
        table.add(healthBar).left();
        table.add(levelLabel).center();
        table.add(countdownLabel).right();
        //table.debugAll();



        stage.addActor(table);
    }

    /**
     * Método update del Hud que actualiza el contador de tiempo
     * @param dt
     */
 public void update(float dt){
        timeCount += dt;
        if(timeCount>=1){
            worldTimer++;
            countdownLabel.setText(String.format("%03d",worldTimer));
            timeCount=0;
        }

 }

    /**
     * Se ejecuta para liberar recursos
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * Actualiza el tmaño del viewport
     * @param width Ancho
     * @param height Alto
     */
    public void resize(int width, int height){
        viewport.update(width, height);
    }
}
