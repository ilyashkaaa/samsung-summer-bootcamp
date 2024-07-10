package com.mygdx.game.markets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ButtonHandlerInterface;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.NPC;

public class FoodMarket extends BasicMarket{
    private static String texturePath = GameResources.FOOD_MARKET_TEXTURE;
    NPC foodSeller;

    public FoodMarket(float x, ButtonHandlerInterface buttonHandlerInterface){
        super(texturePath, x, buttonHandlerInterface);
        foodSeller = new NPC((int) (x*GameSettings.SCALE*GameSettings.BLOCK_SIDE), (int) (GameSettings.MAP_HEIGHT * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE), (int) (12/GameSettings.SCALE/2.5), (int) (28/GameSettings.SCALE/2.5), new Texture("textures/npc/food_seller_body.png"), new Texture("textures/npc/food_seller_head.png"));
    }

    @Override
    public void  draw(SpriteBatch batch){
        super.draw(batch);
        foodSeller.draw(batch);
    }
}
