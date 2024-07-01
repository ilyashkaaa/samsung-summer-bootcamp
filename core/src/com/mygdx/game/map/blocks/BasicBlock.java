package com.mygdx.game.map.blocks;

import com.badlogic.gdx.graphics.Texture;

public class BasicBlock {
    private int durability;
    protected static Texture texture;
    public BasicBlock(int durability) {
        this.durability = durability;
    }

    public int getDurability() {
        return durability;
    }

}
