package com.mygdx.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
    }
    @Override
    public void render(float delta) {
        myGdxGame.stepWorld();
    }

}
