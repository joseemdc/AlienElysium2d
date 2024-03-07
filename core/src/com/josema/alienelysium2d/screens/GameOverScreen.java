package com.josema.alienelysium2d.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.josema.alienelysium2d.MyGdxGame;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.building.utilities.Alignment;

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private SpriteBatch sb;
    private AssetManager manager;

    private Sprite backgroundSprite;
    private MyGdxGame game;
    private Label label;

    public GameOverScreen(MyGdxGame game, AssetManager manager, SpriteBatch sb) {
        this.game = game;
        this.sb=sb;
        this.manager=manager;


    }
    public void loadScreen(){
        viewport = new FillViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        VisUI.load();
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = MyGdxGame.fontUi;
        textButtonStyle.fontColor = Color.WHITE;
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Table table = new Table();
        table.center();
        table.setFillParent(true);
        table.setSize(MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT);
        label = new Label(MyGdxGame.myBundle.get("gameOver"), new Label.LabelStyle(MyGdxGame.fontLogo, Color.WHITE));
        label.setAlignment(Alignment.CENTER.ordinal());
        //label.setAlignment(GroupLayout.Alignment.CENTER.ordinal());
         table.add(label).padBottom(20).size(9000f / MyGdxGame.PPM, 2000f / MyGdxGame.PPM);
        //table.add(label).expandX();
        table.row();
        // Botón
        TextButton buttonPlayAgain = new TextButton(MyGdxGame.myBundle.get("playAgain"), skin);
        //buttonPlayAgain.setPosition(label.getOriginX(), label.getOriginY() - 220);
        buttonPlayAgain.setStyle(textButtonStyle);
        buttonPlayAgain.setSkin(skin);
        buttonPlayAgain.setWidth(200);
        buttonPlayAgain.setHeight(40);
        buttonPlayAgain.getLabel().setFontScale(0.7f);
        buttonPlayAgain.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (MyGdxGame.prefs.hasHaptic()) {

                    Gdx.input.vibrate(Input.VibrationType.LIGHT);
                }
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (MyGdxGame.prefs.hasHaptic()) {

                    Gdx.input.vibrate(Input.VibrationType.HEAVY);
                }
                dispose();

                game.setScreen(new PlayScreen(game,manager));
                Gdx.app.log("Click", "Cambiar pantalla");
            }
        });
        table.add(buttonPlayAgain).expandX().padLeft(10).padTop(5).padBottom(5).size(9000f / MyGdxGame.PPM, 2000f / MyGdxGame.PPM);
        table.row().left();

        // Botón
        TextButton buttonMainMenu = new TextButton(MyGdxGame.myBundle.get("mainMenu"), skin);
        // buttonMainMenu.setPosition(label.getOriginX(), label.getOriginY() - 270);
        buttonMainMenu.setStyle(textButtonStyle);
        buttonMainMenu.setSkin(skin);
        buttonMainMenu.setWidth(200);
        buttonMainMenu.setHeight(40);
        buttonMainMenu.getLabel().setFontScale(0.7f);
        buttonMainMenu.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (MyGdxGame.prefs.hasHaptic()) {

                    Gdx.input.vibrate(Input.VibrationType.LIGHT);
                }
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (MyGdxGame.prefs.hasHaptic()) {

                    Gdx.input.vibrate(Input.VibrationType.HEAVY);
                }
                dispose();
                game.setScreen(new MainScreen(game,manager,sb));
            }
        });
        table.add(buttonMainMenu).expandX().padLeft(10).padTop(5).padBottom(5).size(9000f / MyGdxGame.PPM, 2000f / MyGdxGame.PPM);
        stage.addActor(table);
    }

    @Override
    public void show() {
        backgroundSprite =new Sprite(manager.get("images/background.png", Texture.class));
        backgroundSprite.setSize(MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT);
        loadScreen();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        //backgroundSprite.setPosition((float) MyGdxGame.V_WIDTH/2/MyGdxGame.PPM , (float) MyGdxGame.V_HEIGHT/2/MyGdxGame.PPM );

        backgroundSprite.draw(sb);
        // sb.draw(backgroundSprite,MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT);
        sb.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
