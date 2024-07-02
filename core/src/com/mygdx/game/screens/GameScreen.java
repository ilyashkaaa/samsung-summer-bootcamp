package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.BodyCreator;
import com.mygdx.game.GameEntity;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Player;
import com.mygdx.game.map.blocks.BasicBlock;
import com.mygdx.game.map.blocks.Dirt;
import com.mygdx.game.map.blocks.GenerateMap;
import com.mygdx.game.map.blocks.Grass;

import javax.swing.Box;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    Player player;
    Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
    GenerateMap generateMap;


    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        generateMap = new GenerateMap();
        Body playerBody = BodyCreator.createBody(
                -100, -100,
                32, 60, false,
                myGdxGame.world
        );
        player = new Player(GameSettings.PLAYER_WIDTH, GameSettings.PLAYER_HEIGHT, playerBody, myGdxGame);
    }

    @Override
    public void render(float delta) {
        myGdxGame.stepWorld();
        player.update();
        draw();

    }

    public void draw() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        myGdxGame.batch.begin();
        drawBlocks();



        myGdxGame.batch.end();
        box2DDebugRenderer.render(myGdxGame.world, myGdxGame.camera.combined);

    }

private void drawBlocks() {
    for (int i = 0; i < 200; ++i) {
        for (int k = 0; k < 1000; ++k) {
            if (Math.abs(i*GameSettings.BLOCK_WIDTH*GameSettings.OBJECT_SCALE - myGdxGame.camera.position.x) < 500 && Math.abs(k*GameSettings.BLOCK_WIDTH*GameSettings.OBJECT_SCALE - myGdxGame.camera.position.y) < 500) {
                myGdxGame.batch.draw(generateMap.mapArray[i][k].getTexture(),
                        i*GameSettings.BLOCK_WIDTH*GameSettings.OBJECT_SCALE, k*GameSettings.BLOCK_WIDTH*GameSettings.OBJECT_SCALE,
                        GameSettings.BLOCK_WIDTH*GameSettings.OBJECT_SCALE,
                        GameSettings.BLOCK_WIDTH*GameSettings.OBJECT_SCALE
                );
            }
        }
    }

    }

}
