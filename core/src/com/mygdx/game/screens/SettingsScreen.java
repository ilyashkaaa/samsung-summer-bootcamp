package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MemoryManager;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.map.blocks.GenerateMap;
import com.mygdx.game.uis.Button;
import com.mygdx.game.uis.MoneyManager;
import com.mygdx.game.uis.Slider;
import com.mygdx.game.uis.TextView;

public class SettingsScreen extends ScreenAdapter {
    Slider musicVolume;
    Slider overallVolume;
    Slider soundVolume;
    TextView settingsText;
    TextView musicVolumeInt;
    TextView soundVolumeInt;
    TextView overallVolumeInt;
    TextView trashText;

    MyGdxGame myGdxGame;
    GameScreen gameScreen;
    Button backButton;
    Button trashButton;

    public SettingsScreen(MyGdxGame myGdxGame, GameScreen gameScreen) {
        this.myGdxGame = myGdxGame;
        this.gameScreen = gameScreen;
        musicVolume = new Slider(0, 0, 270 * GameSettings.SCALE * 2, 10 * GameSettings.SCALE * 2);
        soundVolume = new Slider(0, -250, 270 * GameSettings.SCALE * 2, 10 * GameSettings.SCALE * 2);
        overallVolume = new Slider(0, 250, 270 * GameSettings.SCALE * 2, 10 * GameSettings.SCALE * 2);
        settingsText = new TextView(MyGdxGame.bitmapFont, 0, 500, "SETTINGS");
        musicVolumeInt = new TextView(MyGdxGame.bitmapFont, 0, 125, "Music volume: " + MemoryManager.loadMusicVolume());
        soundVolumeInt = new TextView(MyGdxGame.bitmapFont, 0, -125, "Sound volume: " + MemoryManager.loadSoundVolume());
        overallVolumeInt = new TextView(MyGdxGame.bitmapFont, 0, 375, "OVERALL MUSIC: " + MemoryManager.loadOverallVolume());
        backButton = new Button(GameResources.BUTTON_IN_PAUSE_AND_SETTINGS, (int) (-GameSettings.SCR_WIDTH/2 + GameResources.BUTTON_IN_PAUSE_AND_SETTINGS.getWidth()), -400, GameResources.BUTTON_IN_PAUSE_AND_SETTINGS.getWidth() * 2 * GameSettings.SCALE,
                GameResources.BUTTON_IN_PAUSE_AND_SETTINGS.getHeight() * 2 * GameSettings.SCALE, myGdxGame.bitmapFont, "return");
        trashButton = new Button(GameResources.TRASH_TEXTURE,
                (int) (GameSettings.SCR_WIDTH / 2 - GameResources.TRASH_TEXTURE.getWidth() * 2 * GameSettings.SCALE),
                (int) (-GameSettings.SCR_HEIGHT / 2 + GameResources.TRASH_TEXTURE.getHeight() * GameSettings.SCALE * 7), GameResources.TRASH_TEXTURE.getWidth() * GameSettings.SCALE,
                GameResources.TRASH_TEXTURE.getHeight() * GameSettings.SCALE );
        trashText = new TextView(MyGdxGame.bitmapFont, (int) (GameSettings.SCR_WIDTH / 2 - GameResources.TRASH_TEXTURE.getWidth() * 11 * GameSettings.SCALE),
                (int) (-GameSettings.SCR_HEIGHT / 2 + GameResources.TRASH_TEXTURE.getHeight() * GameSettings.SCALE * 4), "Clear data");

    }

    @Override
    public void show() {
        myGdxGame.camera.position.set(0, 0, 0);
    }

    @Override
    public void render(float delta) {
        handleInput();
        myGdxGame.audioManager.updateVolume();
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        myGdxGame.batch.begin();
        musicVolumeInt.setText("Music volume: " + MemoryManager.loadMusicVolume());
        soundVolumeInt.setText("Sound volume: " + MemoryManager.loadSoundVolume());
        overallVolumeInt.setText("Overall volume: " + MemoryManager.loadOverallVolume());
        musicVolume.draw(myGdxGame.batch, myGdxGame.camera.position);
        soundVolume.draw(myGdxGame.batch, myGdxGame.camera.position);
        overallVolume.draw(myGdxGame.batch, myGdxGame.camera.position);
        settingsText.draw(myGdxGame.batch, myGdxGame.camera.position);
        musicVolumeInt.draw(myGdxGame.batch, myGdxGame.camera.position);
        soundVolumeInt.draw(myGdxGame.batch, myGdxGame.camera.position);
        overallVolumeInt.draw(myGdxGame.batch, myGdxGame.camera.position);
        backButton.draw(myGdxGame.batch, myGdxGame.camera.position);
        trashButton.draw(myGdxGame.batch, myGdxGame.camera.position);
        trashText.draw(myGdxGame.batch, myGdxGame.camera.position);
        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            if (!myGdxGame.isStillTouching) {
                myGdxGame.isStillTouching = true;
                if (gameScreen.buttonHandle.buttonHandler(backButton)) {
                    MemoryManager.saveMusicSettings(musicVolume.getValue(myGdxGame.camera.position));
                    MemoryManager.saveSoundSettings(soundVolume.getValue(myGdxGame.camera.position));
                    MemoryManager.saveOverallVolumeSettings(overallVolume.getValue(myGdxGame.camera.position));
                    if (myGdxGame.returnToPause) {
                        myGdxGame.setScreen(myGdxGame.pauseScreen);
                    } else {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }

                }
                if (gameScreen.buttonHandle.buttonHandler(trashButton)) {
                    MemoryManager.saveMap(null);
                    gameScreen.generateMap = new GenerateMap();
                    gameScreen.blocksCollision.generateCollision(gameScreen.generateMap.mapArray);
                    MemoryManager.savePlayerPos(new Vector2(GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE * 20,
                            ((GameSettings.MAP_HEIGHT + 1) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE)));
                    gameScreen.player.getBody().setTransform(MemoryManager.getPlayerPos(), 0);
                    MemoryManager.savePoints(0);
                    MoneyManager.countOfMoney = 0;
                    MemoryManager.saveMoney(0);
                    MoneyManager.points = 0;
                }
            } else {
                myGdxGame.isStillTouching = false;
            }
        }
    }
}
