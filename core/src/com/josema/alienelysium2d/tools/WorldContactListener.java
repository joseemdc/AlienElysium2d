package com.josema.alienelysium2d.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener {
    private AssetManager manager;
    public WorldContactListener(AssetManager manager){
        this.manager=manager;
    }
    @Override
    public void beginContact(Contact contact) {
        Gdx.app.log("Collision","Comienzo contacto");
        manager.get("audio/single-footstep.mp3", Sound.class).play();
        //Gdx.input.vibrate(2000);
        Gdx.input.vibrate(Input.VibrationType.HEAVY);
        //Gdx.input.vibrate(50);


    }


    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("Collision","Fin contacto");
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
