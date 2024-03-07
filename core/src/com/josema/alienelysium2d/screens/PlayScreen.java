package com.josema.alienelysium2d.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.josema.alienelysium2d.MyGdxGame;
import com.josema.alienelysium2d.scenes.Hud;
import com.josema.alienelysium2d.sprites.Alien;
import com.josema.alienelysium2d.sprites.Player;
import com.josema.alienelysium2d.sprites.items.Bullet;
import com.josema.alienelysium2d.tools.B2WorldCreator;
import com.josema.alienelysium2d.tools.Controller;
import com.josema.alienelysium2d.tools.WorldContactListener;

/**
 * Pantalla de juego
 */
public class PlayScreen implements Screen {
    /**
     *Referencia del juego, usado para establecer pantallas
     */
    private MyGdxGame game;
    /**
     * Sprite que equivale a la imagen del fondo
     */
    private Sprite backgroundSprite;
    /**
     * Atlas con las texturas del personaje
     */
    private TextureAtlas atlas;
    /**
     * Atlas con las texturas del enemigo
     */
    private TextureAtlas atlas2;
    //variables báscias del PlayScreen
    Texture texture;
    /**
     * Cámara con proyección ortográfica
     */
    private OrthographicCamera gameCam;
    /**
     * Viewport de la pantalla de juego
     */
    private Viewport gamePort;
    /**
     * Hud de la partida
     */
    public Hud hud;
    //Tiled map variables
    /**
     * Cargador de mapa
     */
    private TmxMapLoader mapLoader;
    /**
     * Mapa de baldosas
     */
    private TiledMap map;
    /**
     * Rederizador de mapas de baldosas ortogonal
     */
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    /**
     * Mundo de físicas 2d
     */
    private World world;
    /**
     * Renderizador debug de box2d
     */
    private Box2DDebugRenderer b2dr;
    /**
     * Creador de mundo B2D
     */
    private B2WorldCreator creator;
    /**
     * Jugador
     */
    private Player player;
    /**
     * AssetManager del juego
     */
    private AssetManager manager;
    /**
     * Se encarga de reproducir música
     */
    private Music music;
    /**
     * Controles del juego
     */
    private Controller controller;
    /**
     * Indica si el jugador ha llegado al final del mapa
     */
    public boolean metaReached=false;


//    public enum State {
//        PAUSE,
//        RUN,
//        RESUME,
//        STOPPED
//    }

    /**
     * Crea una nueva pantalla de juego e inicializa sus componentes
     * @param game Clase principal del juego
     * @param manager AssetManager del juego
     */
    public PlayScreen(MyGdxGame game, AssetManager manager) {

        atlas = new TextureAtlas("player_and_enemy.atlas");
        atlas2 = new TextureAtlas("alien.atlas");
        this.game = game;

        gameCam = new OrthographicCamera();

        //crear un FitViewport para mantener la relación de aspecto a pesar del tamaño de la pantalla
        gamePort = new FitViewport((float) MyGdxGame.V_WIDTH / 100, (float) MyGdxGame.V_HEIGHT /100, gameCam);


        //crear el HUD para la informacion en pantalla
        hud = new Hud(game.batch);
        //crea el controller
        controller = new Controller(game.batch);
        //cargar el mapa y establecer el renderizador del mapa
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");

        renderer = new OrthogonalTiledMapRenderer(map, 1 / (MyGdxGame.PPM));

        //poner centrada la cámara al inicio del mapa
        gameCam.position.set((float) gamePort.getWorldWidth() / 2, (float) gamePort.getWorldHeight() / 2, 0);
        //crea un mundo Box2d
        world = new World(new Vector2(0, -10), true);

        //mostrar debug lines del mapa
        b2dr = new Box2DDebugRenderer();

        creator= new B2WorldCreator(this,manager);
        //crea un personaje en nuestro juego
        this.manager = manager;
        player = new Player(this, manager, game.batch);
        //alien = new Alien(this, .32f, .32f, manager);

        world.setContactListener(new WorldContactListener(manager,this));
        music = manager.get("audio/spaceship-ambience-with-effects-21420.mp3", Music.class);
        music.setLooping(true);
        if (MyGdxGame.prefs.hasMusic()) {

            music.play();
        }
    }

    /**
     * Devuelve el Atlas del jugador
     * @return TexyureAtlas con las texturas del jugador
     */

    public TextureAtlas getAtlas() {
        return atlas;
    }

    /**
     * Devuelve el Atlas del alien
     * @return TextureAtlas con las texturas del alien
     */
    public TextureAtlas getAtlas2() {
        return atlas2;
    }

    /**
     * Dibuja el fondo de la pantalla
     */
    @Override
    public void show() {
        backgroundSprite = new Sprite(manager.get("images/background.png", Texture.class));
        backgroundSprite.setSize(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT);
    }

