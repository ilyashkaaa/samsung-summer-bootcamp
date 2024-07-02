package com.mygdx.game;

import static com.mygdx.game.GameSettings.SCALE;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

public class Player extends GameEntity {

    public Player(float width, float height, Body body, MyGdxGame myGdxGame) {
        super(width, height, body, myGdxGame);
//        this.speed = 4f;

    }

    @Override
    public void update() {
        x = getX()+velX;
        y = getY()+velX;
        velY = 0;
        velX = 0;
        //Vector3 position = myGdxGame.camera.position;
//        position.x = x;
//        position.y = y;
//        myGdxGame.camera.position.set(position);
        updateCamera();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velY += 10;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velX -= 10;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velY -= 10;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velX += 10;
        }
        //body.applyForceToCenter(0, -10, true);

//        body.setLinearVelocity(velX, velY);
        setX((int) x);
        setY((int) y);
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

    public void setX(int x) {
        body.setTransform(x * SCALE, body.getPosition().y, 0);
    }

    public void setY(int y) {
        body.setTransform(body.getPosition().x, y * SCALE, 0);
    }
    public void updateCamera() {
        Vector3 position = myGdxGame.camera.position;
        position.x = x * SCALE;
        position.y = y * SCALE;
        myGdxGame.camera.position.set(position);

    }

}
