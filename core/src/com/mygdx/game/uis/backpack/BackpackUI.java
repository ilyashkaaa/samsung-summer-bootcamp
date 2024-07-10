package com.mygdx.game.uis.backpack;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.uis.Button;

import java.util.ArrayList;


public class BackpackUI {
    public Button backpackButton;
    public Button[] backpackSlots;
    public int selectionIndex;
    MyGdxGame myGdxGame;
    Texture backpackBackground;
    Texture backpackChooseButton;
    public boolean backpackOpen;
//    public ArrayList<Texture> blocksInventory;
    public ArrayList<BackpackSlot> slotsInventoryItem;
    public ArrayList<Texture> uniqueItem;


    public BackpackUI(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        selectionIndex = 0;

        backpackOpen = false;
//        blocksInventory = new ArrayList<>();
        uniqueItem = new ArrayList<>();
        slotsInventoryItem = new ArrayList<>();
        backpackBackground = new Texture("textures/buttons/bag_screen/blue_back.png");
        backpackChooseButton = new Texture("textures/buttons/bag_screen/block_button.png");
        backpackButton = new Button(GameResources.BACKPACK_BUTTON_IMG, 700, 400, (int) (160*GameSettings.SCALE), (int) (160*GameSettings.SCALE));
        backpackSlots = new Button[15];

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 5; j++)
                backpackSlots[i * 5 + j] = new Button(backpackChooseButton,
                        -500 + j * 250,
                        180 - i * 180,
                        175 * GameSettings.SCALE, 175 * GameSettings.SCALE);

        backpackSlots[0].changeButtonTexture(GameResources.SELECTED_BLOCK);
    }

    public void cancelSelection(){
        for (Button button : backpackSlots)
            button.changeButtonTexture(backpackChooseButton);
    }

    public void draw(SpriteBatch batch, Vector3 cameraPos) {
        backpackButton.draw(batch, cameraPos);
        if (backpackOpen) {
            float scale = 1.3f;
            batch.draw(backpackBackground,
                    cameraPos.x - backpackBackground.getWidth() * scale / 2 * GameSettings.SCALE,
                    cameraPos.y - backpackBackground.getHeight() * scale / 2 * GameSettings.SCALE,
                    backpackBackground.getWidth() * GameSettings.SCALE * scale,
                    backpackBackground.getHeight() * GameSettings.SCALE * scale);

//            for (int i = 0; i < 5; i++) {
//                for (int j = 0; j < 3; j++) {
//                    batch.draw(backpackChooseButton,
//                            cameraPos.x - myGdxGame.camera.viewportWidth / 2 + i * 250 * GameSettings.SCALE + 350 * GameSettings.SCALE,
//                            cameraPos.y - myGdxGame.camera.viewportHeight / 2 + j * 180 * GameSettings.SCALE + 250 * GameSettings.SCALE,
//                            backpackChooseButton.getWidth() * GameSettings.SCALE,
//                            backpackChooseButton.getHeight() * GameSettings.SCALE);
//                }
//            }

            for (Button button : backpackSlots)
                button.draw(batch, cameraPos);

//            for (int k = 0; k < slotsInventoryItem.size(); k++) {
//                batch.draw(slotsInventoryItem.get(k).texture,
//                        cameraPos.x - myGdxGame.camera.viewportWidth / 2 + k % 5 * 250 * GameSettings.SCALE + 350 * GameSettings.SCALE,
//                        cameraPos.y - myGdxGame.camera.viewportHeight / 2 - (k / 5) * 180 * GameSettings.SCALE + 250 * GameSettings.SCALE + 2 * 180 * GameSettings.SCALE,
//                        backpackChooseButton.getWidth() * GameSettings.SCALE,
//                        backpackChooseButton.getHeight() * GameSettings.SCALE);
//                if (slotsInventoryItem.get(k).showCount)
//                    GameScreen.font.draw(batch, slotsInventoryItem.get(k).counterOfBlock + "",
//                            cameraPos.x - myGdxGame.camera.viewportWidth / 2 + k % 5 * 250 * GameSettings.SCALE + 250 * GameSettings.SCALE,
//                            cameraPos.y - myGdxGame.camera.viewportHeight / 2 + 140 * GameSettings.SCALE - (k / 5) * 180 * GameSettings.SCALE + 250 * GameSettings.SCALE + 2 * 180 * GameSettings.SCALE);
//            }
        }
    }

//    public void handleInput(Vector2 touchPos) {
//
//        if (backpackButton.isPressed()) {
//            backpackOpen = !backpackOpen;
//        }
//    }

//    public void addItemInInventory() {
//        for (int i = 0; i < blocksInventory.size(); i++) {
//            System.out.println(i);
//            if (!uniqueItem.contains(blocksInventory.get(i))) {
//                uniqueItem.add(blocksInventory.get(i));
//                slotsInventory.add(new BackpackSlot(blocksInventory.get(i)));
//                blocksInventory.remove(i);
//            }
//            else {
//                for (int j = 0; j < slotsInventory.size(); j++) {
//                    if (slotsInventory.get(j).texture == blocksInventory.get(i)) {
//                        slotsInventory.get(j).counterOfBlock++;
//                        blocksInventory.remove(i);
//                        break;
//                    }
//                }
//            }
//        }
//    }

    public void addItemInInventory(Texture texture, Class<?> type, boolean showCount){
        if (!uniqueItem.contains(texture)){
            uniqueItem.add(texture);
            slotsInventoryItem.add(new BackpackSlot(texture, type, showCount));
            backpackSlots[slotsInventoryItem.size() - 1].setHasItem(true);
            backpackSlots[slotsInventoryItem.size() - 1].changeItem(texture, 160 * GameSettings.SCALE, 160 * GameSettings.SCALE);
        }
        else {
            for (BackpackSlot backpackSlot : slotsInventoryItem)
                if (backpackSlot.texture == texture) {
                    backpackSlot.counterOfBlock++;
                    break;
                }
        }
    }
    public boolean removeItemFromInventory(Class<?> type){
        boolean ret = false;
        for (BackpackSlot backpackSlot : slotsInventoryItem)
            if (backpackSlot.block == type) {
                backpackSlot.counterOfBlock--;
                if (backpackSlot.counterOfBlock <= 0) {
                    ret = true;
                    backpackSlots[slotsInventoryItem.size() - 1].setHasItem(false);
                    slotsInventoryItem.remove(backpackSlot);
                    uniqueItem.remove(backpackSlot.texture);
                    selectionIndex = 0;
                    cancelSelection();
                    backpackSlots[uniqueItem.size()].setHasItem(false);
                    backpackSlots[0].changeButtonTexture(GameResources.SELECTED_BLOCK);
                    for (int i = 0; i < uniqueItem.size(); i++) {
                        backpackSlots[i].changeItem(uniqueItem.get(i));
                    }
                }
                break;
            }
        return ret;
    }

    public void setItem(int index, Texture texture, Class<?> type, boolean showCount){
        uniqueItem.set(index, texture);
        backpackSlots[index].changeItem(texture);
        slotsInventoryItem.set(0, new BackpackSlot(texture, type, showCount));
    }
}
