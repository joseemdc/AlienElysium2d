package com.josema.alienelysium2d.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.josema.alienelysium2d.MyGdxGame;
import com.josema.alienelysium2d.scenes.Hud;
import com.josema.alienelysium2d.screens.PlayScreen;
import com.josema.alienelysium2d.sprites.items.Bullet;

public class Player extends Sprite {
    /**
     * Estado del jugador
     *
     * {@link #FAllING},{@link #JUMPING},{@link #STANDING},{@link #RUNNING}
     */
    public enum State {
        /**
     * Callendo
     */
        FAllING,
        /**
         * Saltando
         */
        JUMPING,
        /**
         * Quieto
         */
        STANDING,
        /**
         * Andando
         */
        RUNNING};
    /**
     * Estado actual del jugador
     */
    public State currentState;
    /**
     * Estado del jugador en el frame anterior
     */
    public State previousState;
    /**
     * Este mundo de físicas 2d
     */
    public World world;
    /**
     * Cuerpo del personaje en el mundo de físicas
     */
    public Body b2body;
    /**
     * Textura del personaje cuando está quieto
     */
    private TextureRegion playerStand;
    /**
     * Animación de caminar
     */
    private Animation playerRun;
    /**
     * Animación de saltar
     */
    private Animation playerJump;
    /**
     * Timer que indica cuanto tiempo ha pasadado desde el último frame
     */

    private float stateTimer;
    /**
     *Indica si el personaje está caminando hacia la derecha
     */
    private boolean runningRight;
    /**
     * AssetManager del juego
     */
    private AssetManager manager;
    /**
     * Textura del anterior frame
     */
    private TextureRegion previousFrame;
    /**
     * Indica si el personaje está disparando
     */
    private boolean shooting = false;
    /**
     * SpriteBatch del juego
     */
    private SpriteBatch sb;
    /**
     * Colección de balas presentes en la escena
     */
    private Array<Bullet>bullets= new Array<Bullet>();
    /**
     * Pantalla de juego
     */
    private PlayScreen screen;
    /**
     * Vida del personaje
     */
    private float health = 1f;


    /**
     *Crea un nuevo jugador con sus animaciones para cada estado
     * @param screen Pantalla de juego
     * @param manager AssetManager del juego
     * @param sb SpriteBatch del juego
     *
     */
    public Player(PlayScreen screen, AssetManager manager, SpriteBatch sb) {
        super(screen.getAtlas().findRegion("scifiMan"));
        this.world = screen.getWorld();
        this.manager = manager;
        this.sb = sb;
        this.screen=screen;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;

        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
//        for (int i = 1; i < 4; i++) {
//            frames.add(new TextureRegion(getTexture(), i * 18, 34, 18, 32));
//        }
        frames.add(new TextureRegion(getTexture(), 2, 74, 21, 31));
        frames.add(new TextureRegion(getTexture(), 26, 74, 21, 31));
        frames.add(new TextureRegion(getTexture(), 50, 74, 21, 31));
        frames.add(new TextureRegion(getTexture(), 74, 74, 21, 31));

        playerRun = new Animation(0.15f, frames);
        frames.clear();

        frames.add(new TextureRegion(getTexture(), 3, 234, 15, 23));
        frames.add(new TextureRegion(getTexture(), 68, 306, 19, 31));
        playerJump = new Animation(0.1f, frames);
        frames.clear();



        definePlayer();
        playerStand = new TextureRegion(getTexture(), 2, 74, 21, 31);
        setBounds(0, 0, 18 / MyGdxGame.PPM, 32 / MyGdxGame.PPM);
        setRegion(playerStand);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        TextureRegion actualFrame = getFrame(dt);
        if (previousFrame != null && previousFrame != actualFrame) {
            for (Object frame : playerRun.getKeyFrames()
            ) {
                if (actualFrame.equals((TextureRegion) frame)) {
                    if (MyGdxGame.prefs.hasSoundEffects()) {

                        manager.get("audio/single-footstep.mp3", Sound.class).play();
                    }
                }
            }

        }


            setRegion(actualFrame);



        previousFrame = actualFrame;

        if(bullets.size>0){

            for (Bullet bullet:bullets
            ) {
                bullet.update(dt);
                
            }
        }
    }

