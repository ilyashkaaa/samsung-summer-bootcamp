package com.mygdx.game.markets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.ButtonHandlerInterface;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.NPC;
import com.mygdx.game.entities.Player;
import com.mygdx.game.pickaxes.DiamondPickaxe;
import com.mygdx.game.pickaxes.GoldPickaxe;
import com.mygdx.game.uis.Button;
import com.mygdx.game.uis.backpack.BackpackUI;

public class SellMarket extends BasicMarket{
    private static String texturePath = GameResources.SELL_MARKET_TEXTURE;

    public Button buyButton;
    public Button sellButton;
    public Button diamondPickaxe;
    public Button goldPickaxe;

    public boolean inMenu = true;
    public boolean inSell = false;
    public boolean inBuy = false;
    private final Texture bodyTexture = new Texture("textures/npc/market_seller_body.png");
    private  final Texture headTexture = new Texture("textures/npc/market_seller_head.png");
    NPC marketSeller;


    public SellMarket(float x, ButtonHandlerInterface buttonHandler){
        super(texturePath, x, buttonHandler);
        buyButton = new Button(GameResources.BUTTON_BACKGROUND, GameResources.DIAMOND_PICKAXE,
                500, 0, (int) (250 * GameSettings.SCALE), (int) (250 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE));
        sellButton = new Button(GameResources.BUTTON_BACKGROUND, GameResources.MONEY,
                -500, 0, (int) (250 * GameSettings.SCALE), (int) (250 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE));
        diamondPickaxe = new Button(GameResources.BUTTON_BACKGROUND, GameResources.DIAMOND_PICKAXE,
                500, 0, (int) (250 * GameSettings.SCALE), (int) (250 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE));
        goldPickaxe = new Button(GameResources.BUTTON_BACKGROUND, GameResources.GOLD_PICKAXE,
                -500, 0, (int) (250 * GameSettings.SCALE), (int) (250 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE));
        marketSeller = new NPC((int) (x*GameSettings.SCALE*GameSettings.BLOCK_SIDE), (int) (GameSettings.MAP_HEIGHT * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE), (int) (15/GameSettings.SCALE/2.5), (int) (32/GameSettings.SCALE/2.5), bodyTexture, headTexture);

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
    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);
        marketSeller.draw(batch);
    }

    @Override
    public void doThing(Player player, BackpackUI backpackUI) {
        if (inMenu) {
            if (buttonHandle.buttonHandler(buyButton)) {
                inMenu = false;
                inBuy = true;
            } else if (buttonHandle.buttonHandler(sellButton)) {
                inMenu = false;
                inSell = true;
            } else if (buttonHandle.buttonHandler(exitButton) && !needToReset) {
                inMarket = false;
                inMenu = true;
                inBuy = false;
                inSell = false;
            }
        } else if (inBuy) {
            if (buttonHandle.buttonHandler(diamondPickaxe)) {
                player.setPickaxe(DiamondPickaxe.class);
                backpackUI.setItem(0, player.pickaxe);
            } else if (buttonHandle.buttonHandler(goldPickaxe)) {
                player.setPickaxe(GoldPickaxe.class);
                backpackUI.setItem(0, player.pickaxe);
            } else if (buttonHandle.buttonHandler(exitButton) && !needToReset) {
                inBuy = false;
                inMenu = true;
            }
        } else if (inSell) {
            if (buttonHandle.buttonHandler(exitButton) && !needToReset) {
                inSell = false;
                inMenu = true;
            }
        }
    }


    @Override
    public void dispose() {
        bodyTexture.dispose();
        headTexture.dispose();
    }
}
