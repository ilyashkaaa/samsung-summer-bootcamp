package com.mygdx.game.map.blocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.physics.box2d.ChainShape;
import com.mygdx.game.GameSettings;

public class MapBorder {

    private World world;

    public MapBorder(World world) {
        this.world = world;
    }

    public void createMapBorder(float mapWidth, float mapHeight) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        ChainShape shape = new ChainShape();
        shape.createLoop(new Vector2[]{
                new Vector2(GameSettings.BLOCK_SIDE*GameSettings.OBJECT_SCALE*GameSettings.viewBlocksX/2, 0),
                new Vector2(mapWidth-GameSettings.BLOCK_SIDE*GameSettings.OBJECT_SCALE*GameSettings.viewBlocksX/2, 0),
                new Vector2(mapWidth-GameSettings.BLOCK_SIDE*GameSettings.OBJECT_SCALE*GameSettings.viewBlocksX/2, mapHeight),
                new Vector2(GameSettings.BLOCK_SIDE*GameSettings.OBJECT_SCALE*GameSettings.viewBlocksX/2, mapHeight)
        });

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        world.createBody(bodyDef).createFixture(fixtureDef);

        shape.dispose();
    }
}
