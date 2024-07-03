package com.mygdx.game.entities;

import static com.mygdx.game.GameSettings.PLAYER_HEIGHT;
import static com.mygdx.game.GameSettings.PLAYER_WIDTH;
import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

public class Player extends GameEntity {
    private final int speed = 100;
    PlayerStates playerState;
    int frameCounterForHead;
    int frameCounterForBody;
    int frameMultiplierForHead = 20;
    int frameMultiplierForBody = 4;
    boolean movingLeft;

    public Player(float width, float height, Body body, MyGdxGame myGdxGame) {
        super(width, height, body, myGdxGame);
//        this.speed = 4f;
        playerState = PlayerStates.STANDING;
        movingLeft = false;


    }

    @Override
    public void update() {
        playerState = PlayerStates.STANDING;

        velY = 0;
        velX = 0;
        //Vector3 position = myGdxGame.camera.position;
//        position.x = x;
//        position.y = y;
//        myGdxGame.camera.position.set(position);
        updateCamera();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velY += speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerState = PlayerStates.LEFT_WALKING;
            if (!movingLeft) {
                for (int i = 0; i < 8; i++) {
                    GameResources.PLAYER_WALKING_TEXTURES[i].flip(true, false);
                }
                GameResources.PLAYER_STANDING_TEXTURE.flip(true, false);
                GameResources.PLAYER_HEAD_TEXTURE.flip(true, false);
                movingLeft = true;
            }
            velX -= speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velY -= speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerState = PlayerStates.LEFT_WALKING;
            if (movingLeft) {
                for (int i = 0; i < 8; i++) {
                    GameResources.PLAYER_WALKING_TEXTURES[i].flip(true, false);
                }
                GameResources.PLAYER_STANDING_TEXTURE.flip(true, false);
                GameResources.PLAYER_HEAD_TEXTURE.flip(true, false);
                movingLeft = false;
            }
            velX += speed;
        }
        x = getX() + velX;
        y = getY() + velY;
        //body.applyForceToCenter(0, -10, true);

//        body.setLinearVelocity(velX, velY);
        setX(x);
        setY(y);
//        body.setTransform(getX(), getY(), 0);

//        body.applyForceToCenter();
//        body.setTransform(x + body.getLinearVelocity().x, y + body.getLinearVelocity().y, 0);
        //  System.out.println(x + " " + y);

    }

    @Override
    public void render(SpriteBatch batch) {

    }

    public int getX() {
        return (int) (body.getPosition().x / SCALE);
    }

    public int getY() {
        return (int) (body.getPosition().y / SCALE);
    }

    public void setX(float x) {
        body.setTransform(x * SCALE, body.getPosition().y, 0);
    }

    public void setY(float y) {
        body.setTransform(body.getPosition().x, y * SCALE, 0);
    }

    public void updateCamera() {
        Vector3 position = myGdxGame.camera.position;
        position.x = x * SCALE;
        position.y = y * SCALE;
        myGdxGame.camera.position.set(position);

    }

    public void draw(SpriteBatch batch) {
        if (frameCounterForHead++ == frameMultiplierForHead - 1) frameCounterForHead = 0;
        batch.draw(GameResources.PLAYER_HEAD_TEXTURE,
                body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2,
                body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2 - 2 * frameCounterForHead / frameMultiplierForHead,
                PLAYER_WIDTH * GameSettings.OBJECT_SCALE,
                PLAYER_HEIGHT * GameSettings.OBJECT_SCALE
        );


        switch (playerState) {
            case STANDING:
                GameResources.PLAYER_STANDING_TEXTURE.setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_STANDING_TEXTURE.setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_STANDING_TEXTURE.draw(batch);
                break;
            case RIGHT_WALKING, LEFT_WALKING:
                if (frameCounterForBody++ == GameResources.PLAYER_WALKING_TEXTURES.length * frameMultiplierForBody - 1)
                    frameCounterForBody = 0;
                GameResources.PLAYER_WALKING_TEXTURES[frameCounterForBody / frameMultiplierForBody].setSize(PLAYER_WIDTH * GameSettings.OBJECT_SCALE, PLAYER_HEIGHT * GameSettings.OBJECT_SCALE);
                GameResources.PLAYER_WALKING_TEXTURES[frameCounterForBody / frameMultiplierForBody].setPosition(body.getPosition().x - PLAYER_WIDTH * GameSettings.OBJECT_SCALE / 2, body.getPosition().y - PLAYER_HEIGHT * GameSettings.OBJECT_SCALE / 2);
                GameResources.PLAYER_WALKING_TEXTURES[frameCounterForBody / frameMultiplierForBody].draw(batch);
                break;


        }
    }

}
