package com.mygdx.game.map.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameResources;

public class Amethyst extends BasicBlock{
    private int durability = 200;
    private static final Texture texture = new Texture(GameResources.AMETHYST_BLOCK_TEXTURE);

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
