package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MemoryManager;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.uis.Button;
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

    MyGdxGame myGdxGame;
    GameScreen gameScreen;
    Button backButton;

    public SettingsScreen(MyGdxGame myGdxGame, GameScreen gameScreen) {
        this.myGdxGame = myGdxGame;
        this.gameScreen = gameScreen;
         musicVolume = new Slider(0, 0, 270*GameSettings.SCALE*2, 10*GameSettings.SCALE*2);
         soundVolume = new Slider(0, -250, 270*GameSettings.SCALE*2, 10*GameSettings.SCALE*2);
         overallVolume = new Slider(0, 250, 270*GameSettings.SCALE*2, 10*GameSettings.SCALE*2);
         settingsText = new TextView(myGdxGame.bitmapFont, 0, 500, "SETTINGS");
         musicVolumeInt = new TextView(myGdxGame.bitmapFont, 0, 125, "Music volume: " + MemoryManager.loadMusicVolume());
         soundVolumeInt = new TextView(myGdxGame.bitmapFont, 0, -125, "Sound volume: " + MemoryManager.loadSoundVolume());
         overallVolumeInt = new TextView(myGdxGame.bitmapFont, 0, 375, "OVERALL MUSIC: " + MemoryManager.loadOverallVolume());
         backButton = new Button(GameResources.BUTTON_IN_PAUSE_AND_SETTINGS, 0, -400, GameResources.BUTTON_IN_PAUSE_AND_SETTINGS.getWidth()*2*GameSettings.SCALE,
                 GameResources.BUTTON_IN_PAUSE_AND_SETTINGS.getHeight()*2*GameSettings.SCALE, myGdxGame.bitmapFont, "return");
    }

    @Override
    public void show(){
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
        MemoryManager.saveMusicSettings(musicVolume.getValue(myGdxGame.camera.position));
        MemoryManager.saveSoundSettings(soundVolume.getValue(myGdxGame.camera.position));
        MemoryManager.saveOverallVolumeSettings(overallVolume.getValue(myGdxGame.camera.position));
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
        myGdxGame.batch.end();
    }
    private void handleInput() {
        if (Gdx.input.isTouched()) {
            if (!myGdxGame.isStillTouching) {
                myGdxGame.isStillTouching = true;
                if (gameScreen.buttonHandle.buttonHandler(backButton)) {
                    if (myGdxGame.returnToPause) {
                        myGdxGame.setScreen(myGdxGame.pauseScreen);
                    } else {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                }
            }
        } else {
            myGdxGame.isStillTouching = false;
        }
    }
}
