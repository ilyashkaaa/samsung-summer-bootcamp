package com.mygdx.game.map.blocks;

import com.badlogic.gdx.graphics.Texture;

public class Dirt extends BasicBlock {
    private int durability = 5;
    private static Texture texture = new Texture("textures/blocks/dirt.png");

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
