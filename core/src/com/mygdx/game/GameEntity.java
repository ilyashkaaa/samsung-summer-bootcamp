package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class GameEntity {

    protected float x, y, velX, velY, speed;
    protected float width, height;
    protected Body body;
    MyGdxGame myGdxGame;
    public GameEntity(float width, float height, Body body, MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = width/GameSettings.SCALE;
        this.height = height/GameSettings.SCALE;
        this.body = body;
        this.velX = 0;
        this.velY = 0;
        this.speed = 0;
    }
    public abstract void update();

    public abstract void render(SpriteBatch batch);


    public Body getBody() {
        return body;
    }

}
