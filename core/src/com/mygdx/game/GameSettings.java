package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GameSettings {


    private GameSettings() {}
    public static final int SCR_WIDTH = Gdx.graphics.getWidth();
    public static final int SCR_HEIGHT = Gdx.graphics.getHeight();
    public static final float STEP_TIME = 1f / 60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float SCALE = 0.2f;
    public static final float OBJECT_SCALE = 0.2f;

    public static final int MAP_WIDTH = 200;
    public static final int MAP_HEIGHT = 1000;
    public static final int viewBlocksX = 20;
    public static final int viewBlocksY = 20;

    public static final int BLOCK_SIDE = 160;

    public static final int MARKET_WIDTH = 800;
    public static final int MARKET_HEIGHT = 754;

    public static final int JOYSTICK_SIDE = 320;
    public static final int JOYSTICK_TRIGGER_SIDE = 640;

    public static final int PLAYER_WIDTH = 133;
    public static final int PLAYER_HEIGHT = 280;
}
