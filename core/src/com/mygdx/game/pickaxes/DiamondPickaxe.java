package com.mygdx.game.pickaxes;

import com.mygdx.game.GameResources;

public class DiamondPickaxe extends BasicPickaxe{
    private int price;
    public DiamondPickaxe (){
        super(GameResources.DIAMOND_PICKAXE_TEXTURE, 10);
        price = 1000;
    }
    private DiamondPickaxe (int damage, int price){
        super(GameResources.DIAMOND_PICKAXE_TEXTURE, damage);
        this.price = price;
    }

    @Override
    public BasicPickaxe getNext() {
        return new DiamondPickaxe(damage + 1, price + 500);
    }
    @Override
    public int getCost(){
        return price; /*50 100 500 1000*/
    }
}
