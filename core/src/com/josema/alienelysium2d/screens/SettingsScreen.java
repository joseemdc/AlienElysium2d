package com.josema.alienelysium2d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.josema.alienelysium2d.MyGdxGame;
import com.kotcrab.vis.ui.building.utilities.Alignment;

import java.util.Locale;
import java.util.MissingResourceException;

public class SettingsScreen implements Screen {
    private MyGdxGame game;
    private AssetManager manager;
    private Viewport viewport;
    private Stage stage;
    private SpriteBatch sb;
    private MainScreen mainScreen;
    private PlayScreen playScreen;
    private Sprite backgroundSprite;
    boolean ignoreChangeEvent = false;
    //Elementos UI
    Label label;
     CheckBox hapticCheck;
    CheckBox musicaCheck;
    CheckBox soundEffectsCheck;
    Label labellang;
    TextButton buttonBack;

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
        viewport = new FillViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        CheckBox.CheckBoxStyle checkBoxStyle= new CheckBox.CheckBoxStyle();
        checkBoxStyle.font=MyGdxGame.fontUi;
        checkBoxStyle.fontColor=Color.WHITE;
        TextButton.TextButtonStyle textButtonStyle= new TextButton.TextButtonStyle();
        textButtonStyle.font=MyGdxGame.fontUi;
        textButtonStyle.fontColor=Color.WHITE;
        SelectBox.SelectBoxStyle selectBoxStyle= new SelectBox.SelectBoxStyle();
        selectBoxStyle.font=MyGdxGame.fontUi;
        selectBoxStyle.fontColor=Color.WHITE;
        List.ListStyle listStyle= new List.ListStyle();
        listStyle.font=MyGdxGame.fontUi;
        listStyle.fontColorSelected=Color.WHITE;
        listStyle.fontColorUnselected=Color.WHITE;

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // Crea una tabla, donde añadiremos los elementos de menú
        Table table = new Table();
        table.setSize(MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT);
        table.pad(2000/MyGdxGame.PPM);

        // La tabla ocupa toda la pantalla
        table.setFillParent(true);
        stage.addActor(table);
        // Etiqueta de texto
        label = new Label(MyGdxGame.myBundle.get("settings"), new Label.LabelStyle(MyGdxGame.fontLogo, Color.WHITE));
        label.setAlignment(Alignment.CENTER.ordinal());
        //label.setAlignment(GroupLayout.Alignment.CENTER.ordinal());
        table.add(label).expandX().padBottom(20).size(9000f/MyGdxGame.PPM,2000f/MyGdxGame.PPM);
        table.row();


        hapticCheck = new CheckBox(MyGdxGame.myBundle.get("hapticFeedback"), skin);
//  Obtener el estilo del CheckBox
         checkBoxStyle = hapticCheck.getStyle();

//  Configurar la fuente personalizada en el estilo
        checkBoxStyle.font = MyGdxGame.fontUi;




// Aplicar el estilo actualizado al CheckBox
        hapticCheck.setStyle(checkBoxStyle);

//  Asignar la skin al CheckBox
        hapticCheck.setSkin(skin);


        //hapticCheck.setWidth(200);
       // hapticCheck.setHeight(80);
        hapticCheck.getLabelCell().padLeft(10);
        hapticCheck.getImageCell().size(1000f/MyGdxGame.PPM,1000f/MyGdxGame.PPM);
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
        table.add(hapticCheck).expandX().pad(5).size(9000f/MyGdxGame.PPM,1000f/MyGdxGame.PPM);
        table.row();

        musicaCheck = new CheckBox(MyGdxGame.myBundle.get("music"), skin);
        musicaCheck.setStyle(checkBoxStyle);
        musicaCheck.setSkin(skin);
        musicaCheck.setWidth(200);
        musicaCheck.setHeight(40);
        musicaCheck.getLabelCell().padLeft(10);
        musicaCheck.getImageCell().size(1000f/MyGdxGame.PPM,1000f/MyGdxGame.PPM);
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
        table.add(musicaCheck).expandX().pad(10).size(9000f/MyGdxGame.PPM,1000f/MyGdxGame.PPM);
        table.row();

