package com.mygdx.game.uis;

import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.PlayerStates;


public class Joystick {
    Vector2 cords;
    private final float fixedXNormal = (GameSettings.SCR_WIDTH + GameSettings.JOYSTICK_SIDE) / 2f;
    private final float fixedYNormal = (GameSettings.SCR_HEIGHT - GameSettings.JOYSTICK_SIDE) / 2f;
    private final float fixedXTrigger = (GameSettings.SCR_WIDTH + GameSettings.JOYSTICK_TRIGGER_SIDE) / 2f;
    private final float fixedYTrigger = (GameSettings.SCR_HEIGHT - GameSettings.JOYSTICK_TRIGGER_SIDE) / 2f;
    private final float joystickSideDevOn2 = GameSettings.JOYSTICK_SIDE / 2f;

    public boolean keepTouching;
    public Joystick(){
        cords = new Vector2();
    }
    public void changeCords(Vector2 cords){this.cords.set(cords);}

    public Vector2 selectedBlock = new Vector2();;

    public void draw(SpriteBatch batch, Vector3 cameraPos, Vector2 touchPos){
        batch.draw(GameResources.JOYSTICK_BACKGROUND_TEXTURE,
                cameraPos.x - (fixedXNormal - cords.x) * SCALE,
                cameraPos.y + (fixedYNormal - cords.y) * SCALE,
                GameSettings.JOYSTICK_SIDE * SCALE,
                GameSettings.JOYSTICK_SIDE * SCALE
        );

        if ((touchPos.cpy().sub(cords)).len() < joystickSideDevOn2)
            batch.draw(GameResources.JOYSTICK_TRIGGER_TEXTURE,
                    cameraPos.x - (fixedXTrigger - touchPos.x) * SCALE,
                    cameraPos.y + (fixedYTrigger - touchPos.y) * SCALE,
                    GameSettings.JOYSTICK_TRIGGER_SIDE * SCALE, GameSettings.JOYSTICK_TRIGGER_SIDE * SCALE
            );
        else
            batch.draw(GameResources.JOYSTICK_TRIGGER_TEXTURE,
                    cameraPos.x - (fixedXTrigger - touchPos.cpy().sub(cords).setLength(joystickSideDevOn2).x - cords.x) * SCALE,
                    cameraPos.y + (fixedYTrigger - touchPos.cpy().sub(cords).setLength(joystickSideDevOn2).y - cords.y) * SCALE,
                    GameSettings.JOYSTICK_TRIGGER_SIDE * SCALE, GameSettings.JOYSTICK_TRIGGER_SIDE * SCALE
            );

    }
    public Vector2 getDirection(Vector2 touch){
        return new Vector2(touch.x - cords.x, cords.y - touch.y);
    }


    public boolean joystickUpdate(Player player){
        Vector2 touch = new Vector2(Gdx.input.getX(indexJoystick()),
                Gdx.input.getY(indexJoystick())
        );
//        Vector2 selectedBlock = new Vector2();

        if (Gdx.input.isTouched(indexJoystick()) && touch.x <= GameSettings.SCR_WIDTH / 2f) {
            if (!keepTouching)
                changeCords(touch);
             selectedBlock = player.setMoveVector(getDirection(touch));
            keepTouching = true;
            if (player.playerState == PlayerStates.STANDING && !player.isJumping) {
                player.playerState = PlayerStates.WALKING;
            }
        } else {
            if (player.playerState == PlayerStates.WALKING && !player.isJumping) {
                player.playerState = PlayerStates.STANDING;
            }
            keepTouching = false;
        }
//        return selectedBlock;
        return keepTouching;
    }

    public int countOfTouching() {
        int i = 0;
        while (i < Gdx.input.getMaxPointers()) {
            i++;
            if (Gdx.input.getPressure(i) == 0) break;
        }
        return i - 1;
    }

    public int indexJoystick() {

        int returned = 0;
        for (int i = 0; i < countOfTouching() + 1; i++) {
            if (Gdx.input.getX(i) <= GameSettings.SCR_WIDTH / 2) {
                returned = i;
                break;
            }
        }
        return returned;
    }
}
