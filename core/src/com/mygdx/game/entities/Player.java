package com.mygdx.game.entities;

import static com.mygdx.game.GameSettings.PLAYER_HEIGHT;
import static com.mygdx.game.GameSettings.PLAYER_WIDTH;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

public class Player extends GameEntity {
    private static final int PLAYER_SPEED_X = 25;
    private boolean canJump;
    PlayerStates playerState;
    boolean movingLeft;
    boolean isNeedActivate;
    boolean isActivated;
    boolean fell;
    boolean falling;
    boolean booleanFallingTime = true;
    boolean booleanJumpingTime = true;
    long jumpTime;
    long fallingTime;
    long forceActivatedTime;
    int frameCounter = 0;
    int frameMultiplierForHead = 20;
    int frameMultiplierForWalking = 4;
    int frameMultiplierForDigging = 4;



    public Player(float width, float height, Body body, MyGdxGame myGdxGame) {
        super(width, height, body, myGdxGame);
        canJump = false;
        playerState = PlayerStates.STANDING;
        movingLeft = false;
        body.setLinearDamping(1);


    }


    public void updateCamera() {
        Vector3 position = myGdxGame.camera.position;
        position.x = body.getPosition().x;
        position.y = body.getPosition().y;
        myGdxGame.camera.position.set(position);

    }
    public void setJumpClickClack(boolean canJump) {
        this.canJump = canJump;
    }
    public void setMoveVector(Vector2 moveVector) {
        body.setLinearVelocity(moveVector.setLength(PLAYER_SPEED_X).x, body.getLinearVelocity().y);
        if (moveVector.setLength(1).y > 0.65f && canJump) {
            playerState = PlayerStates.JUMPING;
        }
        flippingTextures(moveVector);
        updateCamera();
    }

