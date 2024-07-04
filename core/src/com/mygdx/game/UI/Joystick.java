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
    Vector2 cords;
    public Joystick(){
        cords = new Vector2();
    }
    public void changeCords(Vector2 cords){this.cords.set(cords);}

    public void draw(SpriteBatch batch, Vector3 cameraPos, Vector2 touchPos){
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

    }
    public Vector2 getDirection(Vector2 touch, Vector3 cameraPos){
        return new Vector2(touch.x - cords.x, cords.y - touch.y);
    }
}
