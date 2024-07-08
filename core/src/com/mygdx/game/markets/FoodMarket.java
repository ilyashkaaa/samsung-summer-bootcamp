package com.mygdx.game.markets;

import com.mygdx.game.GameResources;

public class FoodMarket extends BasicMarket{
    private static String texturePath = GameResources.FOOD_MARKET_TEXTURE;

    public FoodMarket(float x){
        super(texturePath, x);
    }
}
