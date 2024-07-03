package com.mygdx.game.map.blocks;

import com.badlogic.gdx.graphics.Texture;

public class Stone extends BasicBlock{
    private int durability = 3;
    private static Texture texture = new Texture("textures/blocks/stone.png");
    public int getDurability() {
        return durability;
    }

    public Texture getTexture() {
        return texture;
    }
    public void setDurability(int durability) {
        this.durability = durability;
    }
}
