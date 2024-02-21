package com.josema.alienelysium2d.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.josema.alienelysium2d.MyGdxGame;

public class Controller {
    Viewport viewport;
    Stage stage;
    boolean upPressed, leftPressed, rightPressed;
    OrthographicCamera camera;
    boolean touchJustPressed = false;
    public Controller(Batch batch){
        camera = new OrthographicCamera();
        viewport = new FillViewport(MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT,camera);
        stage = new Stage(viewport,batch);
        Gdx.input.setInputProcessor(stage);

        Table table= new Table();
        table.left().bottom();

        Image upImg = new Image(new Texture("controls/switch_up_outline.png"));
        upImg.setSize(4000/MyGdxGame.PPM,4000/MyGdxGame.PPM);
        upImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed=true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });
        Image leftImg = new Image(new Texture("controls/switch_left_outline.png"));
        leftImg.setSize(4000/MyGdxGame.PPM,4000/MyGdxGame.PPM);
        leftImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed=true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });
        Image rightImg = new Image(new Texture("controls/switch_right_outline.png"));
        rightImg.setSize(4000/MyGdxGame.PPM,4000/MyGdxGame.PPM);
        rightImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed=true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });
        table.padLeft(1000/MyGdxGame.PPM);
        table.padBottom(1000/MyGdxGame.PPM);
        table.setWidth(MyGdxGame.V_WIDTH);

        table.add();
        table.add();
        table.add();
        table.add();
        table.row().pad(5,5,5,5);
        table.add(leftImg).size(leftImg.getWidth(),leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(),rightImg.getHeight());
        table.add(upImg).size(upImg.getWidth(),upImg.getHeight()).right().expandX().right();
        table.row().padBottom(5);
        table.add();
        table.add();
        table.add();
        table.add();
table.debugAll();
        stage.addActor(table);

    }
    public void draw(){
        stage.draw();
    }
    public void isUpJustPressed(){

    }

    public boolean isUpPressed() {
        return upPressed;
    }



    public boolean isLeftPressed() {
        return leftPressed;
    }


    public boolean isRightPressed() {
        return rightPressed;
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }
}
