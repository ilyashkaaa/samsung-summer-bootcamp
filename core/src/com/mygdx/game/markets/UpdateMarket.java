package com.mygdx.game.markets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ButtonHandlerInterface;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.NPC;
import com.mygdx.game.entities.Player;
import com.mygdx.game.pickaxes.DiamondPickaxe;
import com.mygdx.game.pickaxes.GoldPickaxe;
import com.mygdx.game.pickaxes.IronPickaxe;
import com.mygdx.game.pickaxes.Stick;
import com.mygdx.game.pickaxes.StonePickaxe;
import com.mygdx.game.uis.Button;
import com.mygdx.game.uis.ItemFrame;
import com.mygdx.game.uis.backpack.BackpackUI;

public class UpdateMarket extends BasicMarket{
    NPC updateSeller;
    public ItemFrame nowPickaxe;
    public ItemFrame wantPickaxe;
    public Button upgradeButton;
    private static String texturePath = GameResources.UPDATE_MARKET_TEXTURE;
    public UpdateMarket(float x, ButtonHandlerInterface buttonHandlerInterface) {
        super(texturePath, x, buttonHandlerInterface);

        upgradeButton = new Button(GameResources.BUTTON_BACKGROUND, 300, -150,
                (int) (600 * GameSettings.SCALE), (int) (200 * GameSettings.SCALE), MyGdxGame.bitmapFont, "upgrade");
        nowPickaxe = new ItemFrame(-400, 0, GameResources.BUTTON_BACKGROUND, GameResources.STICK_PICKAXE.getTexture(),
                (int) (350 * GameSettings.SCALE), (int) (350 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE));
        wantPickaxe = new ItemFrame(300, 130, GameResources.BUTTON_BACKGROUND, GameResources.STONE_PICKAXE.getTexture(),
                (int) (250 * GameSettings.SCALE), (int) (250 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE));
        updateSeller = new NPC((int) (x*GameSettings.SCALE*GameSettings.BLOCK_SIDE), (int) (GameSettings.MAP_HEIGHT * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE), (int) (13/GameSettings.SCALE/2.5), (int) (30/GameSettings.SCALE/2.5), new Texture("textures/npc/update_seller_body.png"), new Texture("textures/npc/update_seller_head.png"));
    }
    @Override
    public void drawInterface(Vector3 cameraPos, MyGdxGame myGdxGame){
        super.drawInterface(cameraPos, myGdxGame);
        upgradeButton.draw(myGdxGame.batch, cameraPos);
        nowPickaxe.draw(myGdxGame.batch, cameraPos);
        wantPickaxe.draw(myGdxGame.batch, cameraPos);
    }
    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);
        updateSeller.draw(batch);
    }

    @Override
    public void doThing(Player player, BackpackUI backpackUI) {

            if (buttonHandle.buttonHandler(exitButton)) {
                inMarket = false;
            } else if (inMarket) {
            if (buttonHandle.buttonHandler(upgradeButton) && !needToReset){
                needToReset = true;
                if (player.pickaxe instanceof Stick){
                    player.setPickaxe(StonePickaxe.class);
                    backpackUI.setItem(0, player.pickaxe);
                    nowPickaxe.setItem(player.pickaxe.getTexture());
                    wantPickaxe.setItem(GameResources.IRON_PICKAXE.getTexture());
                }
                else if (player.pickaxe instanceof StonePickaxe){
                    player.setPickaxe(IronPickaxe.class);
                    backpackUI.setItem(0, player.pickaxe);
                    nowPickaxe.setItem(player.pickaxe.getTexture());
                    wantPickaxe.setItem(GameResources.GOLD_PICKAXE);
                }
                else if (player.pickaxe instanceof IronPickaxe){
                    player.setPickaxe(GoldPickaxe.class);
                    backpackUI.setItem(0, player.pickaxe);
                    nowPickaxe.setItem(player.pickaxe.getTexture());
                    wantPickaxe.setItem(GameResources.DIAMOND_PICKAXE);
                }
                else if (player.pickaxe instanceof GoldPickaxe){
                    player.setPickaxe(DiamondPickaxe.class);
                    backpackUI.setItem(0, player.pickaxe);
                    nowPickaxe.setItem(player.pickaxe.getTexture());
                    wantPickaxe.setHasItem(false);
                }
            }
            else if (buttonHandle.buttonHandler(exitButton)) {
                inMarket = false;
            }
        }
    }
}
