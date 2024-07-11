package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.LeaderBoardManager;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.SetTextInterface;
import com.mygdx.game.uis.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Leaderboard extends ScreenAdapter {
    MyGdxGame myGdxGame;

    List<String> data;
    static ArrayList<TextView> textView;
    Texture texture;

    float width;
    float height;

    SetTextInterface setTextInterface = string -> {
        if (!Objects.equals(string, ""))
            textView = string;
//        Gdx.app.log("kkkk", "ASKNFAEJK>GKJDE>IGEWKJBG>UEGWKUWGR");

    };

    public Leaderboard(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        data = new ArrayList<>();
        width = GameSettings.SCR_WIDTH * GameSettings.OBJECT_SCALE;
        height = GameSettings.SCR_HEIGHT * GameSettings.OBJECT_SCALE;
        texture = GameResources.LEADERBOARD_BACKGROUND;
        textView = new ArrayList<>();
    }

    @Override
    public void show(){
//        LeaderBoardManager.sendRes("testic", 79);
        LeaderBoardManager.getRes(setTextInterface, -300, 480, 50);
        String text = "";
        for (String string : data){
            text += string + "\n";
        }
//        textView.setText("text");
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
//        textView.setText("tt");
//        textView.draw(myGdxGame.batch, myGdxGame.camera.position, true);

        for (TextView textView1 : textView){
            textView1.draw(myGdxGame.batch, myGdxGame.camera.position, false);
        }

        myGdxGame.batch.end();
    }

}
