package com.mygdx.game.markets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ButtonHandlerInterface;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.NPC;

public class FoodMarket extends BasicMarket implements Disposable {
    private static String texturePath = GameResources.FOOD_MARKET_TEXTURE;
    NPC foodSeller;
    private final Texture bodyTexture = new Texture("textures/npc/food_seller_body.png");
    private final Texture headTexture = new Texture("textures/npc/food_seller_head.png");

    public FoodMarket(float x, ButtonHandlerInterface buttonHandlerInterface){
        super(texturePath, x, buttonHandlerInterface);
        foodSeller = new NPC((int) (x*GameSettings.SCALE*GameSettings.BLOCK_SIDE), (int) (GameSettings.MAP_HEIGHT * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE), (int) (12/GameSettings.SCALE/2.5), (int) (28/GameSettings.SCALE/2.5), bodyTexture, headTexture);
    }

    @Override
    public void  draw(SpriteBatch batch){
        super.draw(batch);
        foodSeller.draw(batch);
    }
    @Override
    public void dispose() {
        bodyTexture.dispose();
        headTexture.dispose();
    }
}
