package com.mygdx.game.entities;

import static com.mygdx.game.GameSettings.PLAYER_HEIGHT;
import static com.mygdx.game.GameSettings.PLAYER_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.AudioManager;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MemoryManager;
import com.mygdx.game.MyGdxGame;

import java.lang.reflect.InvocationTargetException;

import com.mygdx.game.pickaxes.BasicPickaxe;

public class Player extends GameEntity {
    private static final int PLAYER_SPEED_X = 75;
    private boolean canJump;
    public PlayerStates playerState;
    public BasicPickaxe pickaxe;
    boolean movingLeft;
    public boolean isJumping;
    public boolean fell;
    public boolean falling;
    public boolean digging = false;

    PlayerStates wasState;
    Animation playerAnim;
    Animation pickAxesAnim;
    boolean setLooping = true;
    float stateTime = 0f;

    public Player(float width, float height, Body body, MyGdxGame myGdxGame, Class<? extends BasicPickaxe> pickaxe) {
        super(width, height, body, myGdxGame);

        playerAnim = new Animation(0.13f, true);
        pickAxesAnim = new Animation(0.13f, true);
        setPlayerAnim(GameResources.PLAYER_WALKING_TEXTURES, true);
        setPickaxeAnim(GameResources.RIGHT_STICK_PICKAXE);

        try {
            this.pickaxe = pickaxe.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
            e.printStackTrace();
        }

        canJump = true;
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
        System.out.println(playerState);
        if ((playerState == PlayerStates.WALKING || playerState == PlayerStates.STANDING || playerState == PlayerStates.FALLING || playerState == PlayerStates.UP_DIGGING || playerState == PlayerStates.SIDE_DIGGING || playerState == PlayerStates.DOWN_DIGGING) && !isJumping) {
            batch.draw(GameResources.PLAYER_HEAD_TEXTURE,
                    body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2,
                    body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2 - TimeUtils.millis() / 500 % 2 * GameSettings.OBJECT_SCALE * 3,
                    PLAYER_WIDTH * GameSettings.OBJECT_SCALE,
                    PLAYER_HEIGHT * GameSettings.OBJECT_SCALE
            );
        }

        if (body.getLinearVelocity().y < -1) {
            falling = true;
            playerState = PlayerStates.FALLING;
        }
        else if (!digging && playerState != PlayerStates.LANDING && playerState != PlayerStates.UP_DIGGING && playerState != PlayerStates.SIDE_DIGGING && playerState != PlayerStates.DOWN_DIGGING)
            playerState = PlayerStates.STANDING;
        if (body.getLinearVelocity().y == 0 && falling) {
            playerState = PlayerStates.LANDING;
            stateTime = 0f;
            setPlayerAnim(GameResources.PLAYER_LANDING_TEXTURES, false);
            falling = false;
        }

        stateTime += Gdx.graphics.getDeltaTime();

        Sprite framePickaxe = (Sprite) pickAxesAnim.getKeyFrame(stateTime, true);
        framePickaxe.setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
        framePickaxe.setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
        if (playerState == PlayerStates.DOWN_DIGGING || playerState == PlayerStates.UP_DIGGING || playerState == PlayerStates.SIDE_DIGGING)
            framePickaxe.draw(batch);

        Sprite frame = (Sprite) playerAnim.getKeyFrame(stateTime, setLooping);
        frame.setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
        frame.setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
        frame.draw(batch);

        if (playerAnim.isAnimationFinished(stateTime)) {
            if (isJumping) {
                isJumping = false;
                playerState = PlayerStates.JUMPING;
                body.applyForceToCenter(0, 1000000000, false);
            }
            if (playerState == PlayerStates.LANDING){
                playerState = PlayerStates.STANDING;
            }
            setPlayerAnim(new Sprite[]{GameResources.PLAYER_STANDING_TEXTURE}, true);
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
            for (int i = 0; i < GameResources.PLAYER_LANDING_TEXTURES.length; i++) {
                GameResources.PLAYER_LANDING_TEXTURES[i].flip(true, false);
            }
        }
    }

