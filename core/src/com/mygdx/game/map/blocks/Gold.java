package com.mygdx.game.map.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GameResources;

public class Gold extends BasicBlock{
    private int durability = 75;
    private static final Texture texture = new Texture(GameResources.GOLD_BLOCK_TEXTURE);
    private int hp = durability;
    private int cost = 25;
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
