package com.mygdx.game.uis.backpack;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.map.blocks.BasicBlock;

public class BackpackSlot {
    int counterOfBlock = 1;
    boolean showCount;
    public String type;
    public Class<BasicBlock> block;
    public Texture texture;

    public BackpackSlot(Texture texture, Class type, boolean showCount) {
        this.type = type.getSuperclass().getSimpleName();
        if (type.getSuperclass() == BasicBlock.class)
            block = type;
        this.showCount = showCount;
        this.texture = texture;
    }
}
