package com.josema.alienelysium2d.sprites.items;

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

public class Bullet extends Sprite {
    boolean fireRight;
    PlayScreen screen;
    World world;
    private float x;
    private float y;
    Body b2body;
    public Bullet(PlayScreen screen, float x, float y, boolean fireTight) {
        this.screen=screen;
        this.fireRight=fireTight;
        this.world=screen.getWorld();
        this.x=x;
        this.y=y;
        setRegion(new TextureRegion(screen.getAtlas().findRegion("scifiMan"), 234, 12, 3, 1));

        setBounds(x,y,8/ MyGdxGame.PPM,6/MyGdxGame.PPM);
        defineBUllet();
    }
    public void update(float dt){
        // Multiplica la velocidad lineal por el tiempo transcurrido (delta time)
        float velocityX = b2body.getLinearVelocity().x * dt;
        float velocityY = b2body.getLinearVelocity().y * dt;
        // Actualiza la posición de la bala usando la velocidad normalizada
        setPosition((b2body.getPosition().x - getWidth() / 2) + velocityX,
                (b2body.getPosition().y - getHeight() / 2) + velocityY);

        //setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
        // setRegion( (TextureRegion)walkAnimation.getKeyFrame(stateTime,true));

        //b2body.setLinearVelocity(velocity);

    }

    protected void defineBUllet() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(fireRight? getX()+12/MyGdxGame.PPM:getX(),getY());
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        com.badlogic.gdx.physics.box2d.PolygonShape shape = new PolygonShape();
        shape.setAsBox(3/MyGdxGame.PPM,1/MyGdxGame.PPM);
        //CircleShape shape = new CircleShape();

        //shape.setRadius(6 / MyGdxGame.PPM);

        fdef.filter.categoryBits = MyGdxGame.ITEM_BIT;
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.ENEMY_BIT ;
        fdef.shape = shape;
        fdef.friction=0;

        b2body.createFixture(fdef).setUserData(this);
        b2body.setLinearVelocity(new Vector2(fireRight ? 4 : -4, 0));
    }
    public void draw(Batch batch){
        super.draw(batch);
    }
}
