package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.screens.GameScreen;

public class MyGdxGame extends Game {
	public World world;
	public SpriteBatch batch;
	public OrthographicCamera camera;
	public GameScreen gameScreen;
	float accumulator = 0;
	
	@Override
	public void create () {
		Box2D.init();
		world = new World(new Vector2(0, 0), true);
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