    public void draw(SpriteBatch batch) {
        drawFalling(batch);
        frameCounter++;
        if (frameCounter == 2 * frameMultiplierForHead - 1) frameCounter = 0;
        if (playerState == PlayerStates.WALKING || playerState == PlayerStates.STANDING) {
            batch.draw(GameResources.PLAYER_HEAD_TEXTURE,
                    body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2,
                    body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2 - (float) frameCounter / frameMultiplierForHead * GameSettings.OBJECT_SCALE * 5,
                    PLAYER_WIDTH * GameSettings.OBJECT_SCALE,
                    PLAYER_HEIGHT * GameSettings.OBJECT_SCALE
            );
        }
        switch (playerState) {
            case STANDING:
                GameResources.PLAYER_STANDING_TEXTURE.setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_STANDING_TEXTURE.setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_STANDING_TEXTURE.draw(batch);
                break;
            case WALKING:
                if (frameCounter == GameResources.PLAYER_WALKING_TEXTURES.length * frameMultiplierForWalking - 1)
                    frameCounter = 0;
                GameResources.PLAYER_WALKING_TEXTURES[frameCounter / frameMultiplierForWalking].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_WALKING_TEXTURES[frameCounter / frameMultiplierForWalking].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_WALKING_TEXTURES[frameCounter / frameMultiplierForWalking].draw(batch);
                break;
            case DOWN_DIGGING:
                if (frameCounter == GameResources.PLAYER_DOWN_DIGGING_TEXTURES.length * frameMultiplierForDigging - 1)
                    frameCounter = 0;
                GameResources.PLAYER_DOWN_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_DOWN_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_DOWN_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].draw(batch);
                break;
            case UP_DIGGING:
                if (frameCounter == GameResources.PLAYER_UP_DIGGING_TEXTURES.length * frameMultiplierForDigging - 1)
                    frameCounter = 0;
                GameResources.PLAYER_UP_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_UP_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_UP_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].draw(batch);
                break;
            case SIDE_DIGGING:
                if (frameCounter == GameResources.PLAYER_SIDE_DIGGING_TEXTURES.length * frameMultiplierForDigging - 1)
                    frameCounter = 0;
                GameResources.PLAYER_SIDE_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_SIDE_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_SIDE_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].draw(batch);
                break;
            case JUMPING:
                drawingJump(batch);
                break;
            default:
                break;
        }
    }

    private void flippingTextures(Vector2 moveVector) {
        if ((moveVector.x > 0 && movingLeft) || (moveVector.x < 0 && !movingLeft)) {
            movingLeft = !movingLeft;
            GameResources.PLAYER_HEAD_TEXTURE.flip(true, false);
            GameResources.PLAYER_STANDING_TEXTURE.flip(true, false);
            for (int i = 0; i < GameResources.PLAYER_WALKING_TEXTURES.length; i++) {
                GameResources.PLAYER_WALKING_TEXTURES[i].flip(true, false);
            }
            for (int i = 0; i < GameResources.PLAYER_DOWN_DIGGING_TEXTURES.length; i++) {
                GameResources.PLAYER_DOWN_DIGGING_TEXTURES[i].flip(true, false);
            }
            for (int i = 0; i < GameResources.PLAYER_UP_DIGGING_TEXTURES.length; i++) {
                GameResources.PLAYER_UP_DIGGING_TEXTURES[i].flip(true, false);
            }
            for (int i = 0; i < GameResources.PLAYER_SIDE_DIGGING_TEXTURES.length; i++) {
                GameResources.PLAYER_SIDE_DIGGING_TEXTURES[i].flip(true, false);
            }
            for (int i = 0; i < GameResources.PLAYER_JUMPING_TEXTURES.length; i++) {
                GameResources.PLAYER_JUMPING_TEXTURES[i].flip(true, false);
            }
        }
    }

    private void drawingJump(SpriteBatch batch) {
        if (booleanJumpingTime) {
            jumpTime = TimeUtils.millis();
            booleanJumpingTime = false;
        }
        if (TimeUtils.millis() - jumpTime < 300) {
            GameResources.PLAYER_JUMPING_TEXTURES[0].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
            GameResources.PLAYER_JUMPING_TEXTURES[0].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
            GameResources.PLAYER_JUMPING_TEXTURES[0].draw(batch);
        }
        if (TimeUtils.millis() - jumpTime > 300 && TimeUtils.millis() - jumpTime < 700) {
            GameResources.PLAYER_JUMPING_TEXTURES[1].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
            GameResources.PLAYER_JUMPING_TEXTURES[1].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
            GameResources.PLAYER_JUMPING_TEXTURES[1].draw(batch);
        }
        if (TimeUtils.millis() - jumpTime > 700 && booleanFallingTime) {
            isNeedActivate = true;
            GameResources.PLAYER_JUMPING_TEXTURES[2].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
            GameResources.PLAYER_JUMPING_TEXTURES[2].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
            GameResources.PLAYER_JUMPING_TEXTURES[2].draw(batch);
        }
        if (isNeedActivate && !isActivated) {
            body.applyForceToCenter(0, 27000, true);

            isActivated = true;
            forceActivatedTime = TimeUtils.millis();

        }



    }
    public void drawFalling(SpriteBatch batch) {
        if (body.getLinearVelocity().y<0) {
            falling = true;
        }
        if (body.getLinearVelocity().y==0 && falling) {
            fell = true;
            playerState = PlayerStates.JUMPING;
        }
        if (fell && falling)  {
            if (booleanFallingTime) {
                fallingTime = TimeUtils.millis();
                booleanFallingTime = false;
            }


            if (TimeUtils.millis() - fallingTime < 200) {

                GameResources.PLAYER_JUMPING_TEXTURES[3].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_JUMPING_TEXTURES[3].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_JUMPING_TEXTURES[3].draw(batch);
            }
            if (TimeUtils.millis() - fallingTime > 200 && TimeUtils.millis() - fallingTime < 500) {
                GameResources.PLAYER_JUMPING_TEXTURES[4].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_JUMPING_TEXTURES[4].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_JUMPING_TEXTURES[4].draw(batch);
            }
            if (TimeUtils.millis() - fallingTime > 500) {
                booleanFallingTime = true;
                fell = false;
                falling = false;
                isActivated = false;
                isNeedActivate = false;
                booleanJumpingTime = true;
                playerState = PlayerStates.STANDING;
                canJump = false;
            }

        }
    }
}