    public void jump(){
        if (canJump) {
            isJumping = true;
            wasState = playerState;
            playerState = PlayerStates.JUMPING;
            stateTime = 0;
            setPlayerAnim(GameResources.PLAYER_JUMPING_TEXTURES, false);
            canJump = false;
        }
    }

    public void drawDigging(float x, float y) {
        if (Math.round(body.getLinearVelocity().y) == 0) {
            if (x == 0 && y == -1) {
                playerState = PlayerStates.DOWN_DIGGING;
                setPlayerAnim(GameResources.PLAYER_DOWN_DIGGING_TEXTURES, true);
                switch (pickaxe.getClass().getSimpleName()){
                    case "Stick":
                        setPickaxeAnim(GameResources.DOWN_STICK_PICKAXE);
                        break;
                    case "StonePickaxe":
                        setPickaxeAnim(GameResources.DOWN_STONE_PICKAXE);
                        break;
                    case "IronPickaxe":
                        setPickaxeAnim(GameResources.DOWN_IRON_PICKAXE);
                        break;
                    case "GoldPickaxe":
                        setPickaxeAnim(GameResources.DOWN_GOLD_PICKAXE);
                        break;
                    case "DiamondPickaxe":
                        setPickaxeAnim(GameResources.DOWN_DIAMOND_PICKAXE);
                        break;
                }
            } else if (x == 0 && y == 2) {
                playerState = PlayerStates.UP_DIGGING;
                setPlayerAnim(GameResources.PLAYER_UP_DIGGING_TEXTURES, true);
                switch (pickaxe.getClass().getSimpleName()){
                    case "Stick":
                        setPickaxeAnim(GameResources.UP_STICK_PICKAXE);
                        break;
                    case "StonePickaxe":
                        setPickaxeAnim(GameResources.UP_STONE_PICKAXE);
                        break;
                    case "IronPickaxe":
                        setPickaxeAnim(GameResources.UP_IRON_PICKAXE);
                        break;
                    case "GoldPickaxe":
                        setPickaxeAnim(GameResources.UP_GOLD_PICKAXE);
                        break;
                    case "DiamondPickaxe":
                        setPickaxeAnim(GameResources.UP_DIAMOND_PICKAXE);
                        break;
                }
            } else {
                playerState = PlayerStates.SIDE_DIGGING;
                setPlayerAnim(GameResources.PLAYER_SIDE_DIGGING_TEXTURES, true);
                switch (pickaxe.getClass().getSimpleName()){
                    case "Stick":
                        setPickaxeAnim(GameResources.RIGHT_STICK_PICKAXE);
                        break;
                    case "StonePickaxe":
                        setPickaxeAnim(GameResources.RIGHT_STONE_PICKAXE);
                        break;
                    case "IronPickaxe":
                        setPickaxeAnim(GameResources.RIGHT_IRON_PICKAXE);
                        break;
                    case "GoldPickaxe":
                        setPickaxeAnim(GameResources.RIGHT_GOLD_PICKAXE);
                        break;
                    case "DiamondPickaxe":
                        setPickaxeAnim(GameResources.RIGHT_DIAMOND_PICKAXE);
                        break;
                }
            }
        }

    }

    public void setPlayerAnim(Sprite[] anim, boolean setLooping){
        playerAnim = new Animation(0.13f, anim);
        this.setLooping = setLooping;
    }
    public void setPickaxeAnim(Sprite[] anim){
        pickAxesAnim = new Animation(0.13f, anim);
    }

    public void playSounds() {
        myGdxGame.audioManager.backgroundMusic.setVolume(myGdxGame.audioManager.musicVolume*myGdxGame.audioManager.overallVolume);
        myGdxGame.audioManager.backgroundMusic.play();
        if (playerState == PlayerStates.WALKING && body.getLinearVelocity().y == 0) {
            myGdxGame.audioManager.walkingOnGrassSound.setVolume(myGdxGame.audioManager.soundVolume*myGdxGame.audioManager.overallVolume);
            myGdxGame.audioManager.walkingOnGrassSound.play();
        } else {
            myGdxGame.audioManager.walkingOnGrassSound.stop();
        }
    }
}
