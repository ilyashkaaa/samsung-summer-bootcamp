package com.mygdx.game.map.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameResources;

public class Diamond extends BasicBlock {
    private int durability = 50;
    private static final Texture texture = new Texture(GameResources.DIAMOND_BLOCK_TEXTURE);
    private int hp = durability;
    private int cost = 50;
    public int getCost(){return cost;}
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
