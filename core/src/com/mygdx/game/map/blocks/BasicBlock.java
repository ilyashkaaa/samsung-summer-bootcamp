package com.mygdx.game.map.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

public abstract class BasicBlock {
    protected static int durability;
    private int hp;
    private boolean hasCollision;
    protected static Texture texture;

    protected BasicBlock() {
        this.hp = durability;
        this.hasCollision = false;
    }

    public abstract int getDurability();

    public abstract Texture getTexture();

    public abstract void setDurability(int durability);

    public void setHasCollision(boolean isNeedCollision) {
        this.hasCollision = isNeedCollision;
    }

    public boolean getHasCollision() {
        return hasCollision;
    }

    public static Body createStaticBody(int i, int k, MyGdxGame myGdxGame) {

        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        // Set its world position
        groundBodyDef.position.set(new Vector2((i * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE + GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE / 2),
                (k * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE + GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE / 2))
        );
        groundBodyDef.fixedRotation = true;

        // Create a body from the definition and add it to the world
        Body groundBody = myGdxGame.world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();

        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE / 2, GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE / 2);

        // Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);

        // Clean up after ourselves
        groundBox.dispose();
        return groundBody;
    }
}