        soundEffectsCheck = new CheckBox(MyGdxGame.myBundle.get("soundEffects"), skin);
        soundEffectsCheck.setStyle(checkBoxStyle);
        soundEffectsCheck.setSkin(skin);
        soundEffectsCheck.setWidth(200);
        soundEffectsCheck.setHeight(40);
        soundEffectsCheck.getLabelCell().padLeft(10);
        soundEffectsCheck.getImageCell().size(1000f/MyGdxGame.PPM,1000f/MyGdxGame.PPM);
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
        table.add(soundEffectsCheck).expandX().pad(10).size(9000f/MyGdxGame.PPM,1000f/MyGdxGame.PPM);
        table.row();
        HorizontalGroup languageGroup= new HorizontalGroup();

        labellang = new Label(MyGdxGame.myBundle.get("language"), new Label.LabelStyle(MyGdxGame.fontUi, Color.WHITE));
        //label.setAlignment(GroupLayout.Alignment.CENTER.ordinal());
        labellang.setWidth(1);

        languageGroup.addActor(labellang);
        languageGroup.space(20);
        //table.add(labellang);
        final SelectBox langSelect = new SelectBox<String>(skin);
        selectBoxStyle= langSelect.getStyle();
        selectBoxStyle.font=MyGdxGame.fontUi;
        listStyle=langSelect.getList().getStyle();
        listStyle.font=MyGdxGame.fontUi;

        langSelect.getList().setStyle(listStyle);
        langSelect.setStyle(selectBoxStyle);
        langSelect.setSize(9000f/MyGdxGame.PPM,1000f/MyGdxGame.PPM);

        String[] languages = new String[]{"Español", "English"};
        langSelect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!ignoreChangeEvent && langSelect.getItems().size>0){

                Gdx.app.log("Changed",langSelect.getSelected().toString());
                switch (langSelect.getSelected().toString()){
                    case "Español":
                        MyGdxGame.prefs.setLang("es");
                        Gdx.app.log("Idioma","Español");
                        break;
                    case "English":
                        MyGdxGame.prefs.setLang("en");
                        Gdx.app.log("Idioma","Ingles");

                }
                Gdx.app.log("Preference",MyGdxGame.prefs.getLang());
                    FileHandle baseFileHandle = Gdx.files.internal("locale/MyBundle");
                    Locale locale = new Locale(MyGdxGame.prefs.getLang());

I18NBundle bundle= new I18NBundle();

bundle=  I18NBundle.createBundle(baseFileHandle, locale,"UTF-8");
                    MyGdxGame.myBundle =bundle;


                }

            }
        });
        langSelect.addListener(new InputListener(){
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
        ignoreChangeEvent=true;
        langSelect.setItems(languages);
        ignoreChangeEvent=false;
        int selectedIndex=-1;
        switch (MyGdxGame.prefs.getLang()){
            case "es":
                selectedIndex=0;
                break;
            case "en":
                selectedIndex=1;
                break;
            default:
                selectedIndex=1;
                break;
        }

        langSelect.setSelectedIndex(selectedIndex);
    languageGroup.addActor(langSelect);
        table.add(languageGroup).pad(10).size(9000f/MyGdxGame.PPM,1000f/MyGdxGame.PPM);
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




        //table.debugAll();
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK, true);

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
        updateUI();
        stage.act(delta);

        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            game.setScreen(mainScreen);
        }
    }
    public void updateUI(){
        label.setText(MyGdxGame.myBundle.get("settings"));
        hapticCheck.setText(MyGdxGame.myBundle.get("hapticFeedback"));
        musicaCheck.setText(MyGdxGame.myBundle.get("music"));
        soundEffectsCheck.setText(MyGdxGame.myBundle.get("soundEffects"));
        buttonBack.setText(MyGdxGame.myBundle.get("back"));
        labellang.setText(MyGdxGame.myBundle.get("language"));
    }

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

    }
}
