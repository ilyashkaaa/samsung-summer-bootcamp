package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyCreator {
    private BodyCreator() {}
    public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        if (MemoryManager.getPlayerPos() == null) {
            MemoryManager.savePlayerPos(new Vector2(x, y));
            System.out.println("aaa");
        }
        bodyDef.position.set(MemoryManager.getPlayerPos());
        System.out.println(x/GameSettings.SCALE/GameSettings.BLOCK_SIDE);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        Vector2[] vertices = new Vector2[6];
        vertices[0] = new Vector2((float) (-width / 2 + 0.5 * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE), -height/2);
        vertices[1] = new Vector2((float) (width / 2 - 0.5 * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE), -height/2);
        vertices[2] = new Vector2(width/2, (-height+GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE)/2);
        vertices[3] = new Vector2(width/2, height/2);
        vertices[4] = new Vector2(-width/2, height/2);
        vertices[5] = new Vector2(-width/2, (-height+GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE)/2);
//        vertices[0] = new Vector2(0, -height / 2);
//        vertices[1] = new Vector2(width / 2, 0);
//        vertices[2] = new Vector2(width / 2, height / 2);
//        vertices[3] = new Vector2(-width / 2, height / 2);
//        vertices[4] = new Vector2(-width / 2, 0);
        shape.set(vertices);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f; // устанавливаем плотность тела
        fixtureDef.friction = 0f;
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;


    }
}
