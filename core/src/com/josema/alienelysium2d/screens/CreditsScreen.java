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
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.josema.alienelysium2d.MyGdxGame;
import com.kotcrab.vis.ui.building.utilities.Alignment;

public class CreditsScreen implements Screen {
    private MyGdxGame game;
    private AssetManager manager;
    private Viewport viewport;
    private Stage stage;
    private SpriteBatch sb;
    private MainScreen mainScreen;
    private Sprite backgroundSprite;
    private boolean isUserScrolling = false;
    Label label;
    ScrollPane scrollPane;
    VerticalGroup verticalGroup;
    Label personajeslabel;

    TextButton buttonBack;

    public CreditsScreen(MyGdxGame game, AssetManager manager,MainScreen mainScreen,SpriteBatch sb){
        this.game = game;
        this.manager = manager;
        this.sb=sb;
        this.mainScreen=mainScreen;
    }
    public void loadScreen(){
        viewport = new FillViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        TextButton.TextButtonStyle textButtonStyle= new TextButton.TextButtonStyle();
        textButtonStyle.font=MyGdxGame.fontUi;
        textButtonStyle.fontColor= Color.WHITE;
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Crea una tabla, donde añadiremos los elementos de menú
        Table table = new Table();
        table.setSize(MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT);
        table.pad(2000/MyGdxGame.PPM);

        // La tabla ocupa toda la pantalla
        table.setFillParent(true);
        //table.setHeight(500);
        stage.addActor(table);
        // Etiqueta de texto
        label = new Label(MyGdxGame.myBundle.get("credits"), new Label.LabelStyle(MyGdxGame.fontLogo, Color.WHITE));
        label.setAlignment(Alignment.CENTER.ordinal());
        //label.setAlignment(GroupLayout.Alignment.CENTER.ordinal());
        table.add(label).expandX().padBottom(10).size(9000f/MyGdxGame.PPM,2000f/MyGdxGame.PPM);
        table.row();

        Container contaniner= new Container();
        contaniner.height(1800/MyGdxGame.PPM);
        Container contaniner2= new Container();
        contaniner2.height(1800/MyGdxGame.PPM);
        //contaniner.debugAll();
        Label developerLabel= new Label(MyGdxGame.myBundle.get("developedBy"), new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));

        personajeslabel= new Label(MyGdxGame.myBundle.get("characters"), new Label.LabelStyle(MyGdxGame.fontLogo, Color.WHITE));
        personajeslabel.setAlignment(Alignment.CENTER.ordinal());
        personajeslabel.setFontScale(0.7f);

        Label mainCharacterlabel= new Label(MyGdxGame.myBundle.get("mainCharacter")+": mayor676, Chasersgaming", new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));
        Label alienLabel= new Label(MyGdxGame.myBundle.get("alien")+": Omni_Theorem", new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));

        Label musiclabel= new Label(MyGdxGame.myBundle.get("musicAndSounds"), new Label.LabelStyle(MyGdxGame.fontLogo, Color.WHITE));
        musiclabel.setAlignment(Alignment.CENTER.ordinal());
        musiclabel.setFontScale(0.7f);

        Label menuThemelabel= new Label(MyGdxGame.myBundle.get("menuTheme")+": ", new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));
        Label playThemelabel= new Label(MyGdxGame.myBundle.get("playTheme")+": ", new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));


        verticalGroup= new VerticalGroup();
        verticalGroup.space(10);
        verticalGroup.addActor(contaniner);
        verticalGroup.addActor(developerLabel);
        verticalGroup.addActor(personajeslabel);
        verticalGroup.addActor(mainCharacterlabel);
        verticalGroup.addActor(alienLabel);
        verticalGroup.addActor(musiclabel);
        verticalGroup.addActor(menuThemelabel);
        verticalGroup.addActor(playThemelabel);
        verticalGroup.addActor(contaniner2);
        scrollPane= new ScrollPane(verticalGroup);
        // Agregar un listener para detectar cuando el usuario interactúa con el ScrollPane

        scrollPane.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isUserScrolling = true; // El usuario ha comenzado a desplazar manualmente
                Gdx.app.log("Scroll","Haciendo scroll");
                return true;
            }


        });
        table.add(scrollPane);
//        // Programar el temporizador para hacer scroll automáticamente con interpolación suave
//        Timer.schedule(new Timer.Task() {
//            float timeElapsed = 0;
//            float duration = 2; // Duración del desplazamiento en segundos
//
//            @Override
//            public void run() {
//                timeElapsed += Gdx.graphics.getDeltaTime();
//                float alpha = timeElapsed / duration;
//                float newY = Interpolation.sine.apply(0, scrollPane.getMaxY(), alpha);
//                scrollPane.setScrollY(newY);
//
//                if (timeElapsed >= duration) {
//                    timeElapsed = 0;
//                }
//            }
//        }, 1, 0.01f); // Hace scroll cada 2 segundos, ajusta esto según tus necesidades
        // Programar el temporizador para hacer scroll automáticamente con interpolación suave
        Timer.schedule(new Timer.Task() {
            float timeElapsed = 0;
            float duration = 0.35f; // Duración del desplazamiento en segundos
            float startY = 0;
            float targetY = 2; // Ajusta este valor según tus necesidades de desplazamiento

            @Override
            public void run() {
                if(!isUserScrolling){

                timeElapsed += Gdx.graphics.getDeltaTime();
                float alpha = timeElapsed / duration;
                float newY = Interpolation.smooth.apply(startY, targetY, alpha);
                scrollPane.setScrollY(newY);

                if (timeElapsed >= duration) {
                    timeElapsed = 0;
                    startY = scrollPane.getScrollY();
                    targetY = startY + 2; // Cambia el valor de desplazamiento según tus necesidades
                    if (startY >= scrollPane.getMaxY()) {
                        scrollPane.setScrollY(0); // Reiniciar al principio si alcanza el final
                        startY = 0;
                        targetY = 2; // Reiniciar el valor de destino
                    }
                }
                }
            }
        }, 0, 0.005f); // Hace scroll cada 0.1 segundos, ajusta esto según tus necesidades
        table.row();



        buttonBack = new TextButton(MyGdxGame.myBundle.get("back"), skin);

        //buttonPlay.setPosition(label.getOriginX(), label.getOriginY() - 120);
        textButtonStyle= buttonBack.getStyle();




        textButtonStyle.font=MyGdxGame.fontUi;
        buttonBack.setStyle(textButtonStyle);
        buttonBack.setSkin(skin);
        buttonBack.setWidth(200);
        buttonBack.setHeight(40);
        buttonBack.getLabel().setFontScale(0.7f);
        buttonBack.addListener(new InputListener() {
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
                game.setScreen(mainScreen);
                Gdx.app.log("Click", "Cambiar pantalla");
            }
        });
        table.add(buttonBack).expandX().pad(10).size(9000f/MyGdxGame.PPM,2000f/MyGdxGame.PPM);
        table.row();
        Gdx.input.setInputProcessor(stage);
        //table.debugAll();
    }
    @Override
    public void show() {
        backgroundSprite =new Sprite(manager.get("images/background.png", Texture.class));
        backgroundSprite.setSize(MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT);
        loadScreen();
    }

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
