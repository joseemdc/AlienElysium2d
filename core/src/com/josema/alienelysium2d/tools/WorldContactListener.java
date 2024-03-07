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
import com.josema.alienelysium2d.screens.PlayScreen;
import com.josema.alienelysium2d.sprites.Alien;
import com.josema.alienelysium2d.sprites.Player;
import com.josema.alienelysium2d.sprites.items.Bullet;

/**
 * Clase que representa el listener del mundo de coliisones
 */
public class WorldContactListener implements ContactListener {
    /**
     * AssetManager del juego
     */
    private AssetManager manager;
    /**
     * Pantalla de juego
     */
    private PlayScreen screen;

    /**
     * Crea un nuevo ContactListener para el mundo de colisiones
     * @param manager AssetManager del juego
     * @param screen Pantalla de juego
     */

    public WorldContactListener(AssetManager manager, PlayScreen screen) {
        this.manager = manager;
        this.screen=screen;
    }

    /**
     * Se ejecuta cuando comienza un contacto
     * @param contact
     */
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

        switch (cDef) {
            case MyGdxGame.ENEMY_BIT | MyGdxGame.BULLET_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.BULLET_BIT) {
                    ((Bullet) fixA.getUserData()).collision();
                    ((Alien) fixB.getUserData()).onBulletHit();
                } else if (fixB.getFilterData().categoryBits == MyGdxGame.BULLET_BIT) {
                    ((Alien) fixA.getUserData()).onBulletHit();
                    ((Bullet) fixB.getUserData()).collision();
                }
                Gdx.app.log("Disparo", "En el blanco");
                break;
            case MyGdxGame.ENEMY_BIT | MyGdxGame.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == MyGdxGame.PLAYER_BIT) {
                    ((Player) fixA.getUserData()).receiveDamage();

                } else if (fixB.getFilterData().categoryBits == MyGdxGame.PLAYER_BIT) {
                    ((Player) fixB.getUserData()).receiveDamage();
                }
                break;
            case MyGdxGame.PLAYER_BIT | MyGdxGame.META_BIT:
                screen.metaReached=true;
                break;
        }


    }

    /**
     * Se ejecuta cuando finaliza un contacto
     * @param contact
     */
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
