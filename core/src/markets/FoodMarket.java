package markets;

import com.mygdx.game.GameResources;
import com.mygdx.game.map.blocks.BasicBlock;

public class FoodMarket extends BasicMarket{
    private static String texturePath = GameResources.FOOD_MARKET_TEXTURE;

    public FoodMarket(float x, BasicBlock[][] mapArray){
        super(texturePath, x, mapArray);
    }
}
