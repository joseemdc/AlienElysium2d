package com.josema.alienelysium2d;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.I18NBundle;
import com.josema.alienelysium2d.screens.MainScreen;
import com.josema.alienelysium2d.tools.Prefs;

import java.util.Locale;

/**
 * Clase principal del Juego
 */
public class MyGdxGame extends Game {
    /**
     * Ancho que se va a aplicar a los viewports
     */

    public static int V_WIDTH = 800;
    /**
     * Alto que se va a aplicar a los viewports
     */
    public static int V_HEIGHT = 400;
    /**
     * Pixeles por metro que se aplican para escalar todas las medidas
     */

    public static final float PPM = 50f;
    /**
     * Bits que identifican el suelo
     */
    public static final short GROUND_BIT = 1;
    /**
     * Bits que identifican el jugador
     */
    public static final short PLAYER_BIT = 2;
    /**
     * Bits que identifican a un enemigo
     */
    public static  final short ENEMY_BIT=4;
    /**
     * Bits que identifican a un objeto
     */
    public static  final short ITEM_BIT=8;
    /**
     * Bits que identifican a una bala
     */
    public static  final short BULLET_BIT=16;
    /**
     * Bits que identifican el final del juego
     */
    public static  final short META_BIT=32;
    /**
     * Generador de fuentes
     */
    public static FreeTypeFontGenerator generator;
    /**
     * Parámetros de fuente
     */
    public static FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    /**
     * Fuente de tamaño normal para la UI
     */
    public static BitmapFont fontUi;
    /**
     * Fuente de tamaño grande para los títulos
     */
    public static BitmapFont fontLogo;
    /**
     * SpriteBatch del juegp
     */
    public SpriteBatch batch;
    /**
     * AssetManager del juego
     */
    public AssetManager manager;
    /**
     * Maneja las preferencias del juego
     */
    public static Prefs prefs;
    /**
     * Bundle usado para la localizacion
     */
    public static I18NBundle myBundle= new I18NBundle();

    /**
     * Carga todos los assets con el manager,las preferencias, fuentes y cambia la pantalla a la principal del juego
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();
        manager.load("audio/spaceship-ambience-with-effects-21420.mp3", Music.class);
        manager.load("audio/single-footstep.mp3", Sound.class);
        manager.load("audio/lasergun.mp3", Sound.class);
        manager.load("audio/neo.mp3", Sound.class);
        manager.load("images/background.png", Texture.class);
        manager.load("images/ayuda.png", Texture.class);
        manager.load("locale/MyBundle", I18NBundle.class);
        //manager.load("locale/MyBundle", I18NBundle.class);
        //backgroundTexture = new Texture("images/background.png");
//	 V_WIDTH = Gdx.graphics.getWidth();
//		 V_HEIGHT=Gdx.graphics.getHeight();
        prefs = new Prefs();
        manager.finishLoading();
         generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/OxaniumRegular-JRRnn.ttf"));
         parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
         fontUi = generator.generateFont(parameter); // font size 12 pixels
        parameter.size =45;
        fontLogo=generator.generateFont(parameter);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!


        FileHandle baseFileHandle = Gdx.files.internal("locale/MyBundle");
        Locale locale = new Locale(prefs.getLang());



         myBundle = I18NBundle.createBundle(baseFileHandle, locale,"UTF-8");

      // myBundle= manager.get("locale/MyBundle", I18NBundle.class);
        setScreen(new MainScreen(this, manager, batch));
    }

    @Override
    public void render() {
        super.render();

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        manager.dispose();

    }
}
