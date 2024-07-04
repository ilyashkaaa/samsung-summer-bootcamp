package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GameSettings {
    public static final int SCR_WIDTH = Gdx.graphics.getWidth();
    public static final int SCR_HEIGHT = Gdx.graphics.getHeight();
    public static final float STEP_TIME = 1f / 60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float SCALE = 0.05f;
    public static final float OBJECT_SCALE = 0.05f;

    public static final int MAP_WIDTH = 20;
    public static final int MAP_HEIGHT = 100;

    public static final int BLOCK_WIDTH = 160;

    public static final int JOYSTICK_SIDE = 320;
    public static final int JOYSTICK_TRIGGER_SIDE = 1280;

    public static final int PLAYER_WIDTH = 100;
    public static final int PLAYER_HEIGHT = 270;
}
