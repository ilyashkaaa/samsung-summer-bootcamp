package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.BodyCreator;

import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

import com.mygdx.game.entities.Player;
import com.mygdx.game.map.blocks.BasicBlock;
import com.mygdx.game.map.blocks.BlocksCollision;
import com.mygdx.game.map.blocks.GenerateMap;

import com.mygdx.game.UI.Joystick;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    Player player;
    Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
    GenerateMap generateMap;
    Joystick joystick;
    BlocksCollision blocksCollision;
    Vector3 touchPos;
    float touchX, touchY;
    boolean keepTouching;



    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        joystick = new Joystick();
        generateMap = new GenerateMap();
        blocksCollision = new BlocksCollision(myGdxGame);

        BlocksCollision.generateCollision(generateMap.mapArray);
        Body playerBody = BodyCreator.createBody(
                0, GameSettings.MAP_HEIGHT * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE + 60,
                GameSettings.PLAYER_WIDTH, GameSettings.PLAYER_HEIGHT, false,
                myGdxGame.world
        );
        player = new Player(GameSettings.PLAYER_WIDTH, GameSettings.PLAYER_HEIGHT, playerBody, myGdxGame);
        touchPos = new Vector3();
        myGdxGame.camera.position.set(0, GameSettings.MAP_HEIGHT * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE + 60, 0);
//        System.out.println(playerBody.getPosition() + "\t" + myGdxGame.camera.position);
    }

    @Override
    public void render(float delta) {
        myGdxGame.stepWorld();
        draw();

        Vector3 touch = new Vector3(Gdx.input.getX(indexJoystick(countOfTouching())),
                Gdx.input.getY(indexJoystick(countOfTouching())), 0
        );
        myGdxGame.camera.unproject(touch);
//        myGdxGame.camera.position.set(new Vector3(50000, 0, 0));
        if (Gdx.input.isTouched(indexJoystick(countOfTouching())) && myGdxGame.camera.position.x - touch.x >= 0) {
            if (!keepTouching) {
                joystick.changeRelativeCords(touch.sub(myGdxGame.camera.position));
            }
            player.setMoveVector(joystick.getDirection(touch, myGdxGame.camera.position));
            keepTouching = true;
        } else {
            player.setMoveVector(new Vector2(0, 0));
            keepTouching = false;
        }
    }

    public void draw() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        BlocksCollision.deleteBlocks();
        myGdxGame.batch.begin();
        drawBlocks();
        player.draw(myGdxGame.batch);

        if(keepTouching){
            Vector3 touch = new Vector3(Gdx.input.getX(indexJoystick(countOfTouching())),
                    Gdx.input.getY(indexJoystick(countOfTouching())), 0
            );
            myGdxGame.camera.unproject(touch);
            joystick.draw(myGdxGame.batch, myGdxGame.camera.position, touch);
        }

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
        for (int i = 0; i < GameSettings.MAP_WIDTH; ++i) {
            for (int k = 0; k < GameSettings.MAP_HEIGHT; ++k) {
                if (Math.abs(i * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE - myGdxGame.camera.position.x) < 500
                        && Math.abs(k * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE - myGdxGame.camera.position.y) < 500) {
                    if (touchX >= i * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE
                            && touchX < (i + 1) * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE
                            && touchY >= k * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE
                            && touchY < (k + 1) * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE) {
                        generateMap.mapArray[i][k].setDurability(0);
                        BlocksCollision.updateCollision(generateMap.mapArray, i, k);
                    }
                    if (generateMap.mapArray[i][k].getDurability() != 0) {
                        myGdxGame.batch.draw(generateMap.mapArray[i][k].getTexture(),
                                i * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE, k * GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE,
                                GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE,
                                GameSettings.BLOCK_WIDTH * GameSettings.OBJECT_SCALE
                        );
                        if (generateMap.mapArray[i][k].getHasCollision()) {
                            BlocksCollision.bodyArray.add(BasicBlock.createStaticBody(i, k, myGdxGame));
                        }

                    }

                }
            }
        }

    }
    private int indexJoystick(int countOfTouching) {
        int returned = 0;
        for (int i = 0; i < countOfTouching + 1; i++) {
            if (Gdx.input.getX(i) <= GameSettings.SCR_WIDTH / 2) {
                returned = i;
                break;
            }
        }
        return returned;
    }
    private int countOfTouching() {
        int i = 0;
        while (i < Gdx.input.getMaxPointers()) {
            i++;
            if (Gdx.input.getPressure(i) == 0) break;
        }
        return i - 1;
    }
}
