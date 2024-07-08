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

import java.lang.reflect.InvocationTargetException;

import pickaxes.BasicPickaxe;

public class Player extends GameEntity {
    private static final int PLAYER_SPEED_X = 25;
    private boolean canJump;
    public PlayerStates playerState;
    public BasicPickaxe pickaxe;
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


    public Player(float width, float height, Body body, MyGdxGame myGdxGame, Class<? extends BasicPickaxe> pickaxe) {
        super(width, height, body, myGdxGame);

        try {
            this.pickaxe = pickaxe.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
            e.printStackTrace();
        }

        canJump = false;
        playerState = PlayerStates.STANDING;
        movingLeft = false;
        body.setLinearDamping(1);


    }

    public void setPickaxe (Class<? extends BasicPickaxe> pickaxe){
        try {
            this.pickaxe = pickaxe.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
            e.printStackTrace();
        }
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

    public void jump() {
        if (canJump)
            playerState = PlayerStates.JUMPING;
    }

    public Vector2 setMoveVector(Vector2 moveVector) {
        body.setLinearVelocity(moveVector.setLength(PLAYER_SPEED_X).x, body.getLinearVelocity().y);
        Vector2 normalizeMove = moveVector.cpy().setLength(1);
//        if (normalizeMove.y > 0.65f && canJump) {
//            playerState = PlayerStates.JUMPING;
//        }

        flippingTextures(moveVector);

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
        drawFalling(batch);
        frameCounter++;
        if (frameCounter == 2 * frameMultiplierForHead - 1) frameCounter = 0;
        if (playerState == PlayerStates.WALKING || playerState == PlayerStates.STANDING) {
            batch.draw(GameResources.PLAYER_HEAD_TEXTURE,
                    body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2,
                    body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2 - frameCounter / frameMultiplierForHead * GameSettings.OBJECT_SCALE * 3,
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
                if (frameCounter >= GameResources.PLAYER_WALKING_TEXTURES.length * frameMultiplierForWalking - 1)
                    frameCounter = 0;
                GameResources.PLAYER_WALKING_TEXTURES[frameCounter / frameMultiplierForWalking].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_WALKING_TEXTURES[frameCounter / frameMultiplierForWalking].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_WALKING_TEXTURES[frameCounter / frameMultiplierForWalking].draw(batch);
                break;
            case DOWN_DIGGING:
                if (frameCounter >= GameResources.PLAYER_DOWN_DIGGING_TEXTURES.length * frameMultiplierForDigging - 1)
                    frameCounter = 0;
                GameResources.PLAYER_DOWN_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_DOWN_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_DOWN_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].draw(batch);
                break;
            case UP_DIGGING:
                if (frameCounter >= GameResources.PLAYER_UP_DIGGING_TEXTURES.length * frameMultiplierForDigging - 1)
                    frameCounter = 0;
                GameResources.PLAYER_UP_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_UP_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_UP_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].draw(batch);
                break;
            case SIDE_DIGGING:
                if (frameCounter >= GameResources.PLAYER_SIDE_DIGGING_TEXTURES.length * frameMultiplierForDigging - 1)
                    frameCounter = 0;
                GameResources.PLAYER_SIDE_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_SIDE_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_SIDE_DIGGING_TEXTURES[frameCounter / frameMultiplierForDigging].draw(batch);
                drawPickaxe(PlayerStates.SIDE_DIGGING);
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
                GameResources.RIGHT_STICK_PICKAXE[i].flip(true, false);
                GameResources.RIGHT_STONE_PICKAXE[i].flip(true, false);
                GameResources.RIGHT_IRON_PICKAXE[i].flip(true, false);
                GameResources.RIGHT_GOLD_PICKAXE[i].flip(true, false);
                GameResources.RIGHT_DIAMOND_PICKAXE[i].flip(true, false);
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
        if (TimeUtils.millis() - jumpTime < 200) {
            GameResources.PLAYER_JUMPING_TEXTURES[0].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
            GameResources.PLAYER_JUMPING_TEXTURES[0].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
            GameResources.PLAYER_JUMPING_TEXTURES[0].draw(batch);
        }
        if (TimeUtils.millis() - jumpTime > 200 && TimeUtils.millis() - jumpTime < 400) {
            GameResources.PLAYER_JUMPING_TEXTURES[1].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
            GameResources.PLAYER_JUMPING_TEXTURES[1].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
            GameResources.PLAYER_JUMPING_TEXTURES[1].draw(batch);
        }
        if (TimeUtils.millis() - jumpTime > 400 && booleanFallingTime) {
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
        if (body.getLinearVelocity().y < 0) {
            playerState = PlayerStates.FALLING;
            GameResources.PLAYER_JUMPING_TEXTURES[2].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
            GameResources.PLAYER_JUMPING_TEXTURES[2].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
            GameResources.PLAYER_JUMPING_TEXTURES[2].draw(batch);
            falling = true;
        }
        if (body.getLinearVelocity().y == 0 && falling) {
            fell = true;
            playerState = PlayerStates.FALLING;

        }
        if (fell && falling) {
            if (booleanFallingTime) {
                fallingTime = TimeUtils.millis();
                booleanFallingTime = false;
            }

            if (TimeUtils.millis() - fallingTime < 200) {

                GameResources.PLAYER_JUMPING_TEXTURES[3].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_JUMPING_TEXTURES[3].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_JUMPING_TEXTURES[3].draw(batch);
            }
            if (TimeUtils.millis() - fallingTime > 200 && TimeUtils.millis() - fallingTime < 400) {
                GameResources.PLAYER_JUMPING_TEXTURES[4].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_JUMPING_TEXTURES[4].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_JUMPING_TEXTURES[4].draw(batch);
            }
            if (TimeUtils.millis() - fallingTime > 400) {
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

    public void drawDigging(int x, int y, int playerX, int playerY) {
        if (playerX == x && playerY > y) {
            playerState = PlayerStates.DOWN_DIGGING;
        } else if (playerX == x && playerY < y) {
            playerState = PlayerStates.UP_DIGGING;
        } else {
            playerState = PlayerStates.SIDE_DIGGING;
        }
    }
    private void drawPickaxe(PlayerStates playerState) {
        switch (playerState) {
            case SIDE_DIGGING:
                switch (pickaxe.getClass().getSimpleName()) {
//                    case STICK:
//                        if (frameCounter >= GameResources.RIGHT_STICK_PICKAXE.length * frameMultiplierForDigging - 1)
//                            frameCounter = 0;
//                        GameResources.RIGHT_STICK_PICKAXE[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
//                        GameResources.RIGHT_STICK_PICKAXE[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
//                        GameResources.RIGHT_STICK_PICKAXE[frameCounter / frameMultiplierForDigging].draw(myGdxGame.batch);
//                        break;
//                    case STONE:
//                        if (frameCounter >= GameResources.RIGHT_STONE_PICKAXE.length * frameMultiplierForDigging - 1)
//                            frameCounter = 0;
//                        GameResources.RIGHT_STONE_PICKAXE[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
//                        GameResources.RIGHT_STONE_PICKAXE[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
//                        GameResources.RIGHT_STONE_PICKAXE[frameCounter / frameMultiplierForDigging].draw(myGdxGame.batch);
//                        break;
//                    case IRON:
//                        if (frameCounter >= GameResources.RIGHT_IRON_PICKAXE.length * frameMultiplierForDigging - 1)
//                            frameCounter = 0;
//                        GameResources.RIGHT_IRON_PICKAXE[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
//                        GameResources.RIGHT_IRON_PICKAXE[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
//                        GameResources.RIGHT_IRON_PICKAXE[frameCounter / frameMultiplierForDigging].draw(myGdxGame.batch);
//                        break;
                    case "GoldPickaxe":
                        if (frameCounter >= GameResources.RIGHT_GOLD_PICKAXE.length * frameMultiplierForDigging - 1)
                            frameCounter = 0;
                        GameResources.RIGHT_GOLD_PICKAXE[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                        GameResources.RIGHT_GOLD_PICKAXE[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                        GameResources.RIGHT_GOLD_PICKAXE[frameCounter / frameMultiplierForDigging].draw(myGdxGame.batch);
                        break;
                    case "DiamondPickaxe":
                        if (frameCounter >= GameResources.RIGHT_DIAMOND_PICKAXE.length * frameMultiplierForDigging - 1)
                            frameCounter = 0;
                        GameResources.RIGHT_DIAMOND_PICKAXE[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                        GameResources.RIGHT_DIAMOND_PICKAXE[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                        GameResources.RIGHT_DIAMOND_PICKAXE[frameCounter / frameMultiplierForDigging].draw(myGdxGame.batch);
                        break;
                }
                break;
            case UP_DIGGING:
                //TODO PICKAXE UP DIGGING
                break;
            case DOWN_DIGGING:
                //TODO PICKAXE DOWN DIGGING
                break;
        }


    }
}
