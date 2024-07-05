package com.mygdx.game.entities;

import static com.mygdx.game.GameSettings.PLAYER_HEIGHT;
import static com.mygdx.game.GameSettings.PLAYER_WIDTH;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

public class Player extends GameEntity {
    private static final int PLAYER_SPEED_X = 25;
    private boolean canJump;
    PlayerStates playerState;
    boolean movingLeft;
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
    public void setJumpClickClack(boolean canJump){
        this.canJump = canJump;
    }
    public Vector2 setMoveVector (Vector2 moveVector){
        body.setLinearVelocity(moveVector.setLength(PLAYER_SPEED_X).x, body.getLinearVelocity().y);
        Vector2 normalizeMove = moveVector.cpy().setLength(1);
        if (normalizeMove.y > 0.65f && canJump) {
            body.applyForceToCenter(0, 4500, true);
            canJump = false;
        }

        flippingTextures(moveVector);
        updateCamera();

        if (normalizeMove.y > 0.65f)
            return new Vector2(0, 2);
        else if (normalizeMove.y <= 0.65f && normalizeMove.y > 0 && normalizeMove.x > 0.5f)
            return new Vector2(1, 1);
        else if (normalizeMove.y <= 0 && normalizeMove.y > -0.65f && normalizeMove.x > 0.5f)
            return new Vector2(1, 0);
        else if (normalizeMove.y <= 0.65f && normalizeMove.y > 0 && normalizeMove.x < -0.5f)
            return new Vector2(-1, 1);
        else if (normalizeMove.y <= 0 && normalizeMove.y > -0.65f && normalizeMove.x < -0.5f)
            return new Vector2(-1, 0);
        else if (normalizeMove.y < -0.65f)
            return new Vector2(0, -1);
        else
            return new Vector2(0, 0);
    }

    public void draw(SpriteBatch batch) {
        frameCounter++;
        if (frameCounter == 2 * frameMultiplierForHead - 1) frameCounter= 0;
        batch.draw(GameResources.PLAYER_HEAD_TEXTURE,
                body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2,
                body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2 - (float) frameCounter /frameMultiplierForHead * GameSettings.OBJECT_SCALE*5,
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
            default:
                break;
        }
    }
    private void flippingTextures(Vector2 moveVector) {
        if ((moveVector.x>0 && movingLeft) || (moveVector.x<0 && !movingLeft) ) {
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


        }


    }
}
