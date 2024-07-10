package com.mygdx.game.map.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameResources;

public class Stone extends BasicBlock{
    private int durability = 10;
    private static final Texture texture = new Texture(GameResources.STONE_BLOCK_TEXTURE);
    public int getDurability() {
        return durability;
    }
    private int hp = durability;
    public int getHp(){return hp;}
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
