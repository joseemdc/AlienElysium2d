package com.josema.alienelysium2d.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.josema.alienelysium2d.MyGdxGame;
import com.josema.alienelysium2d.screens.PlayScreen;

/**
 * Clase que representa un Alien
 */
public class Alien extends Enemy {
    /**
     * Timer que indica cuanto tiempo ha pasadado desde el último frame
     */
    private float stateTime;
    /**
     * Tiempo que el alien lleva caminando en una dirección
     */
    private float walkTime;
    /**
     * Animación de caminar
     */
    private Animation walkAnimation;
    /**
     * Vida del Alien
     */
    private float health = 1f;

    /**
     * Estado del Alien
     */
    public enum State {WALKING, ATTACKING, STANDING, RUNNING}

    /**
     * Estado actual
     */
    public State currentState;
    /**
     * Estado en el anterior render
     */
    public State previousState;
    /**
     *Indica si el alien está caminando hacia la derecha
     */
    private boolean runningRight;
    /**
     * Indica si la bala  está lista para borrarse
     */
    public boolean setToDestroy = false;
    /**
     * Indica si la bala está destruida
     */
    public boolean destroyed=false;
    /**
     * Manager del juego
     */
    private AssetManager manager;
    /**
     * Array donde se guardan todos los TextureRegion que forman parte de la animacion de caminar
     */
    private Array<TextureRegion> frames;

    /**
     *Crea un nuevo Alien en la posición indicada
     * @param screen Pantalla de juego
     * @param x Coordenada x
     * @param y Coordenada y
     * @param manager AssetManager del juego
     */
    public Alien(PlayScreen screen, float x, float y, AssetManager manager) {
        super(screen, x, y);
        this.manager=manager;
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"), 1, 1, 131, 145));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"), 183, 1, 132, 145));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"), 365, 1, 132, 144));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"), 547, 1, 133, 141));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"), 729, 1, 132, 142));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"), 911, 1, 132, 144));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"), 1093, 1, 132, 145));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"), 1275, 1, 133, 146));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"), 1457, 1, 133, 141));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"), 1639, 1, 132, 144));
        walkAnimation = new Animation(0.15f, frames);
        stateTime = 0;
        walkTime = 0;
        setBounds(getX(), getY(), 40 / MyGdxGame.PPM, 40 / MyGdxGame.PPM);
    }

    /**
     * Actualiza el sprite para que su posicion coincida siempre con la del cuerpor de colisiones Box2d siempre y cuando no esté destruido el alien
      * @param dt DeltaTime
     */
    public void update(float dt) {
        stateTime += dt;
        walkTime += dt;
        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        } else if (!destroyed) {

            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            // setRegion( (TextureRegion)walkAnimation.getKeyFrame(stateTime,true));
            b2body.setLinearVelocity(velocity);
            TextureRegion actualFrame = getFrame(dt);
            setRegion(getFrame(dt));
            if (walkTime > 2f) {
                reverseVelocity(true, false);
                walkTime = 0;
            }
        }
    }

    /**
     * Devuelve el frame de la animación específico para un momento dado
     * @param dt DeltaTime
     * @return TextureRegion correspondiente a ese tiempo
     */
    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region = new TextureRegion();

        switch (currentState) {
            case WALKING:
                region = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);
                break;
            case RUNNING:
                region = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);

                break;

            case STANDING:
            default:

                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTime = currentState == previousState ? stateTime + dt : 0;
        previousState = currentState;

        return region;

    }

    /**
     * Devuelve el estado en el que está el Alien
     * @return State estado actual
     */
    public State getState() {
        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.ATTACKING)) {
            return Alien.State.RUNNING;
        }  else if (b2body.getLinearVelocity().x != 0) {
            return Alien.State.WALKING;
        } else {
            return Alien.State.STANDING;
        }
    }

    /**
     * Define el alien creando su cuerpo de colisiones Box2d y posicionándolo segun las coordenadas dadas
     */
    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX() / MyGdxGame.PPM, getY() / MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        com.badlogic.gdx.physics.box2d.PolygonShape shape = new PolygonShape();
        shape.setAsBox(8 / MyGdxGame.PPM, 20 / MyGdxGame.PPM);
        //CircleShape shape = new CircleShape();

        //shape.setRadius(6 / MyGdxGame.PPM);

        fdef.filter.categoryBits = MyGdxGame.ENEMY_BIT;
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.ENEMY_BIT | MyGdxGame.PLAYER_BIT | MyGdxGame.ITEM_BIT | MyGdxGame.BULLET_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
       // b2body.createFixture(fdef);
    }

    /**
     * Resta vida al alien cuando le impacta una bala y reproduce un sonido, si el alien ya no tiene vida se marca con {@link #setToDestroy}
     */
    @Override
    public void onBulletHit() {
        health -= 0.1f;

        if (health < 0.1f) {
            if (MyGdxGame.prefs.hasSoundEffects()) {

                manager.get("audio/neo.mp3", Sound.class).play();
            }
            setToDestroy = true;
        }
    }

    /**
     * Si el alien no está mdestruido dibuja el sprite
     * @param batch Batch del Sprite
     */

    public void draw(Batch batch) {
        if(!destroyed){

            super.draw(batch);
        }
    }
}
