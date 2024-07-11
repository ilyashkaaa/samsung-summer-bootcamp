package com.mygdx.game.uis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameSettings;

public class TextView implements Disposable {
    private BitmapFont bitmapFont;
    private String text;

    float x;
    float y;
    float width;
    float height;

    public TextView(BitmapFont bitmapFont, float x, float y, String text) {
        this.x = x;
        this.y = y;
        this.bitmapFont = bitmapFont;
        this.text = text;
        GlyphLayout glyphLayout = new GlyphLayout(bitmapFont, text);
        width = glyphLayout.width;
        height = glyphLayout.height;
    }

    public void setText(String text) {
        this.text = text;
        GlyphLayout glyphLayout = new GlyphLayout(bitmapFont, text);
        width = glyphLayout.width;
        height = glyphLayout.height;
    }

    public void draw(SpriteBatch batch, Vector3 cameraPos) {
        bitmapFont.draw(batch, text,
                x * GameSettings.SCALE+cameraPos.x - width / 2,
                y * GameSettings.SCALE+cameraPos.y + height * GameSettings.SCALE);
    }

    @Override
    public void dispose() {
        bitmapFont.dispose();
    }


}
