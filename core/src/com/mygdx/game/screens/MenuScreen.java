package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
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
    MovingBackground movingBackground;
    // jumpButton = new Button(GameResources.JUMP_BUTTON, 700, -300, (int) (200 * GameSettings.OBJECT_SCALE));
    public MenuScreen (MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        playButton = new Button(GameResources.MENU_BUTTON, 0, 0, 260*GameSettings.OBJECT_SCALE, 85*GameSettings.OBJECT_SCALE);
        settingsButton = new Button(GameResources.MENU_BUTTON, 0, -100, 260*GameSettings.OBJECT_SCALE, 85*GameSettings.OBJECT_SCALE);
        exitButton = new Button(GameResources.MENU_BUTTON, 0, -200, 260*GameSettings.OBJECT_SCALE, 85*GameSettings.OBJECT_SCALE);

        movingBackground = new MovingBackground(GameResources.SKY);



    }
    @Override
    public void render(float delta){
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        myGdxGame.batch.begin();

        movingBackground.draw(myGdxGame.batch, myGdxGame);
        playButton.draw(myGdxGame.batch, myGdxGame.camera.position);
        settingsButton.draw(myGdxGame.batch, myGdxGame.camera.position);
        exitButton.draw(myGdxGame.batch, myGdxGame.camera.position);

        myGdxGame.batch.end();
        movingBackground.move();
    }



    private void handleInput() {}
}