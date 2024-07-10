package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NPC {
    int x, y, height, width;
    int frameCounter = 0;
    int frameMultiplierForHead = 20;
    Texture bodyTexture, headTexture;
    public NPC(int x, int y, int width, int height, Texture bodyTexture, Texture headTexture){
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
                x,
                y - frameCounter / frameMultiplierForHead * GameSettings.OBJECT_SCALE * 3,
                width,
                height
        );
    }

}
