package com.josema.alienelysium2d.sprites.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.josema.alienelysium2d.MyGdxGame;
import com.josema.alienelysium2d.screens.PlayScreen;
import com.josema.alienelysium2d.sprites.Player;

public class Bullet extends Item{
    private float playerX;
    private float playerY;
    public Bullet(PlayScreen screen, float x, float y, float playerX,float playerY) {
        super(screen, x, y);
        this.playerX=playerX;
        this.playerY=playerY;
        setRegion(new TextureRegion(screen.getAtlas().findRegion("scifiMan"), 234, 12, 3, 1));
    setPosition(playerX,playerY);
        setBounds(getX(),getY(),8/ MyGdxGame.PPM,28/MyGdxGame.PPM);
    }
    public void update(float dt){

        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
        // setRegion( (TextureRegion)walkAnimation.getKeyFrame(stateTime,true));

        b2body.setLinearVelocity(velocity);

    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(playerX,playerY);
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

        b2body.createFixture(fdef);
    }
    public void draw(Batch batch){
        super.draw(batch);
    }
}
