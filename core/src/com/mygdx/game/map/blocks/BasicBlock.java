package com.mygdx.game.map.blocks;

import com.badlogic.gdx.graphics.Texture;

public abstract class BasicBlock {
    protected static int durability;
    private int hp;
    private boolean isNeedCollision;
     protected static Texture texture;

    public BasicBlock() {
        this.hp = durability;
        this.isNeedCollision = true;
    }

    public abstract int getDurability();

    public abstract Texture getTexture();
}
