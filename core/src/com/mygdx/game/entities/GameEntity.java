package com.mygdx.game.entities;


import com.badlogic.gdx.physics.box2d.Body;

import com.mygdx.game.MyGdxGame;

public abstract class GameEntity {

    protected float x;
    protected float y;
    protected float velX;
    protected float velY;
    protected float speed;
    protected float width;
    protected float height;
    protected Body body;
    MyGdxGame myGdxGame;

    protected GameEntity(float width, float height, Body body, MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = width;
        this.height = height;
        this.body = body;
        this.velX = 0;
        this.velY = 0;
        this.speed = 0;
    }


    public Body getBody() {
        return body;
    }

}
