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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.josema.alienelysium2d.MyGdxGame;

public class SettingsScreen implements Screen {
    private MyGdxGame game;
    private AssetManager manager;
    private Viewport viewport;
    private Stage stage;
    private SpriteBatch sb;
    private MainScreen mainScreen;
    private PlayScreen playScreen;
    private Sprite backgroundSprite;

    public SettingsScreen(MyGdxGame game, AssetManager manager, MainScreen mainScreen,SpriteBatch sb) {
        this.game = game;
        this.manager = manager;
        this.mainScreen = mainScreen;
        this.sb=sb;
    }

    public SettingsScreen(MyGdxGame game, AssetManager manager, PlayScreen playScreen,SpriteBatch sb) {
        this.game = game;
        this.manager = manager;
        this.playScreen = playScreen;
        this.sb=sb;
    }

    public void loadScreen() {
        viewport = new ExtendViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        CheckBox.CheckBoxStyle checkBoxStyle= new CheckBox.CheckBoxStyle();
        checkBoxStyle.font=MyGdxGame.fontUi;
        checkBoxStyle.fontColor=Color.WHITE;
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Crea una tabla, donde añadiremos los elementos de menú
        Table table = new Table();
        table.pad(30);

        // La tabla ocupa toda la pantalla
        table.setFillParent(true);
        //table.setHeight(500);
        stage.addActor(table);
        // Etiqueta de texto
        Label label = new Label(MyGdxGame.myBundle.get("settings"), new Label.LabelStyle(MyGdxGame.fontLogo, Color.WHITE));
        //label.setAlignment(GroupLayout.Alignment.CENTER.ordinal());
        table.add(label).expandX().padBottom(20);
        table.row();


        final CheckBox hapticCheck = new CheckBox(MyGdxGame.myBundle.get("hapticFeedback"), skin);
//  Obtener el estilo del CheckBox
         checkBoxStyle = hapticCheck.getStyle();

//  Configurar la fuente personalizada en el estilo
        checkBoxStyle.font = MyGdxGame.fontUi;




// Aplicar el estilo actualizado al CheckBox
        hapticCheck.setStyle(checkBoxStyle);

//  Asignar la skin al CheckBox
        hapticCheck.setSkin(skin);


        hapticCheck.setWidth(200);
        hapticCheck.setHeight(80);
        hapticCheck.getLabelCell().padLeft(10);
        hapticCheck.getImageCell().size(35,35);
        hapticCheck.getImage().setScaling(Scaling.fill);
        hapticCheck.center();

        hapticCheck.setChecked(MyGdxGame.prefs.hasHaptic());
        hapticCheck.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MyGdxGame.prefs.setHaptic(hapticCheck.isChecked());

            }
        });
        hapticCheck.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(MyGdxGame.prefs.hasHaptic()){

                Gdx.input.vibrate(Input.VibrationType.HEAVY);
                }


                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(MyGdxGame.prefs.hasHaptic()){

                Gdx.input.vibrate(Input.VibrationType.LIGHT);
                }


            }
        });
        table.add(hapticCheck).expandX().pad(10).size(400,40);
        table.row();

        final CheckBox musicaCheck = new CheckBox(MyGdxGame.myBundle.get("music"), skin);

        musicaCheck.setSkin(skin);
        musicaCheck.setWidth(200);
        musicaCheck.setHeight(40);
        musicaCheck.getLabelCell().padLeft(10);
        musicaCheck.getImageCell().size(35,35);
        musicaCheck.getImage().setScaling(Scaling.fill);
        musicaCheck.center();

        musicaCheck.setChecked(MyGdxGame.prefs.hasMusic());

        musicaCheck.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MyGdxGame.prefs.setMusic(musicaCheck.isChecked());
            }
        });
        musicaCheck.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(MyGdxGame.prefs.hasHaptic()){

                Gdx.input.vibrate(Input.VibrationType.HEAVY);
                }


                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(MyGdxGame.prefs.hasHaptic()){

                Gdx.input.vibrate(Input.VibrationType.LIGHT);
                }


            }
        });
        table.add(musicaCheck).expandX().pad(10).size(400,40);
        table.row();

        final CheckBox soundEffectsCheck = new CheckBox(MyGdxGame.myBundle.get("soundEffects"), skin);
        soundEffectsCheck.setSkin(skin);
        soundEffectsCheck.setWidth(200);
        soundEffectsCheck.setHeight(40);
        soundEffectsCheck.getLabelCell().padLeft(10);
        soundEffectsCheck.getImageCell().size(35,35);
        soundEffectsCheck.getImage().setScaling(Scaling.fill);
        soundEffectsCheck.center();

        soundEffectsCheck.setChecked(MyGdxGame.prefs.hasSoundEffects());

        soundEffectsCheck.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MyGdxGame.prefs.setSoundEffects(soundEffectsCheck.isChecked());
            }
        });
        soundEffectsCheck.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(MyGdxGame.prefs.hasHaptic()){

                    Gdx.input.vibrate(Input.VibrationType.HEAVY);
                }


                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(MyGdxGame.prefs.hasHaptic()){

                    Gdx.input.vibrate(Input.VibrationType.LIGHT);
                }


            }
        });
        table.add(soundEffectsCheck).expandX().pad(10).size(400,40);
        table.row();
        HorizontalGroup languageGroup= new HorizontalGroup();

        Label labellang = new Label(MyGdxGame.myBundle.get("language"), new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));
        //label.setAlignment(GroupLayout.Alignment.CENTER.ordinal());
        labellang.setWidth(1);

        languageGroup.addActor(labellang);
        languageGroup.space(20);
        //table.add(labellang);
        SelectBox langSelect = new SelectBox<String>(new Skin(Gdx.files.internal("skin/uiskin.json")));

        String[] languages = new String[]{"Español", "English"};
        langSelect.setItems(languages);
    languageGroup.addActor(langSelect);
        table.add(languageGroup).pad(10);
        table.row();


        TextButton buttonBack = new TextButton(MyGdxGame.myBundle.get("back"), new Skin(Gdx.files.internal("skin/uiskin.json")));
        //buttonPlay.setPosition(label.getOriginX(), label.getOriginY() - 120);
        buttonBack.setWidth(200);
        buttonBack.setHeight(40);
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
        table.add(buttonBack).expandX().pad(10).size(200,60);
        table.row();




        table.debugAll();
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);

    }

    @Override
    public void show() {
        backgroundSprite =new Sprite(manager.get("images/background.png", Texture.class));
        loadScreen();
    }

    @Override
    public void render(float delta) {
        //limpia la pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
// Pinta el menú
        sb.begin();
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
