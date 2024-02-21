package com.josema.alienelysium2d.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.josema.alienelysium2d.MyGdxGame;
import com.josema.alienelysium2d.screens.PlayScreen;

public class B2WorldCreator {
    public B2WorldCreator(PlayScreen screen){
        World world=screen.getWorld();
        TiledMap map=screen.getMap();
//crear variables body y fixture
        BodyDef bdef= new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //crear los cuerpos del suelo
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();

            bdef.type= BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ MyGdxGame.PPM,(rect.getY()+rect.getHeight()/2)/MyGdxGame.PPM);

            body=world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/MyGdxGame.PPM,rect.getHeight()/2/MyGdxGame.PPM);
            fdef.shape= shape;
            body.createFixture(fdef);
        }
    }
}
