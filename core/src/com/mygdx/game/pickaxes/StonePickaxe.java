package com.mygdx.game.pickaxes;

import com.mygdx.game.GameResources;

public class StonePickaxe extends BasicPickaxe{
    public StonePickaxe (){super(GameResources.STONE_PICKAXE_TEXTURE, 3);}

    @Override
    public BasicPickaxe getNext() {
        return new IronPickaxe();
    }
    @Override
    public int getCost(){
        return 25; /*50 100 500 1000*/
    }
}
