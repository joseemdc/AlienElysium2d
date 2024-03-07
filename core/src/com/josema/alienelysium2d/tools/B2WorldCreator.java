package com.josema.alienelysium2d.tools;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.josema.alienelysium2d.MyGdxGame;
import com.josema.alienelysium2d.screens.PlayScreen;
import com.josema.alienelysium2d.sprites.Alien;

import java.util.ArrayList;

/**
 * Creador de mundo de colisiones Box2d
 */
public class B2WorldCreator {
    /**
     * Devuelve el array con todos los aliens del mapa
     * @return Array de Alien
     */
    public Array<Alien> getAliens() {
        return aliens;
    }

    /**
     * Array con los Aliens de todo el mapa
     */
    private Array<Alien> aliens = new Array<Alien>();

    /**
     * Crea un creador de mundo que recoge los objetos de rectangulares de la capa 3 (suelo), 4 (meta) y 6(aliens) y los instancia en el mundo de colisiones Box2d
     * @param screen Pantalla de juego
     * @param manager AssetManager del juego
     */
    public B2WorldCreator(PlayScreen screen, AssetManager manager) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
//crear variables body y fixture
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //crear los cuerpos del suelo
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MyGdxGame.PPM, rect.getHeight() / 2 / MyGdxGame.PPM);
            fdef.filter.categoryBits = MyGdxGame.GROUND_BIT;
            fdef.filter.maskBits = MyGdxGame.GROUND_BIT | MyGdxGame.ENEMY_BIT | MyGdxGame.ITEM_BIT | MyGdxGame.ENEMY_BIT | MyGdxGame.PLAYER_BIT | MyGdxGame.BULLET_BIT;
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //crea el cuerpo de la meta
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MyGdxGame.PPM, rect.getHeight() / 2 / MyGdxGame.PPM);
            fdef.filter.categoryBits = MyGdxGame.META_BIT;
            fdef.filter.maskBits = MyGdxGame.PLAYER_BIT;
            fdef.shape = shape;
            body.createFixture(fdef);
        }
//crea las posiciones de los aliens
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            aliens.add(new Alien(screen, rect.x , rect.y , manager));

        }

    }
}
