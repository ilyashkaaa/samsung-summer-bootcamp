package com.mygdx.game.markets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.NPC;

public class UpdateMarket extends BasicMarket{
    NPC updateSeller;
    private static String texturePath = GameResources.UPDATE_MARKET_TEXTURE;
    public UpdateMarket(float x) {
        super(texturePath, x);
        updateSeller = new NPC((int) (x*GameSettings.SCALE*GameSettings.BLOCK_SIDE), (int) (GameSettings.MAP_HEIGHT * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE), (int) (13/GameSettings.SCALE/2.5), (int) (30/GameSettings.SCALE/2.5), new Texture("textures/npc/update_seller_body.png"), new Texture("textures/npc/update_seller_head.png"));
    }
    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);
        updateSeller.draw(batch);
    }
}
