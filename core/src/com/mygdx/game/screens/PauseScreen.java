package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MemoryManager;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.uis.Button;
import com.mygdx.game.uis.MoneyManager;
import com.mygdx.game.uis.TextView;


public class PauseScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    Button continueButton;
    Button settingsButton;
    Button menuButton;
    TextView pauseText;
    Boolean returnToPause;

    Vector3 cameraPos;
    GameScreen gameScreen;


    public PauseScreen(MyGdxGame myGdxGame, GameScreen gameScreen, Vector3 cameraPos) {
        this.myGdxGame = myGdxGame;
        this.gameScreen = gameScreen;
        this.cameraPos = cameraPos;
        pauseText = new TextView(myGdxGame.bitmapFont, 0, 400, "PAUSE");
        menuButton = new Button(GameResources.BUTTON_IN_PAUSE_AND_SETTINGS,
                0,
                -200,
                GameResources.BUTTON_IN_PAUSE_AND_SETTINGS.getWidth()*2*GameSettings.SCALE,
                GameResources.BUTTON_IN_PAUSE_AND_SETTINGS.getHeight()*2*GameSettings.SCALE,
                myGdxGame.bitmapFont, "menu");

        settingsButton = new Button(GameResources.BUTTON_IN_PAUSE_AND_SETTINGS,
                0,
                0,
                GameResources.BUTTON_IN_PAUSE_AND_SETTINGS.getWidth()*2*GameSettings.SCALE,
                GameResources.BUTTON_IN_PAUSE_AND_SETTINGS.getHeight()*2*GameSettings.SCALE,
                myGdxGame.bitmapFont, "settings");
        continueButton = new Button(GameResources.BUTTON_IN_PAUSE_AND_SETTINGS,
                0,
                200,
                GameResources.BUTTON_IN_PAUSE_AND_SETTINGS.getWidth()*2*GameSettings.SCALE,
                GameResources.BUTTON_IN_PAUSE_AND_SETTINGS.getHeight()*2*GameSettings.SCALE,
                myGdxGame.bitmapFont, "continue");

    }

    @Override
    public void show(){
        myGdxGame.camera.position.set(0, 0, 0);
    }

    @Override
    public void render(float delta) {

        handleInput();
        myGdxGame.camera.update();
        //myGdxGame.camera.setToOrtho(false, GameSettings.SCR_WIDTH,GameSettings.SCR_HEIGHT);
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        myGdxGame.batch.begin();
        continueButton.draw(myGdxGame.batch, cameraPos);
        settingsButton.draw(myGdxGame.batch, cameraPos);
        menuButton.draw(myGdxGame.batch, cameraPos);
        pauseText.draw(myGdxGame.batch, myGdxGame.camera.position);
        myGdxGame.batch.end();
    }
    private void handleInput() {
        if (Gdx.input.isTouched()) {
            if (!myGdxGame.isStillTouching) {
                myGdxGame.isStillTouching = true;
                if (gameScreen.buttonHandle.buttonHandler(continueButton)) {
                    myGdxGame.setScreen(gameScreen);
                }
                if (gameScreen.buttonHandle.buttonHandler(settingsButton)) {
                    myGdxGame.returnToPause = true;
                    myGdxGame.setScreen(myGdxGame.settingsScreen);
                }
                if (gameScreen.buttonHandle.buttonHandler(menuButton)) {
                    myGdxGame.returnToPause = false;

                    MemoryManager.saveMoney(MoneyManager.countOfMoney);
                    MemoryManager.savePoints(MoneyManager.points);
                    MoneyManager.countOfMoney = MemoryManager.getMoney();
                    MoneyManager.points = MemoryManager.getPoints();
                    System.out.println(MoneyManager.countOfMoney);
                    System.out.println(MemoryManager.getMoney());
                    MemoryManager.saveMap(gameScreen.generateMap.mapArray);
                    MemoryManager.savePlayerPos(gameScreen.player.getBody().getPosition());
                    myGdxGame.setScreen(myGdxGame.menuScreen);
                }
            }
        } else {
            myGdxGame.isStillTouching = false;
        }
    }

}
