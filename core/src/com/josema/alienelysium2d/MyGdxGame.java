package com.josema.alienelysium2d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.josema.alienelysium2d.screens.MainScreen;
import com.josema.alienelysium2d.screens.PlayScreen;

public class MyGdxGame extends Game {
	public static final int V_WIDTH=400;
	public static final int V_HEIGHT=208;
	public static final float PPM=100;

	public static final short DEFAULT_BIT =1;
	public static final short PLAYER_BIT =2;

	public SpriteBatch batch;
	public  AssetManager manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager= new AssetManager();
		manager.load("audio/spaceship-ambience-with-effects-21420.mp3", Music.class);
		manager.load("audio/single-footstep.mp3", Sound.class);

		manager.finishLoading();
		setScreen(new MainScreen(this,manager,batch));
	}

	@Override
	public void render () {
		super.render();

	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		manager.dispose();

	}
}
