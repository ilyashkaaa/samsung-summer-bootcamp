package com.mygdx.game.map.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameResources;

public class Emerald extends BasicBlock{
    private int durability = 150;
    private static final Texture texture = new Texture(GameResources.EMERALD_BLOCK_TEXTURE);
    private int hp = durability;
    public int getHp(){return hp;}
    public int getDurability() {
        return durability;
    }
    public Texture getTexture() {
        return texture;
    }
    public void setHp(int hp){this.hp = hp;}

    public void setDurability(int durability) {
        this.durability = durability;
    }
    @Override
    public void dispose() {
        texture.dispose();
    }
}
