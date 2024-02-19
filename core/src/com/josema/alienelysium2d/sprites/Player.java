package com.josema.alienelysium2d.sprites;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.josema.alienelysium2d.MyGdxGame;
import com.josema.alienelysium2d.screens.PlayScreen;

public class Player extends Sprite {
    public enum State {FAllING, JUMPING, STANDING, RUNNING}

    ;
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    private Animation playerRun;
    private Animation playerJump;
    private float stateTimer;
    private boolean runningRight;
    private AssetManager manager;
    private TextureRegion previousFrame;


    public Player(World world, PlayScreen screen, AssetManager manager) {
        super(screen.getAtlas().findRegion("scifiMan"));
        this.world = world;
        this.manager = manager;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
//        for (int i = 1; i < 4; i++) {
//            frames.add(new TextureRegion(getTexture(), i * 18, 34, 18, 32));
//        }
        frames.add(new TextureRegion(getTexture(), 2, 34, 15, 31));
        frames.add(new TextureRegion(getTexture(), 18, 34, 15, 31));
        frames.add(new TextureRegion(getTexture(), 34, 34, 15, 31));
        frames.add(new TextureRegion(getTexture(), 50, 34, 15, 31));

        playerRun = new Animation(0.3f, frames);
        frames.clear();

        frames.add(new TextureRegion(getTexture(), 3, 234, 15, 23));
        frames.add(new TextureRegion(getTexture(), 68, 306, 19, 31));
        playerJump = new Animation(0.1f, frames);

        definePlayer();
        playerStand = new TextureRegion(getTexture(), 2, 474, 16, 31);
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
    if(MyGdxGame.prefs.hasSoundEffects()){

                    manager.get("audio/single-footstep.mp3", Sound.class).play();
    }
                }
            }

        }
        setRegion(actualFrame);
        previousFrame = actualFrame;
    }

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

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MyGdxGame.PPM, 32 / MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        com.badlogic.gdx.physics.box2d.PolygonShape shape = new PolygonShape();
        shape.setAsBox(8/MyGdxGame.PPM,15/MyGdxGame.PPM);
        //CircleShape shape = new CircleShape();

        //shape.setRadius(6 / MyGdxGame.PPM);

        fdef.filter.categoryBits = MyGdxGame.PLAYER_BIT;
        fdef.filter.maskBits = MyGdxGame.DEFAULT_BIT;
        fdef.shape = shape;

        b2body.createFixture(fdef);

    }
}
