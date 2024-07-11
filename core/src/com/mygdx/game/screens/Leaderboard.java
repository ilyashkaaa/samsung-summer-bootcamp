package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.ButtonHandlerInterface;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.LeaderBoardManager;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.SetTextInterface;
import com.mygdx.game.uis.Button;
import com.mygdx.game.uis.Joystick;
import com.mygdx.game.uis.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Leaderboard extends ScreenAdapter {
    MyGdxGame myGdxGame;

    List<String> data;
    static ArrayList<TextView> textView;
    Button exitButton;
    Texture texture;

    float width;
    float height;

    ButtonHandlerInterface buttonHandle = button -> {
        for (int i = 0; i <= Joystick.countOfTouching(); i++) {
            if (button.isPressed(new Vector2(Gdx.input.getX(i), Gdx.input.getY(i)))) {
                return true;
            }
        }
        return false;
    };

    SetTextInterface setTextInterface = string -> {
        if (!Objects.equals(string, ""))
            textView = string;
//        Gdx.app.log("kkkk", "ASKNFAEJK>GKJDE>IGEWKJBG>UEGWKUWGR");

    };

    public Leaderboard(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        exitButton = new Button(GameResources.BUTTON_IN_PAUSE_AND_SETTINGS, (int) (-GameSettings.SCR_WIDTH/2 + GameResources.BUTTON_IN_PAUSE_AND_SETTINGS.getWidth()), -400, GameResources.BUTTON_IN_PAUSE_AND_SETTINGS.getWidth() * 2 * GameSettings.SCALE,
                GameResources.BUTTON_IN_PAUSE_AND_SETTINGS.getHeight() * 2 * GameSettings.SCALE, myGdxGame.bitmapFont, "return");
        data = new ArrayList<>();
        width = GameSettings.SCR_WIDTH * GameSettings.OBJECT_SCALE;
        height = GameSettings.SCR_HEIGHT * GameSettings.OBJECT_SCALE;
        texture = GameResources.LEADERBOARD_BACKGROUND;
        textView = new ArrayList<>();
    }

    @Override
    public void show(){
        LeaderBoardManager.getRes(setTextInterface, -300, 475, 100);
    }

    @Override
    public void render(float delta){
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        myGdxGame.batch.begin();

        myGdxGame.batch.draw(texture,
                myGdxGame.camera.position.x - width / 2f,
                myGdxGame.camera.position.y - height / 2f,
                width, height
        );

        exitButton.draw(myGdxGame.batch, myGdxGame.camera.position);

        if (buttonHandle.buttonHandler(exitButton))
            myGdxGame.setScreen(myGdxGame.menuScreen);

        for (TextView textView1 : textView){
            textView1.draw(myGdxGame.batch, myGdxGame.camera.position, false);
        }

        myGdxGame.batch.end();
    }

}
