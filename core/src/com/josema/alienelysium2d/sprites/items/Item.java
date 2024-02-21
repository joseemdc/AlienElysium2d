package com.josema.alienelysium2d.sprites.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.josema.alienelysium2d.MyGdxGame;
import com.josema.alienelysium2d.screens.PlayScreen;

public abstract class Item extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;
    public Item(PlayScreen screen,float x ,float y){
        this.world=screen.getWorld();
        this.screen=screen;
        setPosition(x,y);
        defineEnemy();
        velocity= new Vector2(15f,0);
    }
    protected abstract void defineEnemy();

}
