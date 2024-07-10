package com.mygdx.game.map.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.InInventory;
import com.mygdx.game.MyGdxGame;

import java.lang.reflect.Type;

import java.time.Duration;
import java.util.function.DoubleUnaryOperator;

import javax.swing.Spring;

public abstract class BasicBlock implements InInventory, Disposable {
    protected int durability;
    protected int hp;
    protected int cost;
    protected static float width = GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE;
    protected static float height = GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE;
    protected static Sprite[] breaking = GameResources.BREAKING_BLOCKS;
    private boolean hasCollision;
    private boolean isDestroyed;
    protected static Texture texture;

    protected BasicBlock() {
        this.hp = durability;
        this.hasCollision = false;
    }
    public boolean hit(int hit){
        setHp(getHp() - hit);
        return getHp() > 0;
    }
    public abstract int getDurability();

    public abstract int getHp();

    public abstract Texture getTexture();

    public abstract void setDurability(int durability);
    public abstract void setHp(int hp);

    public void setHasCollision(boolean isNeedCollision) {
        this.hasCollision = isNeedCollision;
    }

    public boolean getHasCollision() {
        return hasCollision;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public int getCost(){
        return 1;
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

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundBox;
        fixtureDef.density = 0.1f; // устанавливаем плотность тела
        // Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(fixtureDef);

        // Clean up after ourselves
        groundBox.dispose();
        return groundBody;
    }
    public void draw(SpriteBatch batch, float x, float y){
        batch.draw(getTexture(), x, y, width, height);

        float slice = getDurability() / 9f;
        for (int i = 0; i < 8; i++) {
            if (getHp() >= getDurability() - slice * (i + 2) && getHp() < getDurability() - slice * (i + 1) && !isDestroyed){
                batch.draw(breaking[i], x, y, width, height);
                }
        }
    }
    @Override
    public void dispose() {
        texture.dispose();
    }
}
