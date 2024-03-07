package com.josema.alienelysium2d.screens;

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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.josema.alienelysium2d.MyGdxGame;
import com.kotcrab.vis.ui.building.utilities.Alignment;

import java.util.ArrayList;

/**
 * Pantalla de records
 */
public class RecordsScreen implements Screen {
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
     * Pantalla principal desde la que se lanza la pantalla de créditos
     */
    private MainScreen mainScreen;
    /**
     * Sprite que equivale a la imagen del fondo
     */
    private Sprite backgroundSprite;
    Label label;
    ScrollPane scrollPane;
    VerticalGroup verticalGroup;
    TextButton buttonBack;

    /**
     * @param game       Clase principal del juego
     * @param manager    Assetmanager del juego
     * @param mainScreen Pantalla principal desde la que se lanza
     * @param sb         SpriteBatch del juego
     */
    public RecordsScreen(MyGdxGame game, AssetManager manager, MainScreen mainScreen, SpriteBatch sb) {
        this.game = game;
        this.manager = manager;
        this.sb = sb;
        this.mainScreen = mainScreen;
    }

    /**
     * Carga los elementos de la pantalla como textos y botones y se establecen sus propiedades
     */
    public void loadScreen() {
        viewport = new FillViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = MyGdxGame.fontUi;
        textButtonStyle.fontColor = Color.WHITE;
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Crea una tabla, donde añadiremos los elementos de menú
        Table table = new Table();
        table.setSize(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT);
        table.pad(2000 / MyGdxGame.PPM);

        // La tabla ocupa toda la pantalla
        table.setFillParent(true);
        //table.setHeight(500);
        stage.addActor(table);
        // Etiqueta de texto
        label = new Label(MyGdxGame.myBundle.get("records"), new Label.LabelStyle(MyGdxGame.fontLogo, Color.WHITE));
        label.setAlignment(Alignment.CENTER.ordinal());
        //label.setAlignment(GroupLayout.Alignment.CENTER.ordinal());
        table.add(label).expandX().padBottom(10).size(9000f / MyGdxGame.PPM, 2000f / MyGdxGame.PPM);
        table.row();
        verticalGroup = new VerticalGroup();
        verticalGroup.space(10);
        ArrayList<Integer> records = MyGdxGame.prefs.getRecords();
        if (records.size() > 0) {

            for (int i = 0; i < records.size(); i++) {
                Label record = new Label(String.format("SECTOR 1    TIME: %d", records.get(i)), new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));
                Gdx.app.log("RECORDS", "Hay records");

                verticalGroup.addActor(record);
            }
        }
        scrollPane = new ScrollPane(verticalGroup);
        table.add(scrollPane);
        table.row();
        buttonBack = new TextButton(MyGdxGame.myBundle.get("back"), skin);

        //buttonPlay.setPosition(label.getOriginX(), label.getOriginY() - 120);
        textButtonStyle = buttonBack.getStyle();


        textButtonStyle.font = MyGdxGame.fontUi;
        buttonBack.setStyle(textButtonStyle);
        buttonBack.setSkin(skin);
        buttonBack.setWidth(200);
        buttonBack.setHeight(40);
        buttonBack.getLabel().setFontScale(0.7f);
        buttonBack.addListener(new InputListener() {
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
                game.setScreen(mainScreen);
                Gdx.app.log("Click", "Cambiar pantalla");
            }
        });
        table.add(buttonBack).expandX().pad(10).size(9000f / MyGdxGame.PPM, 2000f / MyGdxGame.PPM);

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Establece el fondo de la pantalla y llama a {@link #loadScreen()} para añadir el resto de elementos
     */
    @Override
    public void show() {
        backgroundSprite = new Sprite(manager.get("images/background.png", Texture.class));
        backgroundSprite.setSize(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT);
        loadScreen();
    }

    /**
     * Dibuja el fondo y luego los elementos que estén en el Stage, comprueba si el usuario hace click en el botón de ir atrás
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        //limpia la pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
// Pinta el menú
        sb.begin();
        //backgroundSprite.setPosition((float) MyGdxGame.V_WIDTH/2/MyGdxGame.PPM , (float) MyGdxGame.V_HEIGHT/2/MyGdxGame.PPM );

        backgroundSprite.draw(sb);
        // sb.draw(backgroundSprite,MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT);
        sb.end();

        stage.act(delta);

        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            game.setScreen(mainScreen);
        }
    }

    /**
     * Actualiza el viewport con las nuevas dimensiones
     *
     * @param width  Ancho
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
    }
}
