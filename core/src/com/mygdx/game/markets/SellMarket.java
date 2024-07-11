package com.mygdx.game.markets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.ButtonHandlerInterface;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.entities.NPC;
import com.mygdx.game.entities.Player;
import com.mygdx.game.map.blocks.BasicBlock;
import com.mygdx.game.uis.Button;
import com.mygdx.game.uis.MoneyManager;
import com.mygdx.game.uis.backpack.BackpackUI;

public class SellMarket extends BasicMarket {
    private static String texturePath = GameResources.SELL_MARKET_TEXTURE;

//    public Button buyButton;
//    public Button sellButton;
//    public Button diamondPickaxe;
//    public Button goldPickaxe;
    private Button sellButton;
    private boolean needToResetSellButton = false;
    private int lastIndex = -1;
    BackpackUI backpackUI;

    private final Texture bodyTexture = new Texture("textures/npc/market_seller_body.png");
    private final Texture headTexture = new Texture("textures/npc/market_seller_head.png");
    NPC marketSeller;


    public SellMarket(float x, ButtonHandlerInterface buttonHandler) {
        super(texturePath, x, buttonHandler);
        sellButton = new Button(
                GameResources.BUTTON_BACKGROUND,
                0, -400,
                (int) (600 * GameSettings.SCALE),
                (int) (150 * GameSettings.SCALE),
                MyGdxGame.bitmapFont, "sell"
        );//        buyButton = new Button(GameResources.BUTTON_BACKGROUND, GameResources.DIAMOND_PICKAXE,
//                500, 0, (int) (250 * GameSettings.SCALE), (int) (250 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE));
//        sellButton = new Button(GameResources.BUTTON_BACKGROUND, GameResources.MONEY,
//                -500, 0, (int) (250 * GameSettings.SCALE), (int) (250 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE));
//        diamondPickaxe = new Button(GameResources.BUTTON_BACKGROUND, GameResources.DIAMOND_PICKAXE,
//                500, 0, (int) (250 * GameSettings.SCALE), (int) (250 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE));
//        goldPickaxe = new Button(GameResources.BUTTON_BACKGROUND, GameResources.GOLD_PICKAXE,
//                -500, 0, (int) (250 * GameSettings.SCALE), (int) (250 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE));
        marketSeller = new NPC((int) (x * GameSettings.SCALE * GameSettings.BLOCK_SIDE), (int) (GameSettings.MAP_HEIGHT * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE), (int) (15 / GameSettings.SCALE / 2.5), (int) (32 / GameSettings.SCALE / 2.5), bodyTexture, headTexture);

    }

    @Override
    public void drawInterface(Vector3 cameraPos, MyGdxGame myGdxGame) {
        if (backpackUI != null) {
            backpackUI.drawInterface(myGdxGame.batch, cameraPos);
        }
        exitButton.draw(myGdxGame.batch, cameraPos);
        sellButton.draw(myGdxGame.batch, cameraPos);
    }

    @Override
    public void setState(int state) {

    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        marketSeller.draw(batch);
    }

    @Override
    public void doThing(Player player, BackpackUI backpackUI) {
        if (Gdx.input.isTouched()) {
            if (buttonHandle.buttonHandler(exitButton)) {
                inMarket = false;
                backpackUI.cancelSelection();
                backpackUI.backpackSlots[lastIndex].changeButtonTexture(GameResources.SELECTED_BLOCK);
                backpackUI.selectionIndex = lastIndex;
                lastIndex = -1;
            } else if (inMarket) {
                for (int i = 0; i < backpackUI.backpackSlots.length; i++) {
                    if (buttonHandle.buttonHandler(backpackUI.backpackSlots[i]) && i < backpackUI.slotsInventoryItem.size()) {
                        backpackUI.cancelSelection();
                        backpackUI.backpackSlots[i].changeButtonTexture(GameResources.SELECTED_BLOCK);
                        backpackUI.selectionIndex = i;
                        break;
                    }
                }
                if (buttonHandle.buttonHandler(sellButton) && backpackUI.getCurrentItem().item instanceof BasicBlock && !needToResetSellButton) {
                    needToResetSellButton = true;
                    MoneyManager.addMoney(backpackUI.getCurrentItem().item.getCost());
                    backpackUI.removeItemFromInventory((Class<? extends BasicBlock>) backpackUI.getCurrentItem().item.getClass());
                    if (backpackUI.slotsInventoryItem.size() - 1 < lastIndex)
                        lastIndex = 0;
                }
            }
            if (lastIndex == -1) {
                this.backpackUI = backpackUI;
                lastIndex = backpackUI.selectionIndex;
            }
        }
        else
            needToResetSellButton = false;
    }


    @Override
    public void dispose() {
        bodyTexture.dispose();
        headTexture.dispose();
    }
}
