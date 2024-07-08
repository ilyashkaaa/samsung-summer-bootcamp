package com.mygdx.game.markets;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.uis.Button;

public class SellMarket extends BasicMarket{
    private static String texturePath = GameResources.SELL_MARKET_TEXTURE;

    public Button buyButton;
    public Button sellButton;
    public Button diamondPickaxe;
    public Button goldPickaxe;

    public boolean inMenu = true;
    public boolean inSell = false;
    public boolean inBuy = false;

    public SellMarket(float x){
        super(texturePath, x);
        buyButton = new Button("textures/buttons/button_background_square.png", "textures/items/pickaxes/diamond_pickaxe.png",
                500, 0, (int) (250 * GameSettings.SCALE), (int) (250 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE));
        sellButton = new Button("textures/buttons/button_background_square.png", "textures/items/ores/gold.png",
                -500, 0, (int) (250 * GameSettings.SCALE), (int) (250 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE));
        diamondPickaxe = new Button("textures/buttons/button_background_square.png", "textures/items/pickaxes/diamond_pickaxe.png",
                500, 0, (int) (250 * GameSettings.SCALE), (int) (250 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE));
        goldPickaxe = new Button("textures/buttons/button_background_square.png", "textures/items/pickaxes/gold_pickaxe.png",
                -500, 0, (int) (250 * GameSettings.SCALE), (int) (250 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE));
    }

    @Override
    public void drawInterface(Vector3 cameraPos, MyGdxGame myGdxGame){
        super.drawInterface(cameraPos, myGdxGame);
        if (inMenu) {
            buyButton.draw(myGdxGame.batch, cameraPos);
            sellButton.draw(myGdxGame.batch, cameraPos);
        }
        if (inBuy){
            diamondPickaxe.draw(myGdxGame.batch, cameraPos);
            goldPickaxe.draw(myGdxGame.batch, cameraPos);
        }
    }

    @Override
    public void setState(int state){
        switch (state) {
            case 0:
                inMenu = true;
                break;
            case 1:
                inMenu = false;
                break;
            case 2:
                inSell = true;
                break;
            case 3:
                inSell = false;
                break;
            case 4:
                inBuy = true;
                break;
            case 5:
                inBuy = false;
                break;
        }
    }
}
