package com.josema.alienelysium2d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.josema.alienelysium2d.screens.PlayScreen;

public class MyGdxGame extends Game {
	public static final int V_WIDTH=400;
	public static final int V_HEIGHT=208;
	public static final float PPM=100;

	public static final short DEFAULT_BIT =1;
	public static final short PLAYER_BIT =2;

	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
