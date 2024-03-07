package com.josema.alienelysium2d.sprites.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.josema.alienelysium2d.MyGdxGame;
import com.josema.alienelysium2d.screens.PlayScreen;

/**
 * Clase que representa una Bala en el juego
 */
public class Bullet extends Sprite {
    /**
     * Indica si se está disparando hacia la derecha
     */
    boolean fireRight;
    /**
     * Mundo de Box2d
     */
    private World world;
    /**
     * Posicion x
     */
    private float x;
    /**
     * Posicion y
     */
    private float y;
    /**
     * Cuerpo de colisiones de Box2d
     */
    public Body b2body;
    /**
     * Indica si la bala  está lista para borrarse
     */
    public boolean setToDestroy = false;
    /**
     * Indica si la bala está destruida
     */
    public boolean destroyed=false;

    /**
     * Crea una nueva Bala en la posicion indicada y hacia la direccion indicada
     * @param screen Pantalla de juego
     * @param x Coordenada x
     * @param y Coordenada y
     * @param fireTight True si se dispara hacia la derecha o False si es hacia la izquierda
     */
    public Bullet(PlayScreen screen, float x, float y, boolean fireTight) {

        this.fireRight = fireTight;
        this.world = screen.getWorld();
        this.x = x;
        this.y = y;
        //setRegion(new TextureRegion(screen.getAtlas().findRegion("scifiMan"), 68, 306, 3, 1));
        // Crear una textura de un solo píxel del color deseado
        Pixmap pixmap = new Pixmap(3, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 1); // Color RGBA (rojo)
        pixmap.fill();

        // Crear textura a partir del Pixmap
        Texture texture = new Texture(pixmap);
        setTexture(texture);

        setBounds(x, y, 3 / MyGdxGame.PPM, 1 / MyGdxGame.PPM);
        defineBUllet();
    }

    /**
     * ACtualiza la posición de la bala, concretamente del sprite para que coincida con la posición del cuerpo del mundo de colisiones
     * @param dt
     */
    public void update(float dt) {
        // Multiplica la velocidad lineal por el tiempo transcurrido (delta time)
        float velocityX = b2body.getLinearVelocity().x * dt;
        float velocityY = b2body.getLinearVelocity().y * dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed=true;
        }else if(!destroyed){

        // Actualiza la posición de la bala usando la velocidad normalizada
        setPosition((b2body.getPosition().x - getWidth() / 2) + velocityX,
                (b2body.getPosition().y - getHeight() / 2) + velocityY);
        }

        //setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
        // setRegion( (TextureRegion)walkAnimation.getKeyFrame(stateTime,true));

        //b2body.setLinearVelocity(velocity);

    }

    /**
     * Define una bala, su cuerpo de colisiones con sus propiedades tamaño, tipo, forma y con qué objetos puede colisionar
     */
    protected void defineBUllet() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight ? getX() + 12 / MyGdxGame.PPM : getX(), getY());
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        com.badlogic.gdx.physics.box2d.PolygonShape shape = new PolygonShape();
        shape.setAsBox(3 / MyGdxGame.PPM, 1 / MyGdxGame.PPM);
        //CircleShape shape = new CircleShape();

        //shape.setRadius(6 / MyGdxGame.PPM);

        fdef.filter.categoryBits = MyGdxGame.BULLET_BIT;
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.ENEMY_BIT;
        fdef.shape = shape;
        fdef.friction = 0;

        b2body.createFixture(fdef).setUserData(this);

        b2body.setLinearVelocity(new Vector2(fireRight ? 4 : -4, 0));
        setColor(Color.BLACK.cpy().lerp(Color.BLACK, .5f));

    }

    /**
     * Dibuja la bala siempre que no esté destruida
     * @param batch Batch del sprite
     */
    public void draw(Batch batch) {
        if(!destroyed){

        super.draw(batch);
        }
    }

    /**
     * Se ejecuta cuando la bala colisiona con algo e inmediatamente se marca para ser destruida
     */
    public void collision() {
        setToDestroy = true;
    }
}
