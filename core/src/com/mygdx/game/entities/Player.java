package com.mygdx.game.entities;

import static com.mygdx.game.GameSettings.PLAYER_HEIGHT;
import static com.mygdx.game.GameSettings.PLAYER_WIDTH;
import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

public class Player extends GameEntity {
    private final int speed = 100;
    PlayerStates playerState;
    int frameCounterForHead;
    int frameCounterForWalking;
    int frameCounterForDownDigging;
    int frameMultiplierForHead = 20;
    int frameMultiplierForWalking = 4;
    int frameMultiplierForDigging = 4;
    boolean movingLeft;

    public Player(float width, float height, Body body, MyGdxGame myGdxGame) {
        super(width, height, body, myGdxGame);
//        this.speed = 4f;
        playerState = PlayerStates.STANDING;
        movingLeft = false;
        body.setLinearDamping(10);


    }

    @Override
    public void update() {

    }

//    @Override
//    public void update() {
//        playerState = PlayerStates.STANDING;
//
//        velY = 0;
//        velX = 0;
//        //Vector3 position = myGdxGame.camera.position;
////        position.x = x;
////        position.y = y;
////        myGdxGame.camera.position.set(position);
//        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            velY += speed;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//            playerState = PlayerStates.LEFT_WALKING;
//            if (!movingLeft) {
//                for (int i = 0; i < 8; i++) {
//                    GameResources.PLAYER_WALKING_TEXTURES[i].flip(true, false);
//                }
//                for (int i = 0; i < 6; i++) {
//                    GameResources.PLAYER_DOWN_DIGGING_TEXTURES[i].flip(true, false);
//                }
//
//                GameResources.PLAYER_STANDING_TEXTURE.flip(true, false);
//                GameResources.PLAYER_HEAD_TEXTURE.flip(true, false);
//                movingLeft = true;
//            }
//            velX -= speed;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            velY -= speed;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//            playerState = PlayerStates.LEFT_WALKING;
//            if (movingLeft) {
//                for (int i = 0; i < 8; i++) {
//                    GameResources.PLAYER_WALKING_TEXTURES[i].flip(true, false);
//                }
//                GameResources.PLAYER_STANDING_TEXTURE.flip(true, false);
//                GameResources.PLAYER_HEAD_TEXTURE.flip(true, false);
//                movingLeft = false;
//            }
//            velX += speed;
//        }
//        x = getX() + velX;
//        y = getY() + velY;
//        //body.applyForceToCenter(0, -10, true);
//
////        body.setLinearVelocity(velX, velY);
//        setX(x);
//        setY(y);
////        body.setTransform(getX(), getY(), 0);
//
////        body.applyForceToCenter();
////        body.setTransform(x + body.getLinearVelocity().x, y + body.getLinearVelocity().y, 0);
//        //  System.out.println(x + " " + y);

//    }

    @Override
    public void render(SpriteBatch batch) {

    }

//    public int getX() {
//        return (int) (body.getPosition().x / SCALE);
//    }
//
//    public int getY() {
//        return (int) (body.getPosition().y / SCALE);
//    }
//
//    public void setX(float x) {
//        body.setTransform(x * SCALE, body.getPosition().y, 0);
//    }
//
//    public void setY(float y) {
//        body.setTransform(body.getPosition().x, y * SCALE, 0);
//    }

    public void updateCamera() {
        Vector3 position = myGdxGame.camera.position;
        position.x = body.getPosition().x;
        position.y = body.getPosition().y;
        myGdxGame.camera.position.set(position);

    }
    public void setMoveVector (Vector2 moveVector){
        //body.setLinearVelocity(moveVector.setLength(100));
        body.applyForceToCenter(moveVector, true);

//        body.setTransform((moveVector.x + getX()) * SCALE, (moveVector.y + getY()) * SCALE, 0);
        updateCamera();
//        System.out.println(body.getPosition());
    }

    public void draw(SpriteBatch batch) {
        if (frameCounterForHead++ ==2 * frameMultiplierForHead - 1) frameCounterForHead = 0;
        batch.draw(GameResources.PLAYER_HEAD_TEXTURE,
                body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2,
                body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2 - frameCounterForHead /frameMultiplierForHead* GameSettings.OBJECT_SCALE*5,
                PLAYER_WIDTH * GameSettings.OBJECT_SCALE,
                PLAYER_HEIGHT * GameSettings.OBJECT_SCALE
        );


        switch (playerState) {
            case STANDING:
                GameResources.PLAYER_STANDING_TEXTURE.setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_STANDING_TEXTURE.setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_STANDING_TEXTURE.draw(batch);
                break;
            case RIGHT_WALKING:
                if (frameCounterForWalking++ == GameResources.PLAYER_WALKING_TEXTURES.length * frameMultiplierForWalking - 1) frameCounterForWalking = 0;
                GameResources.PLAYER_WALKING_TEXTURES[frameCounterForWalking / frameMultiplierForWalking].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_WALKING_TEXTURES[frameCounterForWalking / frameMultiplierForWalking].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_WALKING_TEXTURES[frameCounterForWalking / frameMultiplierForWalking].draw(batch);
                break;
            case DOWN_DIGGING:
                if (frameCounterForDownDigging++ == GameResources.PLAYER_WALKING_TEXTURES.length * frameMultiplierForDigging - 1) frameCounterForDownDigging = 0;






        }
    }

}
