package com.mygdx.game.uis.backpack;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.InInventory;
import com.mygdx.game.map.blocks.BasicBlock;

public class BackpackSlot {
    int counterOfBlock = 1;
    boolean showCount;
    public InInventory item;

    public BackpackSlot(InInventory item) {
        showCount = item instanceof BasicBlock;
        this.item = item;
    }
}
