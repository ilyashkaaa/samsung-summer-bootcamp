package com.mygdx.game;

import static com.mygdx.game.GameSettings.PLAYER_HEIGHT;
import static com.mygdx.game.GameSettings.PLAYER_WIDTH;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.PlayerStates;

public class Npc {
    int x, y, height, width;
    int frameCounter = 0;
    int frameMultiplierForHead = 20;
    Texture bodyTexture, headTexture;
    public Npc(int x, int y, int width, int height, Texture bodyTexture, Texture headTexture){
        this.x = x;
        this.y = y;
        this.bodyTexture = bodyTexture;
        this.headTexture = headTexture;
        this.height = height;
        this.width = width;
    }
    public void draw(SpriteBatch batch){
        batch.draw(bodyTexture, x, y, width, height);
        frameCounter++;
        if (frameCounter == 2 * frameMultiplierForHead - 1) frameCounter = 0;
        batch.draw(headTexture,
                x - width * GameSettings.OBJECT_SCALE / 2,
                y - height * GameSettings.OBJECT_SCALE / 2 - frameCounter / frameMultiplierForHead * GameSettings.OBJECT_SCALE * 3,
                width,
                height
        );
    }

}
