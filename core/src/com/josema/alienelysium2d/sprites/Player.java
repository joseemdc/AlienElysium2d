package com.josema.alienelysium2d.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.josema.alienelysium2d.MyGdxGame;

public class Player extends Sprite {
    public World world;
    public Body b2body;

    public Player(World world){
        this.world=world;
        definePlayer();
    }
    public void definePlayer(){
        BodyDef bdef= new BodyDef();
        bdef.position.set(32/ MyGdxGame.PPM,32/MyGdxGame.PPM);
        bdef.type= BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bdef);

        FixtureDef fdef= new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(5/MyGdxGame.PPM);

        fdef.shape=shape;

        b2body.createFixture(fdef);

    }
}
