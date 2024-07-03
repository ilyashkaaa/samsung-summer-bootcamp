package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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

import UI.Joystick;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    Player player;
    Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
    GenerateMap generateMap;
    Joystick joystick;
    Vector3 touchPos;
    float touchX, touchY;


    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        joystick = new Joystick(0, 0, myGdxGame);
        generateMap = new GenerateMap();
        Body playerBody = BodyCreator.createBody(
                0, GameSettings.MAP_HEIGHT * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE + 60,
                32, 60, false,
                myGdxGame.world
        );
        player = new Player(GameSettings.PLAYER_WIDTH, GameSettings.PLAYER_HEIGHT, playerBody, myGdxGame);
        touchPos = new Vector3();

    }

    @Override
    public void render(float delta) {
        myGdxGame.stepWorld();
//        player.update();
        joystick.setX(player.getX());
        joystick.setY(player.getY());
        player.addMoveVector(joystick.getDirection());
//        System.out.println(new Vector2(9, 12).setLength(5));
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
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            myGdxGame.camera.unproject(touchPos);
            touchX = touchPos.x;
            touchY = touchPos.y;
        }
        for (int i = 0; i < 200; ++i) {
            for (int k = 0; k < 1000; ++k) {
                if (Math.abs(i * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE - myGdxGame.camera.position.x) < 500 && Math.abs(k * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE - myGdxGame.camera.position.y) < 500) {
                    if (touchX >= i * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE && touchX < (i+1) * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE
                            && touchY >= k * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE && touchY < (k+1) * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE) {
                        generateMap.mapArray[i][k].setDurability(0);
                    }
                    if (generateMap.mapArray[i][k].getDurability() != 0) {
                        myGdxGame.batch.draw(generateMap.mapArray[i][k].getTexture(),
                                i * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE, k * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE,
                                GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE,
                                GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE
                        );
                    }

                }
            }
        }

    }

}
