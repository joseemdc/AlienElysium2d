package com.josema.alienelysium2d.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.josema.alienelysium2d.screens.PlayScreen;

/**
 * Clase que representa a un Enemigo
 */
public abstract class Enemy extends Sprite {
    /**
     * Mundo de colisiones Box2d
     */
    protected World world;
    /**
     * Pantalla de juego
     */
    protected PlayScreen screen;
    /**
     * Cuerpo de coliones Box2d
     */
    public Body b2body;
    /**
     * Velocidad que se le aplica
     */
    public Vector2 velocity;

    /**
     * Crea un nuevo Enemig y establce su posicion a las cordenadas dadas y la velocidad
     * @param screen Pantalla de juego
     * @param x Coordenada x
     * @param y Coordenada y
     */
    public Enemy(PlayScreen screen,float x ,float y){
        this.world=screen.getWorld();
        this.screen=screen;
        setPosition(x,y);
        defineEnemy();
        velocity= new Vector2(1,0);
    }
    protected abstract void defineEnemy();
    public abstract void onBulletHit();

    /**
     * Invierte la velocidad al cambiar de dirección
     * @param x
     * @param y
     */
    public void reverseVelocity(boolean x, boolean y){
        if(x){
            velocity.x=-velocity.x;

        }
        if(y){
            velocity.y=-velocity.y;
        }
    }
}
