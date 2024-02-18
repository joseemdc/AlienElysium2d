package com.josema.alienelysium2d.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.josema.alienelysium2d.MyGdxGame;
import com.josema.alienelysium2d.scenes.Hud;
import com.josema.alienelysium2d.sprites.Player;
import com.josema.alienelysium2d.tools.B2WorldCreator;
import com.josema.alienelysium2d.tools.Controller;
import com.josema.alienelysium2d.tools.WorldContactListener;

public class PlayScreen implements Screen {
    //Referencia del juego, usado para establecer pantallas
    private MyGdxGame game;

    private TextureAtlas atlas;
    //variables báscias del PlayScreen
    Texture texture;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;
    //Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;
    private AssetManager manager;
    private Music music;
    private Controller controller;
    public PlayScreen(MyGdxGame game, AssetManager manager) {

        atlas= new TextureAtlas("player_and_enemy.atlas");
        this.game = game;

        gameCam= new OrthographicCamera();
        //crear un FitViewport para mantener la relación de aspecto a pesar del tamaño de la pantalla
        gamePort = new FillViewport(MyGdxGame.V_WIDTH/MyGdxGame.PPM,MyGdxGame.V_HEIGHT/MyGdxGame.PPM,gameCam);

        //crear el HUD para la informacion en pantalla
        hud= new Hud(game.batch);
        //crea el controller
        controller= new Controller(game.batch);
        //cargar el mapa y establecer el renderizador del mapa
        mapLoader = new TmxMapLoader();
        map=mapLoader.load("level1.tmx");
        renderer=new OrthogonalTiledMapRenderer(map,1/MyGdxGame.PPM);
        //poner centrada la cámara al inicio del mapa
        gameCam.position.set((float) gamePort.getWorldWidth() /2, (float) gamePort.getWorldHeight() /2,0);
    //crea un mundo Box2d
        world = new World(new Vector2(0,-10),true);
        //mostrar debug lines del mapa
        b2dr= new Box2DDebugRenderer();

        new B2WorldCreator(world,map);
        //crea un personaje en nuestro juego
this.manager=manager;
         player = new Player(world,this,manager);

            world.setContactListener(new WorldContactListener(manager));
            music =manager.get("audio/spaceship-ambience-with-effects-21420.mp3",Music.class);
            music.setLooping(true);
            music.play();
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }
    public void handleInput(float dt){
//        float velocityX = 0.0003f; // Velocidad horizontal deseada en unidades por segundo
//        float velocityY=0.03f;
        float velocityX = 4.5f; // Velocidad horizontal deseada en unidades por segundo
        float velocityY=300f;
        float impulseY = velocityY*dt;
        float impulseX = velocityX * dt; // Impulso horizontal requerido por segundo
        if((Gdx.input.isKeyJustPressed(Input.Keys.UP)|| Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.W)|| controller.isUpPressed())&& player.currentState!= Player.State.JUMPING){
            player.b2body.applyLinearImpulse(new Vector2(0,impulseY),player.b2body.getWorldCenter(),true);
        }
        if((Gdx.input.isKeyPressed(Input.Keys.RIGHT)||Gdx.input.isKeyPressed(Input.Keys.D)|| controller.isRightPressed()) && player.b2body.getLinearVelocity().x<=2){
            player.b2body.applyLinearImpulse(new Vector2(impulseX,0),player.b2body.getWorldCenter(),true);
        }
        if((Gdx.input.isKeyPressed(Input.Keys.LEFT)||Gdx.input.isKeyPressed(Input.Keys.A)|| controller.isLeftPressed()) && player.b2body.getLinearVelocity().x>=-2){
            player.b2body.applyLinearImpulse(new Vector2(-impulseX,0),player.b2body.getWorldCenter(),true);
        }
    }
    public void update(float dt){
        //gestionar input
        handleInput(dt);

        world.step((1/60f),6,2);
        player.update(dt);

        // ancla  la gamecam a la posicion x del jugador
        gameCam.position.x=player.b2body.getPosition().x;
        //actualizar cámara
        gameCam.update();
        //Ordenar al renderizador que renderice solo lo que puede ver la cámara del mundo del juego
        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {
        //separa la logica de actualizacion de la de renderizacion
        update(delta);
        //limpia la pantalla
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //renderiza el mapa
        renderer.render();
        //rederiza las Box2DDebugLines
        b2dr.render(world,gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        //establece el batch para dibujar lo que la camara del HUD ve
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        controller.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        controller.resize(width, height);
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
    }
}
