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
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.josema.alienelysium2d.MyGdxGame;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.building.utilities.Alignment;

import javax.swing.GroupLayout;

/**
 * Pantalla principal
 */
public class MainScreen implements Screen {
    /**
     * Clase principal del juego
     */
    private MyGdxGame game;
    /**
     * AssetManager del juego
     */
    private AssetManager manager;
    /**
     * Viewport de la pantalla de créditos
     */
    private Viewport viewport;
    /**
     * Stage donde dibujar los elementos
     */
    private Stage stage;
    /**
     * SpriteBatch del juego
     */
    private SpriteBatch sb;

    /**
     * Sprite que equivale a la imagen del fondo
     */
    private Sprite backgroundSprite;

    /**
     *
     * @param game Clase principal del juego
     * @param manager Assetmanager del juego
     * @param sb SpriteBatch del juego
     */
    public MainScreen(MyGdxGame game, AssetManager manager, SpriteBatch sb) {
        this.game = game;
        this.manager = manager;
        this.sb = sb;
    }
    /**
     * Carga los elementos de la pantalla como textos y botones y se establecen sus propiedades
     */
    public void loadScreen() {
        VisUI.load();
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = MyGdxGame.fontUi;
        textButtonStyle.fontColor = Color.WHITE;
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        // Grafo de escena que contendrá todo el menú
        //stage = new Stage();
        viewport = new FillViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        //viewport = new ScreenViewport(new OrthographicCamera());
        stage = new Stage(viewport, sb);


        // Crea una tabla, donde añadiremos los elementos de menú
        Table table = new Table();
        table.padLeft(2000 / MyGdxGame.PPM).padRight(2000 / MyGdxGame.PPM);


        //table.setPosition(MyGdxGame.V_WIDTH/MyGdxGame.PPM, MyGdxGame.V_HEIGHT/MyGdxGame.PPM);
        // La tabla ocupa toda la pantalla
        table.setFillParent(true);
        //table.setHeight(500);
        stage.addActor(table);

        // Etiqueta de texto
        Label label = new Label("Alien Elysium\n2d Edition", new Label.LabelStyle(MyGdxGame.fontLogo, Color.WHITE));
        //label.setAlignment(GroupLayout.Alignment.CENTER.ordinal());
        label.setAlignment(Alignment.CENTER.ordinal());
        table.add(label).expandX().size(9000f / MyGdxGame.PPM, 2000f / MyGdxGame.PPM).padTop(20).padBottom(20);
        table.row().left();

        // Botón
        TextButton buttonPlay = new TextButton(MyGdxGame.myBundle.get("newGame"), skin);
        //buttonPlay.setPosition(label.getOriginX(), label.getOriginY() - 120);
        textButtonStyle = buttonPlay.getStyle();

        textButtonStyle.font = MyGdxGame.fontUi;
        buttonPlay.setStyle(textButtonStyle);
        buttonPlay.setSkin(skin);
        // buttonPlay.setWidth(200/MyGdxGame.PPM);
        //buttonPlay.setHeight(40/MyGdxGame.PPM);
        buttonPlay.getLabel().setFontScale(0.7f);
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
        table.add(buttonPlay).expandX().padLeft(10).padTop(5).padBottom(5).size(9000f / MyGdxGame.PPM, 2000f / MyGdxGame.PPM);
        table.row().left();

        // Botón
        TextButton buttonAchievements = new TextButton(MyGdxGame.myBundle.get("records"), skin);
        //buttonAchievements.setPosition(label.getOriginX(), label.getOriginY() - 170);
        buttonAchievements.setStyle(textButtonStyle);
        buttonAchievements.setSkin(skin);
        buttonAchievements.setWidth(200);
        buttonAchievements.setHeight(40);
        buttonAchievements.getLabel().setFontScale(0.7f);
        buttonAchievements.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                dispose();
                game.setScreen(new RecordsScreen(game, manager, MainScreen.this, sb));
            }
        });
        table.add(buttonAchievements).expandX().padLeft(10).padTop(5).padBottom(5).size(9000f / MyGdxGame.PPM, 2000f / MyGdxGame.PPM);
        table.row().left();

        // Botón
        TextButton buttonConfig = new TextButton(MyGdxGame.myBundle.get("settings"), skin);
        //buttonConfig.setPosition(label.getOriginX(), label.getOriginY() - 220);
        buttonConfig.setStyle(textButtonStyle);
        buttonConfig.setSkin(skin);
        buttonConfig.setWidth(200);
        buttonConfig.setHeight(40);
        buttonConfig.getLabel().setFontScale(0.7f);
        buttonConfig.addListener(new InputListener() {
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

                game.setScreen(new SettingsScreen(game, manager, MainScreen.this, sb));
                Gdx.app.log("Click", "Cambiar pantalla");
            }
        });
        table.add(buttonConfig).expandX().padLeft(10).padTop(5).padBottom(5).size(9000f / MyGdxGame.PPM, 2000f / MyGdxGame.PPM);
        table.row().left();

        // Botón
        TextButton buttonCredits = new TextButton(MyGdxGame.myBundle.get("credits"), skin);
        // buttonCredits.setPosition(label.getOriginX(), label.getOriginY() - 270);
        buttonCredits.setStyle(textButtonStyle);
        buttonCredits.setSkin(skin);
        buttonCredits.setWidth(200);
        buttonCredits.setHeight(40);
        buttonCredits.getLabel().setFontScale(0.7f);
        buttonCredits.addListener(new InputListener() {
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
                game.setScreen(new CreditsScreen(game, manager, MainScreen.this, sb));
            }
        });
        table.add(buttonCredits).expandX().padLeft(10).padTop(5).padBottom(5).size(9000f / MyGdxGame.PPM, 2000f / MyGdxGame.PPM);
        // Botón
        TextButton buttonHelp = new TextButton(MyGdxGame.myBundle.get("help"), skin);
        // buttonCredits.setPosition(label.getOriginX(), label.getOriginY() - 270);
        buttonHelp.setStyle(textButtonStyle);
        buttonHelp.setSkin(skin);
        buttonHelp.setWidth(200);
        buttonHelp.setHeight(40);
        buttonHelp.getLabel().setFontScale(0.7f);
        buttonHelp.addListener(new InputListener() {
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
                game.setScreen(new HelpScreen(game, manager, MainScreen.this, sb));
            }
        });
        table.add(buttonHelp).expandX().padLeft(10).right().padTop(5).padBottom(5).size(9000f / MyGdxGame.PPM, 2000f / MyGdxGame.PPM);

        //table.debugAll();


        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }
    /**
     * Establece el fondo de la pantalla y llama a {@link #loadScreen()} para añadir el resto de elementos
     */
    @Override
    public void show() {

        backgroundSprite = new Sprite(manager.get("images/background.png", Texture.class));
        backgroundSprite.setSize(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT);

        //  sb.draw(backgroundSprite,MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT);

        loadScreen();

    }
    /**
     * Dibuja el fondo y luego los elementos que estén en el Stage, comprueba si el usuario hace click en el botón de ir atrás
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        //limpia la pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        backgroundSprite.draw(sb);
        //sb.draw(backgroundSprite,MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT);
        sb.end();
// Pinta el menú
        stage.act(delta);

        stage.draw();
    }
    /**
     * Actualiza el viewport con las nuevas dimensiones
     * @param width Ancho
     * @param height Alto
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

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
