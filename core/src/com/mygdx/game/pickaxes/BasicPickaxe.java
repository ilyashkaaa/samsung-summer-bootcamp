package com.mygdx.game.pickaxes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.InInventory;

public class BasicPickaxe implements InInventory, Disposable {
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

    @Override
    public void dispose() {
        texture.dispose();
    }
}
