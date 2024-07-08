package com.mygdx.game.uis;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;

public class CameraMovement {
    MyGdxGame myGdxGame;
    public CameraMovement(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
    }
    public void move (Vector2 playerPos) {
        float x = myGdxGame.camera.position.x - (myGdxGame.camera.position.x - playerPos.x) / 64f;
        float y = myGdxGame.camera.position.y - (myGdxGame.camera.position.y - playerPos.y) / 64f;
        myGdxGame.camera.position.set(x, y, 0);
    }
}
