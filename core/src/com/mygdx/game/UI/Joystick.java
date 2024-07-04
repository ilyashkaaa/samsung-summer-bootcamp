package com.mygdx.game.UI;

import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;


public class Joystick {
    Vector3 relativeCords;
    private Sprite backGroundTexture;
    private Sprite triggerTexture;
    public Joystick(){
        relativeCords = new Vector3();
        backGroundTexture = GameResources.JOYSTICK_BACKGROUND_TEXTURE;
        triggerTexture = GameResources.JOYSTICK_TRIGGER_TEXTURE;
    }
    public void changeRelativeCords(Vector3 pos){
        relativeCords.set(pos);
    }

    public void draw(SpriteBatch batch, Vector3 cameraPos, Vector3 touchPos){
        Vector3 cords = new Vector3(relativeCords.x + cameraPos.x, relativeCords.y + cameraPos.y, 0);
        batch.draw(backGroundTexture, cords.x - GameSettings.JOYSTICK_SIDE * GameSettings.OBJECT_SCALE / 2,
                cords.y - GameSettings.JOYSTICK_SIDE * GameSettings.OBJECT_SCALE / 2,
                GameSettings.JOYSTICK_SIDE * GameSettings.OBJECT_SCALE,
                GameSettings.JOYSTICK_SIDE * GameSettings.OBJECT_SCALE
        );

        Vector2 border = new Vector2(touchPos.x - cords.x, touchPos.y - cords.y);
        if (border.len() < GameSettings.JOYSTICK_SIDE * GameSettings.OBJECT_SCALE / 2)
            batch.draw(triggerTexture, touchPos.x - GameSettings.JOYSTICK_TRIGGER_SIDE * GameSettings.OBJECT_SCALE / 2,
                    touchPos.y - GameSettings.JOYSTICK_TRIGGER_SIDE * GameSettings.OBJECT_SCALE / 2,
                    GameSettings.JOYSTICK_TRIGGER_SIDE * GameSettings.OBJECT_SCALE,
                    GameSettings.JOYSTICK_TRIGGER_SIDE * GameSettings.OBJECT_SCALE
            );
        else
            batch.draw(triggerTexture,
                    border.setLength(GameSettings.JOYSTICK_SIDE * GameSettings.OBJECT_SCALE / 2).x +
                            cords.x - GameSettings.JOYSTICK_TRIGGER_SIDE * GameSettings.OBJECT_SCALE / 2,
                    border.setLength(GameSettings.JOYSTICK_SIDE * GameSettings.OBJECT_SCALE / 2).y +
                            cords.y - GameSettings.JOYSTICK_TRIGGER_SIDE * GameSettings.OBJECT_SCALE / 2,
                    GameSettings.JOYSTICK_TRIGGER_SIDE * GameSettings.OBJECT_SCALE,
                    GameSettings.JOYSTICK_TRIGGER_SIDE * GameSettings.OBJECT_SCALE
            );
    }
    public Vector2 getDirection(Vector3 touch, Vector3 cameraPos){
        Vector3 cords = new Vector3(relativeCords.x + cameraPos.x, relativeCords.y + cameraPos.y, 0);
        return new Vector2(touch.x - cords.x, touch.y - cords.y);
    }
}
