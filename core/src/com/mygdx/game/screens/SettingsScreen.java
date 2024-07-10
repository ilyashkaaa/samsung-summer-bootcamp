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
         musicVolume = new Slider(0, 0, 270*GameSettings.SCALE, 10*GameSettings.SCALE);
         soundVolume = new Slider(0, -100, 270*GameSettings.SCALE, 10*GameSettings.SCALE);
         overallVolume = new Slider(0, 100, 270*GameSettings.SCALE, 10*GameSettings.SCALE);
         settingsText = new TextView(myGdxGame.bitmapFont, 0, 400, "SETTINGS");
         musicVolumeInt = new TextView(myGdxGame.bitmapFont, -GameResources.SLIDER_TEXTURE.getWidth()*GameSettings.SCALE/2f-250, 0, MemoryManager.loadMusicVolume() + "");
         soundVolumeInt = new TextView(myGdxGame.bitmapFont, -GameResources.SLIDER_TEXTURE.getWidth()*GameSettings.SCALE/2f-250, -100, MemoryManager.loadSoundVolume() + "");
         overallVolumeInt = new TextView(myGdxGame.bitmapFont, -GameResources.SLIDER_TEXTURE.getWidth()*GameSettings.SCALE/2f-250, 100, MemoryManager.loadOverallVolume() + "");
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
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        myGdxGame.batch.begin();
        MemoryManager.saveMusicSettings(musicVolume.getValue(myGdxGame.camera.position));
        MemoryManager.saveSoundSettings(soundVolume.getValue(myGdxGame.camera.position));
        MemoryManager.saveOverallVolumeSettings(overallVolume.getValue(myGdxGame.camera.position));
        musicVolumeInt.setText(MemoryManager.loadMusicVolume()+"");
        soundVolumeInt.setText(MemoryManager.loadSoundVolume()+"");
        overallVolumeInt.setText(MemoryManager.loadOverallVolume()+"");
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
            if (gameScreen.buttonHandler(backButton)) {
                myGdxGame.setScreen(myGdxGame.pauseScreen);
            }
        }
    }
}
