package com.mygdx.game.uis.backpack;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.uis.Button;

import java.util.ArrayList;


public class BackpackUI {
    public Button backpackButton;
    MyGdxGame myGdxGame;
    Texture backpackBackground;
    Texture backpackChooseButton;
    public boolean backpackOpen;
    public ArrayList<Texture> blocksInventory;
    public ArrayList<BackpackSlot> slotsInventory;
    public ArrayList<Texture> uniqueBlocks;


    public BackpackUI(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        backpackOpen = false;
        blocksInventory = new ArrayList<>();
        uniqueBlocks = new ArrayList<>();
        slotsInventory = new ArrayList<>();
        backpackBackground = new Texture("textures/buttons/backpack_background.png");
        backpackChooseButton = new Texture("textures/buttons/button_background_square.png");
        backpackButton = new Button(GameResources.BACKPACK_BUTTON_IMG_PATH, 700, 400, (int) (160*GameSettings.SCALE), (int) (160*GameSettings.SCALE));

    }

    public void draw(SpriteBatch batch) {
        backpackButton.draw(batch, myGdxGame.camera.position);
        if (backpackOpen) {
            batch.draw(backpackBackground,
                    myGdxGame.camera.position.x - myGdxGame.camera.viewportWidth / 2 + (float) (GameSettings.SCR_WIDTH - backpackBackground.getWidth()) / 2 * GameSettings.SCALE,
                    myGdxGame.camera.position.y - myGdxGame.camera.viewportHeight / 2 + (float) (GameSettings.MAP_HEIGHT - backpackBackground.getHeight()) / 2 * GameSettings.SCALE,
                    backpackBackground.getWidth() * GameSettings.SCALE,
                    backpackBackground.getHeight() * GameSettings.SCALE);
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 3; j++) {
                    batch.draw(backpackChooseButton,
                            myGdxGame.camera.position.x - myGdxGame.camera.viewportWidth / 2 + i * 250 * GameSettings.SCALE + 350 * GameSettings.SCALE,
                            myGdxGame.camera.position.y - myGdxGame.camera.viewportHeight / 2 + j * 180 * GameSettings.SCALE + 250 * GameSettings.SCALE,
                            backpackChooseButton.getWidth() * GameSettings.SCALE,
                            backpackChooseButton.getHeight() * GameSettings.SCALE);
                }
            }
            for (int k = 0; k < slotsInventory.size(); k++) {
                batch.draw(slotsInventory.get(k).texture,
                        myGdxGame.camera.position.x - myGdxGame.camera.viewportWidth / 2 + k % 5 * 250 * GameSettings.SCALE + 350 * GameSettings.SCALE,
                        myGdxGame.camera.position.y - myGdxGame.camera.viewportHeight / 2 - (k / 5) * 180 * GameSettings.SCALE + 250 * GameSettings.SCALE + 2 * 180 * GameSettings.SCALE,
                        backpackChooseButton.getWidth() * GameSettings.SCALE,
                        backpackChooseButton.getHeight() * GameSettings.SCALE);
                GameScreen.font.draw(batch, slotsInventory.get(k).counterOfBlock + "",
                        myGdxGame.camera.position.x - myGdxGame.camera.viewportWidth / 2 + k % 5 * 250 * GameSettings.SCALE + 250 * GameSettings.SCALE,
                        myGdxGame.camera.position.y - myGdxGame.camera.viewportHeight / 2 + 140*GameSettings.SCALE - (k / 5) * 180 * GameSettings.SCALE + 250 * GameSettings.SCALE + 2 * 180 * GameSettings.SCALE);
            }
        }
    }

//    public void handleInput(Vector2 touchPos) {
//
//        if (backpackButton.isPressed()) {
//            backpackOpen = !backpackOpen;
//        }
//    }

    public void addBlockToInventory() {
        for (int i = 0; i < blocksInventory.size(); i++) {
            if (!uniqueBlocks.contains(blocksInventory.get(i))) {
                uniqueBlocks.add(blocksInventory.get(i));
                slotsInventory.add(new BackpackSlot(blocksInventory.get(i)));
                blocksInventory.remove(i);
            } else {
                for (int j = 0; j < slotsInventory.size(); j++) {
                    if (slotsInventory.get(j).texture == blocksInventory.get(i)) {
                        slotsInventory.get(j).counterOfBlock++;
                        blocksInventory.remove(i);
                        break;
                    }
                }
            }
        }
    }

}
