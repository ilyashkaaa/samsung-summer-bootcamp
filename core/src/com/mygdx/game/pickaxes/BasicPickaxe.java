package com.mygdx.game.pickaxes;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.InInventory;

public class BasicPickaxe implements InInventory {
    protected int damage;
    Texture texture;

    public BasicPickaxe (String texturePath, int damage){
        this.damage = damage;
        texture = new Texture(texturePath);
    }
    public Texture getTexture(){
        return texture;
    }
    public int getDamage(){
        return damage;
    }
}
