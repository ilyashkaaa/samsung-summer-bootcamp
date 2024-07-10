package com.mygdx.game.uis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameSettings;
import com.mygdx.game.InInventory;

public class ItemFrame {
    Texture frameTexture;
    InInventory item;

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
            InInventory item,
            float width,
            float height,
            float itemWidth,
            float itemHeight
    ) {
        this.frameTexture = frameTexture;
        this.item = item;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
    }

    public InInventory getItem() {
        return item;
    }

    public void draw(SpriteBatch batch, Vector3 cameraPos){
        batch.draw(frameTexture,
                x * GameSettings.OBJECT_SCALE - width / 2f + cameraPos.x,
                y * GameSettings.OBJECT_SCALE - height / 2f + cameraPos.y,
                width, height);
        if (hasItem)
            batch.draw(item.getTexture(),
                    x * GameSettings.OBJECT_SCALE - itemWidth / 2f + cameraPos.x,
                    y * GameSettings.OBJECT_SCALE - itemHeight / 2f + cameraPos.y,
                    itemWidth, itemHeight);
    }

    public void setItem(InInventory item){
        this.item = item;
    }
    public void setHasItem(boolean hasItem) {
        this.hasItem = hasItem;
    }
    public Vector2 getPos(){return new Vector2(x, y);}
}
