package com.mygdx.game.uis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameSettings;

public class ItemFrame {
    Texture frameTexture;
    Texture itemTexture;

    int x;
    int y;
    float width;
    float height;
    float itemWidth;
    float itemHeight;
    boolean hasItem = true;

    public ItemFrame(
            int x,
            int y,
            Texture frameTexture,
            Texture itemTexture,
            float width,
            float height,
            float itemWidth,
            float itemHeight
    ) {
        this.frameTexture = frameTexture;
        this.itemTexture = itemTexture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
    }

    public void draw(SpriteBatch batch, Vector3 cameraPos){
        batch.draw(frameTexture,
                x * GameSettings.OBJECT_SCALE - width / 2f + cameraPos.x,
                y * GameSettings.OBJECT_SCALE - height / 2f + cameraPos.y,
                width, height);
        if (hasItem)
            batch.draw(itemTexture,
                    x * GameSettings.OBJECT_SCALE - itemWidth / 2f + cameraPos.x,
                    y * GameSettings.OBJECT_SCALE - itemHeight / 2f + cameraPos.y,
                    itemWidth, itemHeight);
    }

    public void setItem(Texture texture){
        itemTexture = texture;
    }
    public void setHasItem(boolean hasItem) {
        this.hasItem = hasItem;
    }

}
