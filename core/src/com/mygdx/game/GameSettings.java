package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class GameSettings {
    public static final int SCR_WIDTH = Gdx.graphics.getWidth();
    public static final int SCR_HEIGHT = Gdx.graphics.getHeight();
    public static final float STEP_TIME = 1f / 60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
}
