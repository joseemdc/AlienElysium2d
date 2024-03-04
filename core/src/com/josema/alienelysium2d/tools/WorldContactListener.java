package com.josema.alienelysium2d.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.josema.alienelysium2d.MyGdxGame;
import com.josema.alienelysium2d.sprites.Alien;
import com.josema.alienelysium2d.sprites.items.Bullet;

public class WorldContactListener implements ContactListener {
    private AssetManager manager;

    public WorldContactListener(AssetManager manager) {
        this.manager = manager;
    }

    @Override
    public void beginContact(Contact contact) {
        Gdx.app.log("Collision", "Comienzo contacto");
        manager.get("audio/single-footstep.mp3", Sound.class).play();
        //Gdx.input.vibrate(2000);
        Gdx.input.vibrate(Input.VibrationType.HEAVY);
        //Gdx.input.vibrate(50);

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();


        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case MyGdxGame.ENEMY_BIT | MyGdxGame.BULLET_BIT:
                if(fixA.getFilterData().categoryBits == MyGdxGame.BULLET_BIT){
                    ((Bullet)fixA.getUserData()).collision();
                    ((Alien)fixB.getUserData()).onBulletHit();
                }else if(fixB.getFilterData().categoryBits == MyGdxGame.BULLET_BIT){
                    ((Alien)fixA.getUserData()).onBulletHit();
                    ((Bullet)fixB.getUserData()).collision();
                }
                Gdx.app.log("Disparo", "En el blanco");
                break;
            case MyGdxGame.ENEMY_BIT |MyGdxGame.PLAYER_BIT:

        }


    }


    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("Collision", "Fin contacto");
        Gdx.input.vibrate(Input.VibrationType.LIGHT);
        //Gdx.input.vibrate(35);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
