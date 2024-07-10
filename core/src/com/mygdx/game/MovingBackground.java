package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Player;

public class MovingBackground {
    Texture texture;
    float texture1X;
    float texture2X;
    double speed = 0.8;
    float height;

    public MovingBackground(String pathToTexture) {
        texture1X = 0;
        texture2X = 0;
        texture = new Texture(pathToTexture);
    }

    public void move() {
        if (texture1X == texture2X) {
            texture1X = (float) (-3.2 * height);
            texture2X = 0;
        }
        texture1X -= (float) (speed * GameSettings.SCALE);
        texture2X -= (float) (speed * GameSettings.SCALE);
        if (texture1X <= -4.8 * height) {
            texture1X = (float) (1.6 * height - 1 * GameSettings.SCALE);
        }
        if (texture2X <= -4.8 * height) {
            texture2X = (float) (1.6 * height - 1 * GameSettings.SCALE);
        }

    }

    public void draw(SpriteBatch batch, MyGdxGame myGdxGame) {
        height = 3 * GameSettings.OBJECT_SCALE * GameSettings.BLOCK_SIDE + GameSettings.PLAYER_HEIGHT / 2 * GameSettings.OBJECT_SCALE + GameSettings.SCR_HEIGHT * GameSettings.SCALE / 2;
        batch.draw(texture,
                texture1X + myGdxGame.camera.position.x,
                myGdxGame.camera.position.y - GameSettings.PLAYER_HEIGHT / 2 * GameSettings.OBJECT_SCALE - GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE * 3,
                (float) (3.2 * height),
                height
        );
        batch.draw(texture,
                texture2X + myGdxGame.camera.position.x,
                myGdxGame.camera.position.y - GameSettings.PLAYER_HEIGHT / 2 * GameSettings.OBJECT_SCALE - GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE * 3,
                (float) (3.2 * height),
                height
        );
    }
}