    /**
     * Devuelve el frame de la animación correspondiente que se debe mostrar en un momento exacto (DeltaTime), si está caminando establece la dirección en la que camina e invierte el correspondiene frame
     * @param dt DeltaTime
     * @return TextureRegion correspodiente al frame actual
     */
    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;

        switch (currentState) {
            case JUMPING:
                region = (TextureRegion) playerJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) playerRun.getKeyFrame(stateTimer, true);

                break;
            case FAllING:
            case STANDING:
            default:
                region = playerStand;
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;

        return region;

    }

    /**Devuelve el estado en el que se encuentra el personaje
     *
     * @return State - estado del jugador
     */
    public State getState() {
        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
            return State.JUMPING;
        } else if (b2body.getLinearVelocity().y < 0) {
            return State.FAllING;
        } else if (b2body.getLinearVelocity().x != 0) {
            return State.RUNNING;
        } else {
            return State.STANDING;
        }
    }

    /**
     * Define la forma o cuerpo de colisión del jugador, en este caso rectangular y establece su categoryBits a su correspondiente, también se establece que pueda colisionar con el suelo y con otros cuerpos de tipo Enemy
     */
    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MyGdxGame.PPM, 80 / MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        com.badlogic.gdx.physics.box2d.PolygonShape shape = new PolygonShape();
        shape.setAsBox(8 / MyGdxGame.PPM, 15 / MyGdxGame.PPM);
        //CircleShape shape = new CircleShape();

        //shape.setRadius(6 / MyGdxGame.PPM);

        fdef.filter.categoryBits = MyGdxGame.PLAYER_BIT;
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.ENEMY_BIT;
        fdef.shape = shape;

        b2body.createFixture(fdef);

    }


    /**
     *Crea una nueva bala en la dirección a la que mira el jugador, se asegura de que sea cada 0,2 segundos
     */
    public void shoot() {
        if (!shooting) {
            shooting = true;
            screen.hud.healthBar.setAnimateDuration(0.25f);
            bullets.add(new Bullet(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false));
            if(MyGdxGame.prefs.hasHaptic()){
                Gdx.input.vibrate(Input.VibrationType.HEAVY);
            }
            Timer.Task shootingTask= Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    shooting = false; // Cambia shooting a false cuando se complete la tarea
                }
            }, 0.20f); // Reemplaza tiempoDeseado con el tiempo en segundos
        }
    }

    /**
     * Resta 0.1 de la vida del jugador y reduce la barra de vida
     */
    public void receiveDamage(){
        health-=0.1f;
        screen.hud.healthBar.setAnimateDuration(0.0f);
        screen.hud.healthBar.setAnimateDuration(0.0f);
        screen.hud.healthBar.setValue(0.1f);
    }
    public void stopShoot(){
        if(shooting){
            shooting=false;
        }
    }

    /**Devuelve si el jugador está disparando o no
     *
     * @return True si el jugador está disparando o False si no lo está
     */
    public boolean isShooting() {
        return shooting;
    }

    /**
     * Dibuja el personaje y las balas si las hubiera en ese instante
     * @param batch
     */
    public void draw(Batch batch){
        super.draw(batch);
        if(bullets.size>0){

            for (Bullet bullet:bullets
            ) {
                bullet.draw(batch);
            }
        }
//        if(player.isShooting()) {
//            Bullet bullet = new Bullet(this, player.b2body.getPosition().x, player.b2body.getPosition().y,player.b2body.getPosition().x,player.b2body.getPosition().y);
//            bullets.add(bullet);
//        }
    }
}
