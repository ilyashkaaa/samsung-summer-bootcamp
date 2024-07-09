package com.mygdx.game.markets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.Npc;

public class FoodMarket extends BasicMarket{
    private static String texturePath = GameResources.FOOD_MARKET_TEXTURE;
    Npc foodSeller;

    public FoodMarket(float x){
        super(texturePath, x);
        foodSeller = new Npc(115, (int) (GameSettings.MAP_HEIGHT * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE), 5, 11, new Texture("textures/npc/food_seller_body.png"), new Texture("textures/npc/food_seller_head.png"));
    }

    @Override
    public void  draw(SpriteBatch batch){
        super.draw(batch);
        foodSeller.draw(batch);
    }
}
