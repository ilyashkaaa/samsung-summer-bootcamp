package com.mygdx.game.map.blocks;

import com.badlogic.gdx.graphics.Texture;

public class Stone extends BasicBlock{
    private static int durability = 3;
    private static Texture texture = new Texture("textures/blocks/stone.png");
    public int getDurability() {
        return durability;
    }

    public Texture getTexture() {
        return texture;
    }
}
