package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameResources {
    public static final Sprite[] PLAYER_WALKING_TEXTURES = new Sprite[] {
            new Sprite(new Texture("textures/hero/walking/step2.png")),
            new Sprite(new Texture("textures/hero/walking/step3.png")),
            new Sprite(new Texture("textures/hero/walking/step4.png")),
            new Sprite(new Texture("textures/hero/walking/step5.png")),
            new Sprite(new Texture("textures/hero/walking/step6.png")),
            new Sprite(new Texture("textures/hero/walking/step7.png")),
            new Sprite(new Texture("textures/hero/walking/step8.png")),
            new Sprite(new Texture("textures/hero/walking/step9.png"))
    };

    public static final Sprite PLAYER_HEAD_TEXTURE = new Sprite(new Texture("textures/hero/walking/head.png"));
    public static final Sprite PLAYER_STANDING_TEXTURE = new Sprite(new Texture("textures/hero/walking/step1.png"));
}
