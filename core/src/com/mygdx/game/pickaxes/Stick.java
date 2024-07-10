package com.mygdx.game.pickaxes;

import com.mygdx.game.GameResources;

public class Stick extends BasicPickaxe{
    public Stick (){
        super(GameResources.STICK_PICKAXE_TEXTURE, 1);
    }

    @Override
    public BasicPickaxe getNext() {
        return new StonePickaxe();
    }

}
