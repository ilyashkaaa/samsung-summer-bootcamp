package com.mygdx.game.map.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.screens.GameScreen;

public abstract class BasicBlock {
    protected static int durability;
    private int hp;
    private boolean isNeedCollision;
     protected static Texture texture;

    public BasicBlock() {
        this.hp = durability;
        this.isNeedCollision = true;
    }

    public abstract int getDurability();

    public abstract Texture getTexture();

    public void setDurability(int durability) {
        BasicBlock.durability= durability;
    }

    public static Body createStaticBody(int i, int k, GameScreen gameScreen, MyGdxGame myGdxGame) {

        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        // Set its world position
        groundBodyDef.position.set(new Vector2(i * 64 + 32, k * 64 + 32));
        groundBodyDef.fixedRotation = true;

        // Create a body from the definition and add it to the world
        Body groundBody = myGdxGame.world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();

        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(32, 32);

        // Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);

        // Clean up after ourselves
        groundBox.dispose();
        return groundBody;
    }
}
