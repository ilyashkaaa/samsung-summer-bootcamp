package com.mygdx.game.UI;

import static com.mygdx.game.GameSettings.OBJECT_SCALE;
import static com.mygdx.game.GameSettings.SCALE;
import static com.mygdx.game.GameSettings.SCR_HEIGHT;

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
    Vector2 cords;
    private Sprite backGroundTexture;
    private Sprite triggerTexture;
    public Joystick(){
        relativeCords = new Vector3();
        cords = new Vector2();
        backGroundTexture = GameResources.JOYSTICK_BACKGROUND_TEXTURE;
        triggerTexture = GameResources.JOYSTICK_TRIGGER_TEXTURE;
    }
    public void changeRelativeCords(Vector3 pos){
        relativeCords.set(pos);
    }
    public void changeCords(Vector2 cords){this.cords.set(cords);}

    public void draw(SpriteBatch batch, Vector3 cameraPos, Vector2 touchPos){
        System.out.println(((touchPos.cpy().sub(cords)).setLength(GameSettings.JOYSTICK_SIDE / 2).y * SCALE + cameraPos.x) + "");
        batch.draw(GameResources.JOYSTICK_BACKGROUND_TEXTURE,
                cameraPos.x - ((GameSettings.SCR_WIDTH + GameSettings.JOYSTICK_SIDE) / 2 - cords.x) * SCALE,
                cameraPos.y + ((GameSettings.SCR_HEIGHT - GameSettings.JOYSTICK_SIDE) / 2 - cords.y) * SCALE,
                GameSettings.JOYSTICK_SIDE * SCALE,
                GameSettings.JOYSTICK_SIDE * SCALE
        );

        if ((touchPos.cpy().sub(cords)).len() < GameSettings.JOYSTICK_SIDE / 2)
            batch.draw(GameResources.JOYSTICK_TRIGGER_TEXTURE,
                    cameraPos.x - ((GameSettings.SCR_WIDTH + GameSettings.JOYSTICK_TRIGGER_SIDE) / 2 - touchPos.x) * SCALE,
                    cameraPos.y + ((GameSettings.SCR_HEIGHT - GameSettings.JOYSTICK_TRIGGER_SIDE) / 2 - touchPos.y) * SCALE,
                    GameSettings.JOYSTICK_TRIGGER_SIDE * SCALE, GameSettings.JOYSTICK_TRIGGER_SIDE * SCALE
            );
        else
            batch.draw(GameResources.JOYSTICK_TRIGGER_TEXTURE,
                    cameraPos.x - ((GameSettings.SCR_WIDTH + GameSettings.JOYSTICK_TRIGGER_SIDE) / 2 -
                            touchPos.cpy().sub(cords).setLength(GameSettings.JOYSTICK_SIDE / 2).x/* +
                            GameSettings.JOYSTICK_SIDE / 2*/ - cords.x) * SCALE,
                    cameraPos.y + ((GameSettings.SCR_HEIGHT - GameSettings.JOYSTICK_TRIGGER_SIDE) / 2 -
                            touchPos.cpy().sub(cords).setLength(GameSettings.JOYSTICK_SIDE / 2).y/* +
                            GameSettings.JOYSTICK_SIDE / 2*/ - cords.y) * SCALE,
                    GameSettings.JOYSTICK_TRIGGER_SIDE * SCALE, GameSettings.JOYSTICK_TRIGGER_SIDE * SCALE
            );

//        if (border.len() < GameSettings.JOYSTICK_SIDE * GameSettings.OBJECT_SCALE / 2)
//            batch.draw(triggerTexture, touchPos.x - GameSettings.JOYSTICK_TRIGGER_SIDE * GameSettings.OBJECT_SCALE / 2,
//                    touchPos.y - GameSettings.JOYSTICK_TRIGGER_SIDE * GameSettings.OBJECT_SCALE / 2,
//                    GameSettings.JOYSTICK_TRIGGER_SIDE * GameSettings.OBJECT_SCALE,
//                    GameSettings.JOYSTICK_TRIGGER_SIDE * GameSettings.OBJECT_SCALE
//            );
//        else
//            batch.draw(triggerTexture,
//                    border.setLength(GameSettings.JOYSTICK_SIDE * GameSettings.OBJECT_SCALE / 2).x +
//                            cords.x - GameSettings.JOYSTICK_TRIGGER_SIDE * GameSettings.OBJECT_SCALE / 2,
//                    border.setLength(GameSettings.JOYSTICK_SIDE * GameSettings.OBJECT_SCALE / 2).y +
//                            cords.y - GameSettings.JOYSTICK_TRIGGER_SIDE * GameSettings.OBJECT_SCALE / 2,
//                    GameSettings.JOYSTICK_TRIGGER_SIDE * GameSettings.OBJECT_SCALE,
//                    GameSettings.JOYSTICK_TRIGGER_SIDE * GameSettings.OBJECT_SCALE
//            );
    }
    public Vector2 getDirection(Vector3 touch, Vector3 cameraPos){
        Vector3 cords = new Vector3(relativeCords.x + cameraPos.x, relativeCords.y + cameraPos.y, 0);
        return new Vector2(touch.x - cords.x, touch.y - cords.y);
    }
    public Vector2 getDirection(Vector2 touch, Vector3 cameraPos){
        return new Vector2(touch.x - cords.x, cords.y - touch.y);
    }
}
