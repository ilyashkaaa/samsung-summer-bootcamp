package com.mygdx.game.uis.backpack;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.map.blocks.BasicBlock;

public class BackpackSlot {
    int counterOfBlock = 1;
    Texture texture;

    public BackpackSlot(Texture texture) {
        this.texture = texture;
    }
}