    /**
     * Gestiona la entrada del usuario y aplica las fuerzas correspondientes al jugador
     * @param dt DeltaTime tiempo desde el ultimo render
     */
    public void handleInput(float dt) {
//        float velocityX = 0.0003f; // Velocidad horizontal deseada en unidades por segundo
//        float velocityY=0.03f;
        if (MyGdxGame.prefs.usesAccelerometer()) {
            int orientation = Gdx.input.getRotation();

            float accelerometerY = Gdx.input.getAccelerometerY();

            // Constantes de sensibilidad para ajustar la respuesta del movimiento
            float sensitivityX = 0.1f; // Sensibilidad en el eje X


            // Calcula el impulso basado en la inclinación del dispositivo
            float impulseX = -accelerometerY * sensitivityX;
    if(orientation==90){
        impulseX=-impulseX;
    }
            // Aplica el impulso al cuerpo del jugador
            if (player.currentState == Player.State.JUMPING) {

                player.b2body.applyLinearImpulse(new Vector2(impulseX, 0), player.b2body.getWorldCenter(), true);
            }
        }

        float velocityX = 4.5f; // Velocidad horizontal deseada en unidades por segundo
        float velocityY = 300f;
        float impulseY = velocityY * dt;
        float impulseX = velocityX * dt; // Impulso horizontal requerido por segundo
        if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.W) || controller.isUpPressed()) && player.currentState != Player.State.JUMPING) {
            player.b2body.applyLinearImpulse(new Vector2(0, 4), player.b2body.getWorldCenter(), true);
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D) || controller.isRightPressed()) && player.b2body.getLinearVelocity().x <= 2) {
            player.b2body.applyLinearImpulse(new Vector2(1, 0), player.b2body.getWorldCenter(), true);
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A) || controller.isLeftPressed()) && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(new Vector2(-1, 0), player.b2body.getWorldCenter(), true);
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.F) || controller.isFirePressed()) && !player.isShooting()) {
            player.shoot();
            MyGdxGame.prefs.addRecord(hud.worldTimer);

        }
        if (player.b2body.getPosition().y < 0.0f) {
            player.playerDead = true;
        }
        Gdx.app.log("POSITION", String.valueOf(player.b2body.getPosition().y));
    }

    /**
     * Actualiza los elementos del juego con el DeltaTime
     * @param dt DeltaTime tiempo desde el ultimo render
     */
    public void update(float dt) {
        //gestionar input
        handleInput(dt);

        world.step((1 / 60f), 6, 2);
        player.update(dt);
        for(Alien alien: creator.getAliens()){
        alien.update(dt);

        }
        hud.update(dt);


        // ancla  la gamecam a la posicion x del jugador
        gameCam.position.x = player.b2body.getPosition().x;
        //actualizar cámara
        gameCam.update();
        //Ordenar al renderizador que renderice solo lo que puede ver la cámara del mundo del juego
        renderer.setView(gameCam);
    }

    /**
     * Dibuja cada vez que se ejecuta todos los elementos que hay en el juego y comprueba si el jugador ha llegado al final
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {


        //separa la logica de actualizacion de la de renderizacion
        update(delta);

        //limpia la pantalla
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //  game.batch.setProjectionMatrix(backCam.combined);
        game.batch.begin();
        backgroundSprite.draw(game.batch);
        game.batch.end();

        //renderiza el mapa
        renderer.render();
        //rederiza las Box2DDebugLines
        //b2dr.render(world, gameCam.combined);
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        //
        player.draw(game.batch);
        for(Alien alien: creator.getAliens()){

        alien.draw(game.batch);
        }

        game.batch.end();

        //establece el batch para dibujar lo que la camara del HUD ve
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (Gdx.app.getType() == (Application.ApplicationType.Android)) {
            controller.draw();
        }
        if (gameOver()) {
            game.setScreen(new GameOverScreen(game, manager, game.batch));
            //game.setScreen(new SettingsScreen(game,manager,this,game.batch));
            Gdx.app.log("MUERTO", "Game Over");
            dispose();
        }
        if(metaReached){
            MyGdxGame.prefs.addRecord(hud.worldTimer);
            game.setScreen(new WinScreen(game,manager,game.batch));
            dispose();
        }
    }

    /**
     * Actualiza el viewport, el controller y el hud con las nuevas dimensiones
     * @param width Ancho
     * @param height Alto
     */
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        controller.resize(width, height);
        hud.resize(width,height);
    }

    /**
     * Devuelve el mapa del nivel
     * @return TiledMap mapa actual del juego
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * Devuelve el World del juego
     * @return World del juego
     */
    public World getWorld() {
        return world;
    }

    /**
     * Comprueba si el jugador ha muerto
     * @return True si el jugador ha mueto o False si no
     */
    public boolean gameOver() {
        if (player.isDead()) {
            return true;
        } else {
            return false;
        }
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        music.dispose();
    }
}
