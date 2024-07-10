package com.mygdx.game.uis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

public class MoneyManager {
    private static int countOfMoney;
    private static int points;
    private int x, y;
    private int width, height;
    private String text;

    BitmapFont bitmapFont;
    Texture moneyTexture;
    Texture pointsTexture;

    public MoneyManager (int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bitmapFont = MyGdxGame.bitmapFont;

        countOfMoney = 0;
        moneyTexture = GameResources.MONEY;
        pointsTexture = GameResources.POINTS;
    }

    public void draw(SpriteBatch batch, Vector3 cameraPos){
        float textWidth, textHeight;
        GlyphLayout glyphLayout = new GlyphLayout(bitmapFont, countOfMoney + "");
        textWidth = glyphLayout.width;
        textHeight = glyphLayout.height;
        if (bitmapFont != null) {
            bitmapFont.draw(
                    batch, countOfMoney + "",
                    x * GameSettings.OBJECT_SCALE - textWidth + cameraPos.x - width / 2f - 10,
                    y * GameSettings.OBJECT_SCALE + textHeight / 2f + cameraPos.y + height / 2f
            );
            bitmapFont.draw(
                    batch, points + "",
                    x * GameSettings.OBJECT_SCALE - textWidth + cameraPos.x - width / 2f - 10,
                    y * GameSettings.OBJECT_SCALE + textHeight / 2f + cameraPos.y - height / 2f
            );
        }
        batch.draw(moneyTexture,
                x * GameSettings.OBJECT_SCALE - width / 2f + cameraPos.x,
                y * GameSettings.OBJECT_SCALE + cameraPos.y,
                width, height);
        batch.draw(pointsTexture,
                x * GameSettings.OBJECT_SCALE - width / 2f + cameraPos.x,
                y * GameSettings.OBJECT_SCALE - height + cameraPos.y,
                width, height);
    }

    public static void addMoney(int countOfMoney){
        setCountOfMoney(getCountOfMoney() + countOfMoney);
        if (countOfMoney > 0)
            points += countOfMoney;
    }
    public static int getCountOfMoney() {
        return countOfMoney;
    }
    public static void setCountOfMoney(int countOfMoneySet){
        countOfMoney = countOfMoneySet;
    }
}
