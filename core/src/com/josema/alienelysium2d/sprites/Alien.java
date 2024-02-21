package com.josema.alienelysium2d.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.josema.alienelysium2d.MyGdxGame;
import com.josema.alienelysium2d.screens.PlayScreen;

public class Alien extends Enemy {
private float stateTime;
private Animation walkAnimation;
private Animation attackAnimation;
private Array<TextureRegion>frames;
    public Alien(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames= new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"),1,1,131,145));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"),183,1,132,145));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"),365,1,132,144));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"),547,1,133,141));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"),729,1,132,142));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"),911,1,132,144));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"),1093,1,132,145));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"),1275,1,133,146));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"),1457,1,133,141));
        frames.add(new TextureRegion(screen.getAtlas2().findRegion("1_"),1639,1,132,144));
        walkAnimation= new Animation(0.15f,frames);
        stateTime = 0;
        setBounds(getX(),getY(),40/MyGdxGame.PPM,40/MyGdxGame.PPM);
    }
    public void update(float dt){
        stateTime+=dt;
        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
        setRegion( (TextureRegion)walkAnimation.getKeyFrame(stateTime,true));
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MyGdxGame.PPM, 32 / MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        com.badlogic.gdx.physics.box2d.PolygonShape shape = new PolygonShape();
        shape.setAsBox(8/MyGdxGame.PPM,20/MyGdxGame.PPM);
        //CircleShape shape = new CircleShape();

        //shape.setRadius(6 / MyGdxGame.PPM);

        fdef.filter.categoryBits = MyGdxGame.ENEMY_BIT;
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.ENEMY_BIT |MyGdxGame.PLAYER_BIT;
        fdef.shape = shape;

        b2body.createFixture(fdef);
    }
}
