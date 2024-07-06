package com.mygdx.game.uis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameSettings;

public class Button {
    boolean isCircle;
    boolean hasItem;
    Texture buttonTexture;
    Texture itemTexture;
    int x;
    int y;
    int width;
    int height;
    int itemWidth;
    int itemHeight;
    int radius;

    public Button(String buttonTexturePath, int x, int y, int width, int height) {
        buttonTexture = new Texture(buttonTexturePath);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        isCircle = false;
        hasItem = false;
    }
    public Button(String buttonTexturePath, String itemTexturePath, int x, int y, int width, int height, int itemWidth, int itemHeight) {
        buttonTexture = new Texture(buttonTexturePath);
        itemTexture = new Texture(itemTexturePath);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        isCircle = false;
        hasItem = true;
    }
    public Button(String buttonTexturePath, int x, int y, int radius) {
        buttonTexture = new Texture(buttonTexturePath);
        this.x = x;
        this.y = y;
        this.radius = radius;
        isCircle = true;
        hasItem = false;
    }
    public Button(String buttonTexturePath, String itemTexturePath, int x, int y, int radius, int itemWidth, int itemHeight) {
        buttonTexture = new Texture(buttonTexturePath);
        itemTexture = new Texture(itemTexturePath);
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        isCircle = true;
        hasItem = true;
    }

    public void draw(SpriteBatch batch, Vector3 cameraPos) {
        if (isCircle)
            batch.draw(buttonTexture,
                    x * GameSettings.OBJECT_SCALE - radius + cameraPos.x,
                    y * GameSettings.OBJECT_SCALE - radius + cameraPos.y,
                    radius * 2, radius * 2);
        else
            batch.draw(buttonTexture,
                    x * GameSettings.OBJECT_SCALE - width / 2f + cameraPos.x,
                    y * GameSettings.OBJECT_SCALE - height / 2f + cameraPos.y,
                    width, height);
        if (hasItem)
            batch.draw(itemTexture,
                    x * GameSettings.OBJECT_SCALE - itemWidth / 2f + cameraPos.x,
                    y * GameSettings.OBJECT_SCALE - itemHeight / 2f + cameraPos.y,
                    itemWidth, itemHeight);
    }
    public boolean isPressed (Vector2 touchPos) {
        if (isCircle)
            return touchPos.sub(x + GameSettings.SCR_WIDTH / 2f, GameSettings.SCR_HEIGHT / 2f - y).len() <=
                    radius / GameSettings.OBJECT_SCALE;
        else {
            return touchPos.x <= x + GameSettings.SCR_WIDTH / 2f + width / GameSettings.OBJECT_SCALE / 2f &&
                    touchPos.x >= x + GameSettings.SCR_WIDTH / 2f - width / GameSettings.OBJECT_SCALE / 2f &&
                    touchPos.y <= y + GameSettings.SCR_HEIGHT / 2f + height / GameSettings.OBJECT_SCALE / 2f &&
                    touchPos.y >= y + GameSettings.SCR_HEIGHT / 2f - height / GameSettings.OBJECT_SCALE / 2f;
        }
    }
}
