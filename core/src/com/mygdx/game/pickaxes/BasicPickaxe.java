package com.mygdx.game.pickaxes;

import com.badlogic.gdx.graphics.Texture;

public class BasicPickaxe {
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
