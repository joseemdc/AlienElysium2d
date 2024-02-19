package com.josema.alienelysium2d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.josema.alienelysium2d.MyGdxGame;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.building.utilities.Alignment;

import javax.swing.GroupLayout;

public class MainScreen implements Screen {
    private MyGdxGame game;
    private AssetManager manager;
    private Viewport viewport;
    private Stage stage;
    private SpriteBatch sb;
    private Sprite backgroundSprite;


    public MainScreen(MyGdxGame game, AssetManager manager, SpriteBatch sb) {
        this.game = game;
        this.manager = manager;
        this.sb = sb;
    }

    public void loadScreen() {
        VisUI.load();
        // Grafo de escena que contendrá todo el menú
        //stage = new Stage();
        viewport = new ExtendViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        // Crea una tabla, donde añadiremos los elementos de menú
        Table table = new Table();
        table.right().top();


        //table.setPosition(MyGdxGame.V_WIDTH/MyGdxGame.PPM, MyGdxGame.V_HEIGHT/MyGdxGame.PPM);
        // La tabla ocupa toda la pantalla
        table.setFillParent(true);
        //table.setHeight(500);
        stage.addActor(table);

        // Etiqueta de texto
        Label label = new Label("Alien Elysium \n 2d Edition", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //label.setAlignment(GroupLayout.Alignment.CENTER.ordinal());
        label.setAlignment(Alignment.CENTER.ordinal());
        table.add(label).expandX();
        table.row().left();

        // Botón
        TextButton buttonPlay = new TextButton(MyGdxGame.myBundle.get("newGame"), new Skin(Gdx.files.internal("skin/uiskin.json")));
        //buttonPlay.setPosition(label.getOriginX(), label.getOriginY() - 120);
        buttonPlay.setWidth(200);
        buttonPlay.setHeight(40);
        buttonPlay.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                dispose();
                game.setScreen(new PlayScreen(game, manager));
                Gdx.app.log("Click", "Cambiar pantalla");
            }
        });
        table.add(buttonPlay).expandX().padLeft(10).padTop(5).padBottom(5);
        table.row().left();

        // Botón
        TextButton buttonHistory = new TextButton(MyGdxGame.myBundle.get("achievements"), new Skin(Gdx.files.internal("skin/uiskin.json")));
        buttonHistory.setPosition(label.getOriginX(), label.getOriginY() - 170);
        buttonHistory.setWidth(200);
        buttonHistory.setHeight(40);
        buttonHistory.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                dispose();
                game.setScreen(new PlayScreen(game, manager));
            }
        });
        table.add(buttonHistory).expandX().padLeft(10).padTop(5).padBottom(5);
        table.row().left();

        // Botón
        TextButton buttonConfig = new TextButton(MyGdxGame.myBundle.get("settings"), new Skin(Gdx.files.internal("skin/uiskin.json")));
        buttonConfig.setPosition(label.getOriginX(), label.getOriginY() - 220);
        buttonConfig.setWidth(200);
        buttonConfig.setHeight(40);
        buttonConfig.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(MyGdxGame.prefs.hasHaptic()){

                Gdx.input.vibrate(Input.VibrationType.LIGHT);
                }
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(MyGdxGame.prefs.hasHaptic()){

                Gdx.input.vibrate(Input.VibrationType.HEAVY);
                }
                dispose();
                game.setScreen(new SettingsScreen(game, manager,MainScreen.this));
                Gdx.app.log("Click", "Cambiar pantalla");
            }
        });
        table.add(buttonConfig).expandX().padLeft(10).padTop(5).padBottom(5);
        table.row().left();

        // Botón
        TextButton buttonQuit = new TextButton(MyGdxGame.myBundle.get("credits"), new Skin(Gdx.files.internal("skin/uiskin.json")));
        buttonQuit.setPosition(label.getOriginX(), label.getOriginY() - 270);
        buttonQuit.setWidth(200);
        buttonQuit.setHeight(40);
        buttonQuit.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                game.dispose();
                System.exit(0);
            }
        });
        table.add(buttonQuit).expandX().padLeft(10).padTop(5).padBottom(5);
        table.debugAll();


        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        sb.begin();
        backgroundSprite =new Sprite(manager.get("images/background.png", Texture.class));
        //backgroundSprite.setSize(MyGdxGame.V_WIDTH/MyGdxGame.PPM,MyGdxGame.V_HEIGHT/MyGdxGame.PPM);
        //backgroundSprite.draw(sb);
        sb.draw(backgroundSprite,Gdx.graphics.getHeight(),Gdx.graphics.getWidth());

        loadScreen();
        sb.end();
    }

    @Override
    public void render(float delta) {
        //limpia la pantalla
        //Gdx.gl.glClearColor(0, 0, 0, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        //backgroundSprite.draw(sb);
        sb.draw(backgroundSprite,MyGdxGame.V_WIDTH/MyGdxGame.PPM,MyGdxGame.V_HEIGHT/MyGdxGame.PPM);
        sb.end();
// Pinta el menú
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
        stage.dispose();
        VisUI.dispose();
    }
}
