package com.mygdx.game.entities;

import static com.mygdx.game.GameSettings.PLAYER_HEIGHT;
import static com.mygdx.game.GameSettings.PLAYER_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

import java.lang.reflect.InvocationTargetException;

import com.mygdx.game.pickaxes.BasicPickaxe;

public class Player extends GameEntity {
    private static final int PLAYER_SPEED_X = 75;
    private boolean canJump;
    public PlayerStates playerState;
    public BasicPickaxe pickaxe;
    boolean movingLeft;
    boolean isNeedActivate;
    boolean isActivated;
    public boolean isJumping;
    public boolean fell;
    public boolean falling;
    boolean booleanFallingTime = true;
    boolean booleanJumpingTime = true;
    long jumpTime;
    long millis = 0;
    long forceActivatedTime;

    float timeElapsed = 0.0f;
    int currentFrame = 0;
    float frameMultiplierForHead = 0.5f;
    float frameMultiplierForWalking = 0.1f;
    float frameMultiplierForDigging = 0.1f;


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

    public void setPickaxe (BasicPickaxe pickaxe){
        this.pickaxe = pickaxe;
    }

    public void playerBreak(){
        body.setLinearVelocity(body.getLinearVelocity().cpy().sub(
                body.getLinearVelocity().setLength(1).x, 0));
    }

    public void updateCamera() {
        myGdxGame.camera.position.set(body.getPosition().x, body.getPosition().y, 0);
    }

    public void setJumpClickClack(boolean canJump) {
        this.canJump = canJump;
    }

    public void jump() {
        if (canJump && body.getLinearVelocity().y == 0) {
            playerState = PlayerStates.JUMPING;
            canJump = false;
        }
    }

