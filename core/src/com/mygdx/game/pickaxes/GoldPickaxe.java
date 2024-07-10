package com.mygdx.game.pickaxes;

import com.mygdx.game.GameResources;

public class GoldPickaxe extends BasicPickaxe{
    public GoldPickaxe (){
        super(GameResources.GOLD_PICKAXE_TEXTURE, 8);
    }

    @Override
    public BasicPickaxe getNext() {
        return new DiamondPickaxe();
    }
    @Override
    public int getCost(){
        return 500; /*50 100 500 1000*/
    }
}
