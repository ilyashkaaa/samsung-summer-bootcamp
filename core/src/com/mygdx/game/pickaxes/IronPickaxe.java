package com.mygdx.game.pickaxes;

import com.mygdx.game.GameResources;

public class IronPickaxe extends BasicPickaxe{
    public IronPickaxe (){
        super(GameResources.IRON_PICKAXE_TEXTURE, 5);
    }

    @Override
    public BasicPickaxe getNext() {
        return new GoldPickaxe();
    }
    @Override
    public int getCost(){
        return 100; /*50 100 500 1000*/
    }
}
