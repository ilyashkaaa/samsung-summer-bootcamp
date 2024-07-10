package com.mygdx.game.markets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ButtonHandlerInterface;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.NPC;
import com.mygdx.game.entities.Player;
import com.mygdx.game.pickaxes.Stick;
import com.mygdx.game.pickaxes.StonePickaxe;
import com.mygdx.game.uis.Button;
import com.mygdx.game.uis.ItemFrame;
import com.mygdx.game.uis.MoneyManager;
import com.mygdx.game.uis.backpack.BackpackUI;

public class UpdateMarket extends BasicMarket {
    NPC updateSeller;
    public ItemFrame nowPickaxe;
    public ItemFrame wantPickaxe;
    public Button upgradeButton;
    private BitmapFont bitmapFont;
    float textWidth;
    float textHeight;
    private final Texture bodyTexture = new Texture("textures/npc/update_seller_body.png");
    private final Texture headTexture = new Texture("textures/npc/update_seller_head.png");

    private static String texturePath = GameResources.UPDATE_MARKET_TEXTURE;

    public UpdateMarket(float x, ButtonHandlerInterface buttonHandlerInterface) {
        super(texturePath, x, buttonHandlerInterface);

        bitmapFont = MyGdxGame.bitmapFont;
        GlyphLayout glyphLayout = new GlyphLayout(bitmapFont, "");
        textWidth = glyphLayout.width;
        textHeight = glyphLayout.height;
        upgradeButton = new Button(
                GameResources.BUTTON_BACKGROUND,
                300, -150,
                (int) (600 * GameSettings.SCALE),
                (int) (200 * GameSettings.SCALE),
                MyGdxGame.bitmapFont, "upgrade"
        );
        nowPickaxe = new ItemFrame(
                -400, 0,
                GameResources.BUTTON_BACKGROUND,
                new Stick(),
                (int) (350 * GameSettings.SCALE), (int) (350 * GameSettings.SCALE), (int) (200 * GameSettings.SCALE), (int) (200 * GameSettings.SCALE));
        wantPickaxe = new ItemFrame(300, 130, GameResources.BUTTON_BACKGROUND, new StonePickaxe(),
                (int) (250 * GameSettings.SCALE), (int) (250 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE), (int) (100 * GameSettings.SCALE));
        updateSeller = new NPC((int) (x * GameSettings.SCALE * GameSettings.BLOCK_SIDE), (int) (GameSettings.MAP_HEIGHT * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE), (int) (13 / GameSettings.SCALE / 2.5), (int) (30 / GameSettings.SCALE / 2.5), bodyTexture, headTexture);
    }

    @Override
    public void drawInterface(Vector3 cameraPos, MyGdxGame myGdxGame) {
        super.drawInterface(cameraPos, myGdxGame);
        upgradeButton.draw(myGdxGame.batch, cameraPos);
        nowPickaxe.draw(myGdxGame.batch, cameraPos);
        wantPickaxe.draw(myGdxGame.batch, cameraPos);
        GlyphLayout glyphLayout = new GlyphLayout(bitmapFont, Integer.toString(wantPickaxe.getItem().getCost()));
        textWidth = glyphLayout.width;
        textHeight = glyphLayout.height;
        if (bitmapFont != null) bitmapFont.draw(
                myGdxGame.batch,
                Integer.toString(wantPickaxe.getItem().getCost()),
                170 * GameSettings.OBJECT_SCALE + cameraPos.x - textWidth,
                130 * GameSettings.OBJECT_SCALE + cameraPos.y + textHeight / 2f
        );
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        updateSeller.draw(batch);
    }

    @Override
    public void doThing(Player player, BackpackUI backpackUI) {

        if (buttonHandle.buttonHandler(exitButton)) {
            inMarket = false;
        } else if (inMarket) {
            if (buttonHandle.buttonHandler(upgradeButton) && !needToReset/* && MoneyManager.getCountOfMoney() >= player.pickaxe.getNext().getCost()*/) {
                needToReset = true;
                player.setPickaxe(player.pickaxe.getNext());
                wantPickaxe.setItem(player.pickaxe.getNext());
                backpackUI.setItem(0, player.pickaxe);
                nowPickaxe.setItem(player.pickaxe);
                MoneyManager.setCountOfMoney(MoneyManager.getCountOfMoney() - player.pickaxe.getCost());
            } else if (buttonHandle.buttonHandler(exitButton)) {
                inMarket = false;
            }
        }
    }

    @Override
    public void dispose() {
        bodyTexture.dispose();
        headTexture.dispose();
    }

}
