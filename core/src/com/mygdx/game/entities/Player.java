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
    private final int speedX = 25;
    private boolean canJump;
    PlayerStates playerState;
    int frameCounter = 0;
    int frameMultiplierForHead = 20;
    int frameMultiplierForWalking = 4;
    int frameMultiplierForDigging = 4;



    public Player(float width, float height, Body body, MyGdxGame myGdxGame) {
        super(width, height, body, myGdxGame);
        canJump = false;
//        this.speed = 4f;
        playerState = PlayerStates.STANDING;
        body.setLinearDamping(1);


    }

    @Override
    public void update() {

    }
    @Override
    public void render(SpriteBatch batch) {

    }


    public void updateCamera() {
        Vector3 position = myGdxGame.camera.position;
        position.x = body.getPosition().x;
        position.y = body.getPosition().y;
        myGdxGame.camera.position.set(position);

    }
//    public void checkVelocityY(){
//        if (Math.abs(body.getLinearVelocity().y) > speedY)
//            body.setLinearVelocity(body.getLinearVelocity().x,
//                    speedY * Math.abs(body.getLinearVelocity().y) / body.getLinearVelocity().y);
//        System.out.println(body.getLinearVelocity().y);
//
//    }
    public void setJumpClickClack(boolean canJump){
        this.canJump = canJump;
    }
    public void setMoveVector (Vector2 moveVector){
        body.setLinearVelocity(moveVector.setLength(speedX).x, body.getLinearVelocity().y);
        if (moveVector.setLength(1).y > 0.65f && canJump) {
            body.applyForceToCenter(0, 4500, true);
            canJump = false;
        }
//        body.applyForceToCenter(moveVector.setLength(speed), true);
//        body.setTransform((moveVector.x + getX()) * SCALE, (moveVector.y + getY()) * SCALE, 0);
        updateCamera();
//        System.out.println(body.getPosition());
    }

    public void draw(SpriteBatch batch) {
        frameCounter++;
        if (frameCounter == 2 * frameMultiplierForHead - 1) frameCounter= 0;
        batch.draw(GameResources.PLAYER_HEAD_TEXTURE,
                body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2,
                body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2 - frameCounter /frameMultiplierForHead* GameSettings.OBJECT_SCALE*5,
                PLAYER_WIDTH * GameSettings.OBJECT_SCALE,
                PLAYER_HEIGHT * GameSettings.OBJECT_SCALE
        );
        switch (playerState) {
            case STANDING:
                GameResources.PLAYER_STANDING_TEXTURE.setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_STANDING_TEXTURE.setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_STANDING_TEXTURE.draw(batch);
                break;
            case WALKING:
                if (frameCounter == GameResources.PLAYER_WALKING_TEXTURES.length * frameMultiplierForWalking - 1) frameCounter = 0;
                GameResources.PLAYER_WALKING_TEXTURES[frameCounter / frameMultiplierForWalking].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_WALKING_TEXTURES[frameCounter / frameMultiplierForWalking].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_WALKING_TEXTURES[frameCounter / frameMultiplierForWalking].draw(batch);
                break;
            case DOWN_DIGGING:
                if (frameCounter == GameResources.PLAYER_DOWN_DIGGING_TEXTURES.length * frameMultiplierForDigging - 1) frameCounter = 0;
                GameResources.PLAYER_DOWN_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_DOWN_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_DOWN_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].draw(batch);
                break;
            case UP_DIGGING:
                if (frameCounter == GameResources.PLAYER_UP_DIGGING_TEXTURES.length * frameMultiplierForDigging - 1) frameCounter = 0;
                GameResources.PLAYER_UP_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_UP_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_UP_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].draw(batch);
                break;
            case SIDE_DIGGING:
                if (frameCounter == GameResources.PLAYER_SIDE_DIGGING_TEXTURES.length * frameMultiplierForDigging - 1) frameCounter = 0;
                GameResources.PLAYER_SIDE_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_SIDE_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_SIDE_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].draw(batch);
                break;
        }
    }

}
