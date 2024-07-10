package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MovingBackground;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.uis.Button;

public class MenuScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    Button playButton;
    Button settingsButton;
    Button exitButton;
    Button leaderButton;
    MovingBackground movingBackground;

    // jumpButton = new Button(GameResources.JUMP_BUTTON, 700, -300, (int) (200 * GameSettings.OBJECT_SCALE));
    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        myGdxGame.returnToPause = false;
        playButton = new Button(GameResources.MENU_BUTTON, 0, 0, 650*GameSettings.OBJECT_SCALE, 85*GameSettings.OBJECT_SCALE, myGdxGame.bitmapFont, "play");
        leaderButton = new Button(GameResources.MENU_BUTTON, 0, -100, 650*GameSettings.OBJECT_SCALE, 85*GameSettings.OBJECT_SCALE, myGdxGame.bitmapFont, "leaders");
        settingsButton = new Button(GameResources.MENU_BUTTON, 0, -200, 650*GameSettings.OBJECT_SCALE, 85*GameSettings.OBJECT_SCALE, myGdxGame.bitmapFont, "settings");
        exitButton = new Button(GameResources.MENU_BUTTON, 0, -300, 650*GameSettings.OBJECT_SCALE, 85*GameSettings.OBJECT_SCALE,myGdxGame.bitmapFont, "exit");

        movingBackground = new MovingBackground(GameResources.MENU_SCREEN);



    }

    @Override
    public void render(float delta) {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        myGdxGame.batch.begin();

        movingBackground.draw(myGdxGame.batch, myGdxGame);
        playButton.draw(myGdxGame.batch, myGdxGame.camera.position);
        leaderButton.draw(myGdxGame.batch, myGdxGame.camera.position);
        settingsButton.draw(myGdxGame.batch, myGdxGame.camera.position);
        exitButton.draw(myGdxGame.batch, myGdxGame.camera.position);
        myGdxGame.batch.end();
        movingBackground.move();

        if (Gdx.input.isTouched()) {
            if (!myGdxGame.isStillTouching) {
                myGdxGame.isStillTouching = true;
                if (myGdxGame.gameScreen.buttonHandler(playButton)) {
                    myGdxGame.setScreen(myGdxGame.gameScreen);
                } else if (myGdxGame.gameScreen.buttonHandler(leaderButton)) {
                    //myGdxGame.setScreen(myGdxGame.);
                } else if (myGdxGame.gameScreen.buttonHandler(settingsButton)) {
                    myGdxGame.setScreen(myGdxGame.settingsScreen);

                } else if (myGdxGame.gameScreen.buttonHandler(exitButton)) {
                    Gdx.app.exit();
                }
            }
        } else {
            myGdxGame.isStillTouching = false;
        }
    }
}