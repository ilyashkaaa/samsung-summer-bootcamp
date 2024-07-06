package com.mygdx.game.uis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

public class ButtonView extends View {

    Texture texture;
    BitmapFont bitmapFont;
    MyGdxGame myGdxGame;

    String text;

    float textX;
    float textY;

    public ButtonView(float x, float y, float width, float height, BitmapFont font, String texturePath, String text) {
        super(x, y, width, height);

        this.text = text;
        this.bitmapFont = font;

        texture = new Texture(texturePath);

        GlyphLayout glyphLayout = new GlyphLayout(bitmapFont, text);
        float textWidth = glyphLayout.width;
        float textHeight = glyphLayout.height;

        textX = x + (width - textWidth) / 2;
        textY = y + (height + textHeight) / 2;
    }

    public ButtonView(float x, float y, float width, float height, String texturePath, MyGdxGame myGdxGame) {
        super(x, y, width, height);
        this.myGdxGame = myGdxGame;

        texture  = new Texture(texturePath);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, myGdxGame.camera.position.x - myGdxGame.camera.viewportWidth/2 + x, myGdxGame.camera.position.y - myGdxGame.camera.viewportHeight/2 + y, width, height);
        if (bitmapFont != null) bitmapFont.draw(batch, text, textX, textY);
    }

    @Override
    public void dispose() {
       texture.dispose();
       if (bitmapFont != null) bitmapFont.dispose();
    }

}
