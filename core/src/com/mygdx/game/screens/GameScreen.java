package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
import com.mygdx.game.uis.Button;
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
    Vector2 selectedBlock;
    Texture selectionBlock = new Texture("textures/blocks/wood.png");
    Button jumpButton;
    Button breakingButton;
    Button placeButton;
    int playerBlockCordX;
    int playerBlockCordY;
    long lastHit;
    boolean keepTouching;
    int viewBlocksX = 40;
    int viewBlocksY = 40;


    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        jumpButton = new Button("textures/joystick/joystick.png", "textures/blocks/stone.png",
                700, -300, (int) (100 * GameSettings.OBJECT_SCALE), (int) (50 * GameSettings.OBJECT_SCALE), (int) (50 * GameSettings.OBJECT_SCALE));
        breakingButton = new Button("textures/joystick/joystick.png", "textures/items/diamond_pickaxe.png",
                700, -50, (int) (100 * GameSettings.OBJECT_SCALE), (int) (100 * GameSettings.OBJECT_SCALE), (int) (100 * GameSettings.OBJECT_SCALE));
        placeButton = new Button("textures/joystick/joystick.png", "textures/blocks/mossyblock.png",
                700, 200, (int) (100 * GameSettings.OBJECT_SCALE), (int) (100 * GameSettings.OBJECT_SCALE), (int) (100 * GameSettings.OBJECT_SCALE));

        joystick = new Joystick();
        generateMap = new GenerateMap();
        blocksCollision = new BlocksCollision(myGdxGame);

        selectedBlock = new Vector2();

        lastHit = TimeUtils.millis();

        blocksCollision.generateCollision(generateMap.mapArray);
        Body playerBody = BodyCreator.createBody(
                GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE, ((GameSettings.MAP_HEIGHT + 1) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE),
                GameSettings.PLAYER_WIDTH * GameSettings.OBJECT_SCALE, GameSettings.PLAYER_HEIGHT * GameSettings.OBJECT_SCALE, false,
                myGdxGame.world
        );
        player = new Player(GameSettings.PLAYER_WIDTH, GameSettings.PLAYER_HEIGHT, playerBody, myGdxGame);
        myGdxGame.camera.position.set(0, GameSettings.MAP_HEIGHT * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE, 0);
    }

    @Override
    public void render(float delta) {
        myGdxGame.stepWorld();
        draw(delta);

        playerBlockCordX = (int) (player.getBody().getPosition().x / GameSettings.BLOCK_SIDE / GameSettings.OBJECT_SCALE);
        playerBlockCordY = (int) ((player.getBody().getPosition().y - 5) / GameSettings.BLOCK_SIDE / GameSettings.OBJECT_SCALE);

        Vector3 touch = new Vector3(Gdx.input.getX(indexJoystick(countOfTouching())),
                Gdx.input.getY(indexJoystick(countOfTouching())), 0
        );

        if (Gdx.input.isTouched(indexJoystick(countOfTouching())) && touch.x <= GameSettings.SCR_WIDTH / 2f) {
            if (!keepTouching)
                joystick.changeCords(new Vector2(touch.x, touch.y));
            selectedBlock = player.setMoveVector(joystick.getDirection(new Vector2(touch.x, touch.y)));
            keepTouching = true;
        } else {
            selectedBlock.setZero();
            player.updateCamera();
            keepTouching = false;
        }

//        if (Gdx.input.isTouched()) {
//            Vector3 touchPos = new Vector3 (Gdx.input.getX(), Gdx.input.getY(), 0);
//            myGdxGame.camera.unproject(touchPos);
//            int touchPosX = (int) (touchPos.x / GameSettings.BLOCK_SIDE / GameSettings.OBJECT_SCALE);
//            int touchPosY = (int) (touchPos.y / GameSettings.BLOCK_SIDE / GameSettings.OBJECT_SCALE);
//            if (touchPosX >= playerBlockCordX - 1 && touchPosX <= playerBlockCordX + 1 &&
//                    touchPosY >= playerBlockCordY - 1 && touchPosY <= playerBlockCordY + 2 &&
//                    !(touchPosX == playerBlockCordX + 1 && touchPosY == playerBlockCordY - 1) &&
//                    !(touchPosX == playerBlockCordX + 1 && touchPosY == playerBlockCordY + 2) &&
//                    !(touchPosX == playerBlockCordX - 1 && touchPosY == playerBlockCordY - 1) &&
//                    !(touchPosX == playerBlockCordX - 1 && touchPosY == playerBlockCordY + 2) &&
//                    touchPosX >= 0 && touchPosX < GameSettings.MAP_WIDTH &&
//                    touchPosY >= 0 && touchPosY < GameSettings.MAP_HEIGHT &&
//                    generateMap.mapArray[touchPosX][touchPosY] != null && TimeUtils.millis() - lastHit >= 200) {
//                lastHit = TimeUtils.millis();
//                if (!generateMap.mapArray[touchPosX][touchPosY].hit(1)) {
//                    generateMap.mapArray[touchPosX][touchPosY] = null;
//                    blocksCollision.updateCollision(generateMap.mapArray, touchPosX, touchPosY, true);
//                }
//            }
//        }

        if (Gdx.input.isTouched()) {
            int x = (int) (playerBlockCordX + selectedBlock.x);
            int y = (int) (playerBlockCordY + selectedBlock.y);
            if (buttonHandler(jumpButton))
                player.jump();
            if (buttonHandler(breakingButton)){
                if (x >= 0 && x < GameSettings.MAP_WIDTH && y >= 0 && y < GameSettings.MAP_HEIGHT &&
                        generateMap.mapArray[x][y] != null && TimeUtils.millis() - lastHit >= 200) {
                    lastHit = TimeUtils.millis();
                    if (!generateMap.mapArray[x][y].hit(1)) {
                        generateMap.mapArray[x][y] = null;
                        blocksCollision.updateCollision(generateMap.mapArray, x, y, true);
                    }
                }
            }
            if (buttonHandler(placeButton) && !selectedBlock.isZero() &&
                    playerBlockCordX >= 0 && playerBlockCordX < GameSettings.MAP_WIDTH &&
                    playerBlockCordY >= 1 && playerBlockCordY <= GameSettings.MAP_HEIGHT) {
                generateMap.mapArray[x][y] = new Mossy();
                generateMap.mapArray[x][y].setHasCollision(true);
                blocksCollision.updateCollision(generateMap.mapArray, playerBlockCordX, playerBlockCordY - 1, false);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            int x = (int) (playerBlockCordX + selectedBlock.x);
            int y = (int) (playerBlockCordY + selectedBlock.y);
            if (x >= 0 && x < GameSettings.MAP_WIDTH && y >= 0 && y < GameSettings.MAP_HEIGHT &&
                    generateMap.mapArray[x][y] != null && TimeUtils.millis() - lastHit >= 200) {
                lastHit = TimeUtils.millis();
                if (!generateMap.mapArray[x][y].hit(1)) {
                    generateMap.mapArray[x][y] = null;
                    blocksCollision.updateCollision(generateMap.mapArray, x, y, true);
                }
            }
        }

        if (playerBlockCordX >= 0 && playerBlockCordX < GameSettings.MAP_WIDTH &&
                playerBlockCordY >= 1 && playerBlockCordY <= GameSettings.MAP_HEIGHT) {
            if (generateMap.mapArray[playerBlockCordX][playerBlockCordY - 1] != null || player.getBody().getLinearVelocity().y == 0) {
                player.setJumpClickClack(true);
            }
        }
    }

    public void draw(float delta) {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        blocksCollision.deleteBlocks();
        myGdxGame.batch.begin();
        drawBlocks();
        if (!selectedBlock.isZero())
            myGdxGame.batch.draw(selectionBlock,
                    (selectedBlock.x + playerBlockCordX) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE,
                    (selectedBlock.y + playerBlockCordY) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE,
                    GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE, GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE);
        player.draw(myGdxGame.batch);

        jumpButton.draw(myGdxGame.batch, myGdxGame.camera.position);
        breakingButton.draw(myGdxGame.batch, myGdxGame.camera.position);
        placeButton.draw(myGdxGame.batch, myGdxGame.camera.position);

        if (keepTouching) {
            Vector2 touch = new Vector2(Gdx.input.getX(indexJoystick(countOfTouching())),
                    Gdx.input.getY(indexJoystick(countOfTouching()))
            );
            joystick.draw(myGdxGame.batch, myGdxGame.camera.position, touch);
        }


//****************** FOR FPS *******************************
//        font.draw(myGdxGame.batch, (1 / delta) + "", myGdxGame.camera.position.x, myGdxGame.camera.position.y);
//**********************************************************


        myGdxGame.batch.end();
        box2DDebugRenderer.render(myGdxGame.world, myGdxGame.camera.combined);

    }

    private void drawBlocks() {
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
//                    if (touchPos.x >= (playerBlockCordX - viewBlocksX / 2f + i) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE
//                            && touchPos.x < (playerBlockCordX - viewBlocksX / 2f + i + 1) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE
//                            && touchPos.y >= (playerBlockCordY - viewBlocksY / 2f + k) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE
//                            && touchPos.y < (playerBlockCordY - viewBlocksY / 2f + k + 1) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE) {
//                        generateMap.mapArray[playerBlockCordX - viewBlocksX / 2 + i][playerBlockCordY - viewBlocksY / 2 + k] = null;
//                        blocksCollision.updateCollision(generateMap.mapArray, playerBlockCordX - viewBlocksX / 2 + i, playerBlockCordY - viewBlocksY / 2 + k, true);
//                    }
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
    private boolean buttonHandler (Button button){
        for (int i = 0; i <= countOfTouching(); i++) {
            if (button.isPressed(new Vector2(Gdx.input.getX(i), Gdx.input.getY(i))))
                return true;
        }
        return false;
    }
}
