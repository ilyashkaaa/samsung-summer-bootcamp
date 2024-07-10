package com.mygdx.game.map.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameResources;

public class Dirt extends BasicBlock {
    private int durability = 5;
    private int hp = durability;
    private static final Texture texture = new Texture(GameResources.DIRT_BLOCK_TEXTURE);
    private int cost = 1;
    public int getCost(){return cost;}
    public int getDurability() {
        return durability;
    }
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
