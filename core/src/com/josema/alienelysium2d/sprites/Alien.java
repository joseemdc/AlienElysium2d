package com.josema.alienelysium2d.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.josema.alienelysium2d.MyGdxGame;
import com.josema.alienelysium2d.screens.PlayScreen;

public class Alien extends Enemy {
private float stateTime;
private float walkTime;
private Animation walkAnimation;
private Animation attackAnimation;
private int health;
    public enum State {WALKING, ATTACKING, STANDING, RUNNING}
    public State currentState;
    public State previousState;
    private boolean runningRight;

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
        walkTime =0;
        setBounds(getX(),getY(),40/MyGdxGame.PPM,40/MyGdxGame.PPM);
    }
    public void update(float dt){
        stateTime+=dt;
        walkTime+=dt;
        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
       // setRegion( (TextureRegion)walkAnimation.getKeyFrame(stateTime,true));
        b2body.setLinearVelocity(velocity);
        TextureRegion actualFrame = getFrame(dt);
        setRegion(getFrame(dt));
        if(walkTime>2f){
            reverseVelocity(true,false);
            walkTime=0;
        }
    }
    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region= new TextureRegion();

        switch (currentState) {
            case WALKING:
                region = (TextureRegion) walkAnimation.getKeyFrame(stateTime,true);
                break;
            case RUNNING:
                region = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);

                break;
            case ATTACKING:
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
    public State getState() {
        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.ATTACKING)) {
            return Alien.State.RUNNING;
        } else if (b2body.getLinearVelocity().y < 0) {
            return State.ATTACKING;
        } else if (b2body.getLinearVelocity().x != 0) {
            return Alien.State.WALKING;
        } else {
            return Alien.State.STANDING;
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(140 / MyGdxGame.PPM, 80 / MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        com.badlogic.gdx.physics.box2d.PolygonShape shape = new PolygonShape();
        shape.setAsBox(8/MyGdxGame.PPM,20/MyGdxGame.PPM);
        //CircleShape shape = new CircleShape();

        //shape.setRadius(6 / MyGdxGame.PPM);

        fdef.filter.categoryBits = MyGdxGame.ENEMY_BIT;
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.ENEMY_BIT |MyGdxGame.PLAYER_BIT | MyGdxGame.ITEM_BIT | MyGdxGame.BULLET_BIT;
        fdef.shape = shape;

        b2body.createFixture(fdef);
    }

    @Override
    public void onBulletHit() {

    }

    public boolean isAttacking(){
        return true;
    }
    public void draw(Batch batch){
super.draw(batch);
    }
}
