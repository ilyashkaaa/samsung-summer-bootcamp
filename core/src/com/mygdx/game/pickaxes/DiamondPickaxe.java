package com.mygdx.game.pickaxes;

import com.mygdx.game.GameResources;

public class DiamondPickaxe extends BasicPickaxe{
    public DiamondPickaxe (){
        super(GameResources.DIAMOND_PICKAXE_TEXTURE, 5);
    }
    public DiamondPickaxe (int damage){
        super(GameResources.DIAMOND_PICKAXE_TEXTURE, damage);
    }

    @Override
    public BasicPickaxe getNext() {
        return new DiamondPickaxe(damage + 1);
    }
}
