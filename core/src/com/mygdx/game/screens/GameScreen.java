package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.BodyCreator;

import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

import com.mygdx.game.entities.Player;
import com.mygdx.game.map.blocks.BasicBlock;
import com.mygdx.game.map.blocks.BlocksCollision;
import com.mygdx.game.map.blocks.GenerateMap;
import com.mygdx.game.map.blocks.Mossy;
import com.mygdx.game.uis.Joystick;


public class GameScreen extends ScreenAdapter {


//****************** FOR FPS********************************
    BitmapFont font = new BitmapFont();
//***********************************************************


    MyGdxGame myGdxGame;
    Player player;
    Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
    GenerateMap generateMap;
    Joystick joystick;
    BlocksCollision blocksCollision;
    Vector3 touchPos;
    long lastHit;
    boolean keepTouching;
    int playerBlockCordX;
    int playerBlockCordY;
    int viewBlocksX = 40;
    int viewBlocksY = 40;


    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        joystick = new Joystick();
        generateMap = new GenerateMap();
        blocksCollision = new BlocksCollision(myGdxGame);

        lastHit = TimeUtils.millis();

        blocksCollision.generateCollision(generateMap.mapArray);
        Body playerBody = BodyCreator.createBody(
                GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE, ((GameSettings.MAP_HEIGHT + 1) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE),
                GameSettings.PLAYER_WIDTH * GameSettings.OBJECT_SCALE, GameSettings.PLAYER_HEIGHT * GameSettings.OBJECT_SCALE, false,
                myGdxGame.world
        );
        player = new Player(GameSettings.PLAYER_WIDTH, GameSettings.PLAYER_HEIGHT, playerBody, myGdxGame);
        touchPos = new Vector3();
        myGdxGame.camera.position.set(0, GameSettings.MAP_HEIGHT * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE, 0);
    }

    @Override
    public void render(float delta) {
        myGdxGame.stepWorld();
        draw(delta);


        playerBlockCordX = (int) (player.getBody().getPosition().x / GameSettings.BLOCK_SIDE / GameSettings.OBJECT_SCALE);
        playerBlockCordY = (int) (player.getBody().getPosition().y / GameSettings.BLOCK_SIDE / GameSettings.OBJECT_SCALE);
        Vector3 touch = new Vector3(Gdx.input.getX(indexJoystick(countOfTouching())),
                Gdx.input.getY(indexJoystick(countOfTouching())), 0
        );
        Vector2 blockToDigUp;

        if (Gdx.input.isTouched(indexJoystick(countOfTouching())) && touch.x <= GameSettings.SCR_WIDTH / 2f) {
            if (!keepTouching)
                joystick.changeCords(new Vector2(touch.x, touch.y));
            blockToDigUp = player.setMoveVector(joystick.getDirection(new Vector2(touch.x, touch.y)));
            int x = (int) (playerBlockCordX + blockToDigUp.x);
            int y = (int) (playerBlockCordY + blockToDigUp.y);
            if (x >= 0 && x < GameSettings.MAP_WIDTH && y >= 0 && y < GameSettings.MAP_HEIGHT &&
                    generateMap.mapArray[x][y] != null && TimeUtils.millis() - lastHit >= 200) {
                lastHit = TimeUtils.millis();
                 if (!generateMap.mapArray[x][y].hit(1)) {
                     generateMap.mapArray[x][y] = null;
                     blocksCollision.updateCollision(generateMap.mapArray, x, y, true);
                 }
            }
            keepTouching = true;
        } else {
            player.updateCamera();
            keepTouching = false;
        }

        if (playerBlockCordX >= 0 && playerBlockCordX < GameSettings.MAP_WIDTH &&
                playerBlockCordY >= 1 && playerBlockCordY <= GameSettings.MAP_HEIGHT) {
            if (generateMap.mapArray[playerBlockCordX][playerBlockCordY - 1] != null || player.getBody().getLinearVelocity().y==0) {
                player.setJumpClickClack(true);
            }
        }

        if (player.needToPlaceBlock() &&
                playerBlockCordX >= 0 && playerBlockCordX < GameSettings.MAP_WIDTH &&
                playerBlockCordY >= 1 && playerBlockCordY <= GameSettings.MAP_HEIGHT) {
            generateMap.mapArray[playerBlockCordX][playerBlockCordY - 1] = new Mossy();
            generateMap.mapArray[playerBlockCordX][playerBlockCordY - 1].setHasCollision(true);
            blocksCollision.updateCollision(generateMap.mapArray, playerBlockCordX, playerBlockCordY - 1, false);
        }
    }

    public void draw(float delta) {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        blocksCollision.deleteBlocks();
        myGdxGame.batch.begin();
        drawBlocks();
        player.draw(myGdxGame.batch);

        if (keepTouching) {
            Vector2 touch = new Vector2(Gdx.input.getX(indexJoystick(countOfTouching())),
                    Gdx.input.getY(indexJoystick(countOfTouching()))
            );
            joystick.draw(myGdxGame.batch, myGdxGame.camera.position, touch);
        }


//****************** FOR FPS *******************************
        font.draw(myGdxGame.batch, (1 / delta) + "", myGdxGame.camera.position.x, myGdxGame.camera.position.y);
//**********************************************************


        myGdxGame.batch.end();
        box2DDebugRenderer.render(myGdxGame.world, myGdxGame.camera.combined);

    }

    private void drawBlocks() {
        playerBlockCordX = (int) (player.getBody().getPosition().x / GameSettings.BLOCK_SIDE / GameSettings.OBJECT_SCALE);
        playerBlockCordY = (int) (player.getBody().getPosition().y / GameSettings.BLOCK_SIDE / GameSettings.OBJECT_SCALE);

        for (int i = 0; i < viewBlocksX; i++) {
            for (int k = 0; k < viewBlocksY; k++) {
                if (playerBlockCordX - viewBlocksX / 2 + i >= 0 && playerBlockCordX - viewBlocksX / 2 + i < GameSettings.MAP_WIDTH
                        && playerBlockCordY - viewBlocksY / 2 + k >= 0 && playerBlockCordY - viewBlocksY / 2 + k < GameSettings.MAP_HEIGHT) {

                    //drawing blocks
                    if (generateMap.mapArray[playerBlockCordX - viewBlocksX / 2 + i][playerBlockCordY - viewBlocksY / 2 + k] != null) {
                        generateMap.mapArray[playerBlockCordX - viewBlocksX / 2 + i][playerBlockCordY - viewBlocksY / 2 + k].draw(myGdxGame.batch,
                                (playerBlockCordX - viewBlocksX / 2f + i) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE,
                                (playerBlockCordY - viewBlocksY / 2f + k) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE
                                );
                        if (generateMap.mapArray[playerBlockCordX - viewBlocksX / 2 + i][playerBlockCordY - viewBlocksY / 2 + k].getHasCollision()) {
                            blocksCollision.bodyArray.add(BasicBlock.createStaticBody(playerBlockCordX - viewBlocksX / 2 + i, playerBlockCordY - viewBlocksY / 2 + k, myGdxGame));
                        }
                    }


                    //update collision for blocks
                    if (touchPos.x >= (playerBlockCordX - viewBlocksX / 2f + i) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE
                            && touchPos.x < (playerBlockCordX - viewBlocksX / 2f + i + 1) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE
                            && touchPos.y >= (playerBlockCordY - viewBlocksY / 2f + k) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE
                            && touchPos.y < (playerBlockCordY - viewBlocksY / 2f + k + 1) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE) {
                        generateMap.mapArray[playerBlockCordX - viewBlocksX / 2 + i][playerBlockCordY - viewBlocksY / 2 + k] = null;
                        blocksCollision.updateCollision(generateMap.mapArray, playerBlockCordX - viewBlocksX / 2 + i, playerBlockCordY - viewBlocksY / 2 + k, true);
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
