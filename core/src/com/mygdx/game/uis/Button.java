package com.mygdx.game.uis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

public class Button {
    boolean isCircle;
    boolean hasItem;
    Texture buttonTexture;
    Texture itemTexture;
    int x;
    int y;
    float width;
    float height;
    float itemWidth;
    float itemHeight;
    int radius;
    BitmapFont bitmapFont;

    String text;

    float textX;
    float textY;
    float textWidth;
    float textHeight;

    public Button(Texture buttonTexture, int x, int y, float width, float height) {
        this.buttonTexture = buttonTexture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        isCircle = false;
        hasItem = false;
    }

    public Button(Texture buttonTexture, Texture itemTexturePath, int x, int y, float width, float height, float itemWidth, float itemHeight) {
        this.buttonTexture = buttonTexture;
        itemTexture = itemTexturePath;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        isCircle = false;
        hasItem = true;
    }

    public Button(Texture buttonTexture, int x, int y, int radius) {
        this.buttonTexture = buttonTexture;
        this.x = x;
        this.y = y;
        this.radius = radius / 2;
        isCircle = true;
        hasItem = false;
    }

    public Button(Texture buttonTexture, Texture itemTexturePath, int x, int y, int radius, float itemWidth, float itemHeight) {
        this.buttonTexture = buttonTexture;
        itemTexture = itemTexturePath;
        this.x = x;
        this.y = y;
        this.radius = radius / 2;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        isCircle = true;
        hasItem = true;
    }

    public Button(Texture buttonTexture, int x, int y, float width, float height, BitmapFont font, String text) {
        this.buttonTexture = buttonTexture;
        this.x = x;
        this.y = y;
        this.text = text;
        this.bitmapFont = font;
        this.width = width;
        this.height = height;
        //MyGdxGame.bitmapFont.getData().scale(1f);
        GlyphLayout glyphLayout = new GlyphLayout(bitmapFont, text);
        textWidth = glyphLayout.width;
        textHeight = glyphLayout.height;

    }

    public void draw(SpriteBatch batch, Vector3 cameraPos) {
        if (text!= null) {
            GlyphLayout glyphLayout = new GlyphLayout(bitmapFont, text);
            textWidth = glyphLayout.width;
            textHeight = glyphLayout.height;
        }
        if (bitmapFont != null) {
            textX = x * GameSettings.OBJECT_SCALE - textWidth / 2 + cameraPos.x;
            textY = y * GameSettings.OBJECT_SCALE + textHeight / 2 + cameraPos.y;
        }
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

        if (bitmapFont != null) bitmapFont.draw(batch, text, textX, textY);
    }

    public boolean isPressed(Vector2 touchPos) {
        if (isCircle)
            return touchPos.sub(x + GameSettings.SCR_WIDTH / 2f, GameSettings.SCR_HEIGHT / 2f - y).len() <=
                    radius / GameSettings.OBJECT_SCALE;
        else {
            return touchPos.x <= x + GameSettings.SCR_WIDTH / 2f + width / GameSettings.OBJECT_SCALE / 2f &&
                    touchPos.x >= x + GameSettings.SCR_WIDTH / 2f - width / GameSettings.OBJECT_SCALE / 2f &&
                    touchPos.y >= -y + GameSettings.SCR_HEIGHT / 2f - height / GameSettings.OBJECT_SCALE / 2f &&
                    touchPos.y <= -y + GameSettings.SCR_HEIGHT / 2f + height / GameSettings.OBJECT_SCALE / 2f;
        }
    }

    public void changeItem(Texture texture) {
        itemTexture = texture;
    }

    public void changeButtonTexture(Texture texture) {
        buttonTexture = texture;
    }

    public void setHasItem(boolean hasItem) {
        this.hasItem = hasItem;
    }

    public void changeItem(Texture texture, float itemWidth, float itemHeight) {
        itemTexture = texture;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
    }
}
