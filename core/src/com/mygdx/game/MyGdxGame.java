package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.screens.GameScreen;

public class MyGdxGame extends Game {
	public World world;
	public SpriteBatch batch;
	public BitmapFont bitmapFont;
	public OrthographicCamera camera;
	public GameScreen gameScreen;
	float accumulator = 0;
	
	@Override
	public void create () {
		Gdx.graphics.setVSync(true);
		Gdx.graphics.setForegroundFPS(60);
		Box2D.init();
		world = new World(new Vector2(0, -150), true);
		bitmapFont = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
		bitmapFont.setColor(Color.WHITE);
		bitmapFont.getData().scale(0.0000005f);






		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameSettings.SCR_WIDTH*GameSettings.SCALE,GameSettings.SCR_HEIGHT*GameSettings.SCALE);
		batch = new SpriteBatch();
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}
	@Override
	public void dispose () {
		batch.dispose();

	}
	public void stepWorld() {
		float delta = Gdx.graphics.getDeltaTime();
		accumulator += Math.min(delta, 0.25f);

		if (accumulator >= GameSettings.STEP_TIME) {
			accumulator -= GameSettings.STEP_TIME;
			world.step(GameSettings.STEP_TIME, GameSettings.VELOCITY_ITERATIONS, GameSettings.POSITION_ITERATIONS);
		}
	}
}