    public Vector2 setMoveVector(Vector2 moveVector) {
        body.setLinearVelocity(moveVector.setLength(PLAYER_SPEED_X).x, body.getLinearVelocity().y);
        Vector2 normalizeMove = moveVector.cpy().setLength(1);

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

    public void draw(SpriteBatch batch, float deltaTime) {
        int frame;

        if (playerState == PlayerStates.WALKING || playerState == PlayerStates.STANDING) {
            batch.draw(GameResources.PLAYER_HEAD_TEXTURE,
                    body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2,
                    body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2 - TimeUtils.millis() / 500 % 2 * GameSettings.OBJECT_SCALE * 3,
                    PLAYER_WIDTH * GameSettings.OBJECT_SCALE,
                    PLAYER_HEIGHT * GameSettings.OBJECT_SCALE
            );
        }

        if (body.getLinearVelocity().y < 0)
            falling = true;
        if (body.getLinearVelocity().y == 0 && falling) {
            playerState = PlayerStates.LANDING;
            falling = false;
        }

        switch (playerState){
            case STANDING:
                GameResources.PLAYER_STANDING_TEXTURE.setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_STANDING_TEXTURE.setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_STANDING_TEXTURE.draw(batch);
                break;
            case WALKING:
                frame = (int) (TimeUtils.millis() / 75) % 5;
                GameResources.PLAYER_WALKING_TEXTURES[frame].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_WALKING_TEXTURES[frame].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_WALKING_TEXTURES[frame].draw(batch);
                break;
            case DOWN_DIGGING:
                frame = (int) (TimeUtils.millis() / 75) % 6;
                GameResources.PLAYER_DOWN_DIGGING_TEXTURES[frame].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_DOWN_DIGGING_TEXTURES[frame].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_DOWN_DIGGING_TEXTURES[frame].draw(batch);
                drawPickaxe(PlayerStates.DOWN_DIGGING, frame);
                break;
            case UP_DIGGING:
                frame = (int) (TimeUtils.millis() / 75) % 5;
                GameResources.PLAYER_UP_DIGGING_TEXTURES[frame].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_UP_DIGGING_TEXTURES[frame].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_UP_DIGGING_TEXTURES[frame].draw(batch);
                drawPickaxe(PlayerStates.UP_DIGGING, frame);
                break;
            case SIDE_DIGGING:
                frame = (int) (TimeUtils.millis() / 75) % 6;
                GameResources.PLAYER_SIDE_DIGGING_TEXTURES[frame].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_SIDE_DIGGING_TEXTURES[frame].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_SIDE_DIGGING_TEXTURES[frame].draw(batch);
                drawPickaxe(PlayerStates.SIDE_DIGGING, frame);
                break;
            case JUMPING:
                if (!isJumping && body.getLinearVelocity().y == 0) {
                    millis = TimeUtils.millis();
                    isJumping = true;
                    Gdx.input.vibrate(15);
                }
                frame = (int) ((TimeUtils.millis() - millis) / 200) % 3;
                GameResources.PLAYER_JUMPING_TEXTURES[frame].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_JUMPING_TEXTURES[frame].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_JUMPING_TEXTURES[frame].draw(batch);
                if (frame == 2 && body.getLinearVelocity().y == 0) {
                    body.applyForceToCenter(0, 5000000, true);
                    isJumping = false;
                }
                break;
            case LANDING:
                if (!fell) {
                    millis = TimeUtils.millis();
                    fell = true;
                }
                frame = (int) ((TimeUtils.millis() - millis) / 200) % 3 + 3;
                if (frame == 5) {
                    fell = false;
                    break;
                }
                GameResources.PLAYER_JUMPING_TEXTURES[frame].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_JUMPING_TEXTURES[frame].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_JUMPING_TEXTURES[frame].draw(batch);
                break;
        }


//        drawFalling(batch);
//        timeElapsed += deltaTime;
//        if (timeElapsed >= frameMultiplierForHead) {
//            currentFrame++;
//            if (currentFrame >= 2) {
//                currentFrame = 0;
//            }
//            timeElapsed = 0.0f;
//        }
//        if (playerState == PlayerStates.WALKING || playerState == PlayerStates.STANDING) {
//            batch.draw(GameResources.PLAYER_HEAD_TEXTURE,
//                    body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2,
//                    body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2 - currentFrame%2 * GameSettings.OBJECT_SCALE * 3,
//                    PLAYER_WIDTH * GameSettings.OBJECT_SCALE,
//                    PLAYER_HEIGHT * GameSettings.OBJECT_SCALE
//            );
//        }
//        switch (playerState) {
//            case STANDING:
//                GameResources.PLAYER_STANDING_TEXTURE.setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
//                GameResources.PLAYER_STANDING_TEXTURE.setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
//                GameResources.PLAYER_STANDING_TEXTURE.draw(batch);
//                break;
//            case WALKING:
//                if (currentFrame >= GameResources.PLAYER_WALKING_TEXTURES.length-1) {
//                    currentFrame = 0;
//                }
//                if (timeElapsed >= frameMultiplierForWalking) {
//                    currentFrame++;
//                    timeElapsed = 0.0f;
//                }
//                GameResources.PLAYER_WALKING_TEXTURES[currentFrame].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
//                GameResources.PLAYER_WALKING_TEXTURES[currentFrame].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
//                GameResources.PLAYER_WALKING_TEXTURES[currentFrame].draw(batch);
//                break;
//            case DOWN_DIGGING:
//                if (currentFrame >= GameResources.PLAYER_DOWN_DIGGING_TEXTURES.length-1) {
//                    currentFrame = 0;
//                }
//                if (timeElapsed >= frameMultiplierForDigging) {
//                    currentFrame++;
//                    timeElapsed = 0.0f;
//                }
//                GameResources.PLAYER_DOWN_DIGGING_TEXTURES[currentFrame].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
//                GameResources.PLAYER_DOWN_DIGGING_TEXTURES[currentFrame].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
//                GameResources.PLAYER_DOWN_DIGGING_TEXTURES[currentFrame].draw(batch);
//                drawPickaxe(PlayerStates.DOWN_DIGGING);
//                break;
//            case UP_DIGGING:
//                if (currentFrame >= GameResources.PLAYER_UP_DIGGING_TEXTURES.length-1) {
//                    currentFrame = 0;
//                }
//                if (timeElapsed >= frameMultiplierForWalking) {
//                    currentFrame++;
//                    timeElapsed = 0.0f;
//                }
//                GameResources.PLAYER_UP_DIGGING_TEXTURES[currentFrame].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
//                GameResources.PLAYER_UP_DIGGING_TEXTURES[currentFrame].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
//                GameResources.PLAYER_UP_DIGGING_TEXTURES[currentFrame].draw(batch);
//                drawPickaxe(PlayerStates.UP_DIGGING);
//                break;
//            case SIDE_DIGGING:
//                if (currentFrame >= GameResources.PLAYER_SIDE_DIGGING_TEXTURES.length-1) {
//                    currentFrame = 0;
//                }
//                if (timeElapsed >= frameMultiplierForWalking) {
//                    currentFrame++;
//                    timeElapsed = 0.0f;
//                }
//                GameResources.PLAYER_SIDE_DIGGING_TEXTURES[currentFrame].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
//                GameResources.PLAYER_SIDE_DIGGING_TEXTURES[currentFrame].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
//                GameResources.PLAYER_SIDE_DIGGING_TEXTURES[currentFrame].draw(batch);
//                drawPickaxe(PlayerStates.SIDE_DIGGING);
//                break;
//            case JUMPING:
//                drawingJump(batch);
//                break;
//            default:
//                break;
//        }
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
                GameResources.DOWN_STICK_PICKAXE[i].flip(true, false);
                GameResources.DOWN_STONE_PICKAXE[i].flip(true, false);
                GameResources.DOWN_IRON_PICKAXE[i].flip(true, false);
                GameResources.DOWN_GOLD_PICKAXE[i].flip(true, false);
                GameResources.DOWN_DIAMOND_PICKAXE[i].flip(true, false);
            }
            for (int i = 0; i < GameResources.PLAYER_UP_DIGGING_TEXTURES.length; i++) {
                GameResources.PLAYER_UP_DIGGING_TEXTURES[i].flip(true, false);

                GameResources.UP_STICK_PICKAXE[i].flip(true, false);
                GameResources.UP_STONE_PICKAXE[i].flip(true, false);
                GameResources.UP_IRON_PICKAXE[i].flip(true, false);
                GameResources.UP_GOLD_PICKAXE[i].flip(true, false);
                GameResources.UP_DIAMOND_PICKAXE[i].flip(true, false);
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

//    public void drawFalling(SpriteBatch batch) {
//        if (body.getLinearVelocity().y < 0 && !fell) {
//            playerState = PlayerStates.FALLING;
//            GameResources.PLAYER_JUMPING_TEXTURES[2].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
//            GameResources.PLAYER_JUMPING_TEXTURES[2].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
//            GameResources.PLAYER_JUMPING_TEXTURES[2].draw(batch);
//            falling = true;
//        }
//        if (body.getLinearVelocity().y == 0 && falling) {
//            fell = true;
//            playerState = PlayerStates.FALLING;
//
//        }
//        if (fell && falling) {
//            if (booleanFallingTime) {
//                fallingTime = TimeUtils.millis();
//                booleanFallingTime = false;
//            }
//
//            if (TimeUtils.millis() - fallingTime < 200) {
//
//                GameResources.PLAYER_JUMPING_TEXTURES[3].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
//                GameResources.PLAYER_JUMPING_TEXTURES[3].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
//                GameResources.PLAYER_JUMPING_TEXTURES[3].draw(batch);
//            }
//            if (TimeUtils.millis() - fallingTime > 200 && TimeUtils.millis() - fallingTime < 400) {
//                GameResources.PLAYER_JUMPING_TEXTURES[4].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
//                GameResources.PLAYER_JUMPING_TEXTURES[4].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
//                GameResources.PLAYER_JUMPING_TEXTURES[4].draw(batch);
//            }
//            if (TimeUtils.millis() - fallingTime > 400) {
//                booleanFallingTime = true;
//                if (fell && falling) {
//                    playerState = PlayerStates.STANDING;
//                }
//                fell = false;
//                falling = false;
//                isActivated = false;
//                isNeedActivate = false;
//                booleanJumpingTime = true;
//                canJump = false;
//            }
//
//        }
//    }

    public void drawDigging(float x, float y) {
        if (x == 0 && y == -1) {
            playerState = PlayerStates.DOWN_DIGGING;
        } else if (x == 0 && y == 2) {
            playerState = PlayerStates.UP_DIGGING;
        } else {
            playerState = PlayerStates.SIDE_DIGGING;
        }
    }
    private void drawPickaxe(PlayerStates playerState, int frame) {
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
                        GameResources.RIGHT_GOLD_PICKAXE[frame].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                        GameResources.RIGHT_GOLD_PICKAXE[frame].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                        GameResources.RIGHT_GOLD_PICKAXE[frame].draw(myGdxGame.batch);
                        break;
                    case "DiamondPickaxe":
                        GameResources.RIGHT_DIAMOND_PICKAXE[frame].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                        GameResources.RIGHT_DIAMOND_PICKAXE[frame].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                        GameResources.RIGHT_DIAMOND_PICKAXE[frame].draw(myGdxGame.batch);
                        break;
                }
                break;
            case UP_DIGGING:
                if (timeElapsed >= frameMultiplierForWalking) {
                    currentFrame++;
                    if (currentFrame >= GameResources.PLAYER_UP_DIGGING_TEXTURES.length) {
                        currentFrame = 0;
                    }
                    timeElapsed = 0.0f;
                }
                switch (pickaxe.getClass().getSimpleName()) {
//                    case STICK:
//                        if (frameCounter >= GameResources.UP_STICK_PICKAXE.length * frameMultiplierForDigging - 1)
//                            frameCounter = 0;
//                        GameResources.UP_STICK_PICKAXE[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
//                        GameResources.UP_STICK_PICKAXE[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
//                        GameResources.UP_STICK_PICKAXE[frameCounter / frameMultiplierForDigging].draw(myGdxGame.batch);
//                        break;
//                    case STONE:
//                        if (frameCounter >= GameResources.UP_STONE_PICKAXE.length * frameMultiplierForDigging - 1)
//                            frameCounter = 0;
//                        GameResources.UP_STONE_PICKAXE[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
//                        GameResources.UP_STONE_PICKAXE[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
//                        GameResources.UP_STONE_PICKAXE[frameCounter / frameMultiplierForDigging].draw(myGdxGame.batch);
//                        break;
//                    case IRON:
//                        if (frameCounter >= GameResources.UP_IRON_PICKAXE.length * frameMultiplierForDigging - 1)
//                            frameCounter = 0;
//                        GameResources.UP_IRON_PICKAXE[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
//                        GameResources.UP_IRON_PICKAXE[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
//                        GameResources.UP_IRON_PICKAXE[frameCounter / frameMultiplierForDigging].draw(myGdxGame.batch);
//                        break;
                    case "GoldPickaxe":
                        GameResources.UP_GOLD_PICKAXE[frame].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                        GameResources.UP_GOLD_PICKAXE[frame].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                        GameResources.UP_GOLD_PICKAXE[frame].draw(myGdxGame.batch);
                        break;
                    case "DiamondPickaxe":
                        GameResources.UP_DIAMOND_PICKAXE[frame].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                        GameResources.UP_DIAMOND_PICKAXE[frame].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                        GameResources.UP_DIAMOND_PICKAXE[frame].draw(myGdxGame.batch);
                        break;
                }
                break;
            case DOWN_DIGGING:
                if (timeElapsed >= frameMultiplierForWalking) {
                    currentFrame++;
                    if (currentFrame >= GameResources.PLAYER_DOWN_DIGGING_TEXTURES.length) {
                        currentFrame = 0;
                    }
                    timeElapsed = 0.0f;
                }
                switch (pickaxe.getClass().getSimpleName()) {
//                    case STICK:
//                        if (frameCounter >= GameResources.DOWN_STICK_PICKAXE.length * frameMultiplierForDigging - 1)
//                            frameCounter = 0;
//                        GameResources.DOWN_STICK_PICKAXE[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
//                        GameResources.DOWN_STICK_PICKAXE[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
//                        GameResources.DOWN_STICK_PICKAXE[frameCounter / frameMultiplierForDigging].draw(myGdxGame.batch);
//                        break;
//                    case STONE:
//                        if (frameCounter >= GameResources.DOWN_STONE_PICKAXE.length * frameMultiplierForDigging - 1)
//                            frameCounter = 0;
//                        GameResources.DOWN_STONE_PICKAXE[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
//                        GameResources.DOWN_STONE_PICKAXE[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
//                        GameResources.DOWN_STONE_PICKAXE[frameCounter / frameMultiplierForDigging].draw(myGdxGame.batch);
//                        break;
//                    case IRON:
//                        if (frameCounter >= GameResources.DOWN_IRON_PICKAXE.length * frameMultiplierForDigging - 1)
//                            frameCounter = 0;
//                        GameResources.DOWN_IRON_PICKAXE[frameCounter / frameMultiplierForDigging].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
//                        GameResources.DOWN_IRON_PICKAXE[frameCounter / frameMultiplierForDigging].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
//                        GameResources.DOWN_IRON_PICKAXE[frameCounter / frameMultiplierForDigging].draw(myGdxGame.batch);
//                        break;
                    case "GoldPickaxe":
                        GameResources.DOWN_GOLD_PICKAXE[frame].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                        GameResources.DOWN_GOLD_PICKAXE[frame].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                        GameResources.DOWN_GOLD_PICKAXE[frame].draw(myGdxGame.batch);
                        break;
                    case "DiamondPickaxe":
                        GameResources.DOWN_DIAMOND_PICKAXE[frame].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                        GameResources.DOWN_DIAMOND_PICKAXE[frame].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                        GameResources.DOWN_DIAMOND_PICKAXE[frame].draw(myGdxGame.batch);
                        break;
                }
                break;
        }


    }
    public void playSounds() {
        if (playerState == PlayerStates.WALKING) {
            myGdxGame.audioManager.walkingOnGrassSound.setVolume(myGdxGame.audioManager.soundVolume*myGdxGame.audioManager.overallVolume);
            myGdxGame.audioManager.walkingOnGrassSound.play();
        } else {
            myGdxGame.audioManager.walkingOnGrassSound.stop();
        }
    }
}
