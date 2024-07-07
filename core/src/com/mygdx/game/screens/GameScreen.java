package com.mygdx.game.screens;

import static com.mygdx.game.GameSettings.viewBlocksX;
import static com.mygdx.game.GameSettings.viewBlocksY;

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
import com.mygdx.game.MapBorder;
import com.mygdx.game.MyGdxGame;

import com.mygdx.game.entities.GameEntity;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.PlayerStates;
import com.mygdx.game.map.blocks.BasicBlock;
import com.mygdx.game.map.blocks.BlocksCollision;
import com.mygdx.game.map.blocks.GenerateMap;
import com.mygdx.game.map.blocks.Mossy;
import com.mygdx.game.uis.Button;
import com.mygdx.game.uis.Joystick;
import com.mygdx.game.uis.backpack.BackpackUI;


public class GameScreen extends ScreenAdapter {


//****************** FOR FPS********************************
   public static BitmapFont font = new BitmapFont();
//***********************************************************


    MyGdxGame myGdxGame;
    Player player;
    Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
    GenerateMap generateMap;
    MapBorder mapBorder;
    Joystick joystick;
    BlocksCollision blocksCollision;
    Vector2 selectedBlock;
    Texture selectionBlock = new Texture("textures/blocks/selected_block.png");
    Button jumpButton;
    Button breakingButton;
    Button placeButton;
    int playerBlockCordX;
    int playerBlockCordY;
    long lastHit;
    boolean keepTouching;
    boolean backpackToggle;

    BackpackUI backpackUI;


    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        jumpButton = new Button("textures/buttons/main_screen/jump_button_on.png", 700, -300, (int) (200 * GameSettings.OBJECT_SCALE));
        breakingButton = new Button("textures/joystick/joystick.png", "textures/items/pickaxes/diamond_pickaxe.png",
                700, -50, (int) (200 * GameSettings.OBJECT_SCALE), (int) (100 * GameSettings.OBJECT_SCALE), (int) (100 * GameSettings.OBJECT_SCALE));
        placeButton = new Button("textures/joystick/joystick.png", "textures/blocks/stone/mossyblock.png",
                700, 200, (int) (200 * GameSettings.OBJECT_SCALE), (int) (100 * GameSettings.OBJECT_SCALE), (int) (100 * GameSettings.OBJECT_SCALE));

        joystick = new Joystick();
        
        generateMap = new GenerateMap();
        
        blocksCollision = new BlocksCollision(myGdxGame);

        mapBorder = new MapBorder(myGdxGame.world);
        
        backpackUI = new BackpackUI(myGdxGame);

        selectedBlock = new Vector2();

        lastHit = TimeUtils.millis();

        blocksCollision.generateCollision(generateMap.mapArray);
        Body playerBody = BodyCreator.createBody(
                GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE*20, ((GameSettings.MAP_HEIGHT + 1) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE),
                GameSettings.PLAYER_WIDTH * GameSettings.OBJECT_SCALE, GameSettings.PLAYER_HEIGHT * GameSettings.OBJECT_SCALE, false,
                myGdxGame.world
        );
        mapBorder.createMapBorder(GameSettings.MAP_WIDTH*GameSettings.BLOCK_SIDE*GameSettings.OBJECT_SCALE, (GameSettings.MAP_HEIGHT+10)*GameSettings.BLOCK_SIDE*GameSettings.OBJECT_SCALE);
        player = new Player(GameSettings.PLAYER_WIDTH, GameSettings.PLAYER_HEIGHT, playerBody, myGdxGame);
        //myGdxGame.camera.position.set(0, GameSettings.MAP_HEIGHT * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE, 0);
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
            if (player.playerState == PlayerStates.STANDING) {
                player.playerState = PlayerStates.WALKING;
            }
        } else {
            if (player.playerState == PlayerStates.WALKING ) {
                player.playerState = PlayerStates.STANDING;
            }
//            selectedBlock.setZero();
            player.updateCamera();
            keepTouching = false;
        }

        if (Gdx.input.isTouched()) {
            int x = (int) (playerBlockCordX + selectedBlock.x);
            int y = (int) (playerBlockCordY + selectedBlock.y);

            if (buttonHandler(backpackUI.backpackButton))
                backpackToggle = true;
            if (!buttonHandler(backpackUI.backpackButton) && backpackToggle){
                backpackToggle = false;
                backpackUI.backpackOpen = !backpackUI.backpackOpen;
            }

            if (buttonHandler(jumpButton))
                player.jump();
            if (buttonHandler(breakingButton)){
                if (player.getBody().getLinearVelocity().y==0) {
                    player.drawDigging(x, y, playerBlockCordX, playerBlockCordY);
                } else {
                    player.playerState = PlayerStates.STANDING;
                }
                if (x >= 0 && x < GameSettings.MAP_WIDTH && y >= 0 && y < GameSettings.MAP_HEIGHT
                        && generateMap.mapArray[x][y] != null && TimeUtils.millis() - lastHit >= 200
                        && player.playerState != PlayerStates.JUMPING && player.playerState != PlayerStates.FALLING) {
                    lastHit = TimeUtils.millis();
                    if (!generateMap.mapArray[x][y].hit(1)) {
                        backpackUI.blocksInventory.add(generateMap.mapArray[x][y].getTexture());
                        backpackUI.addBlockToInventory();
                        generateMap.mapArray[x][y] = null;
                        blocksCollision.updateCollision(generateMap.mapArray, x, y, true);
                    }
                }
            }

            if (buttonHandler(placeButton) && !selectedBlock.isZero() &&
                    x >= 0 && x < GameSettings.MAP_WIDTH && y >= 0 && y < GameSettings.MAP_HEIGHT) {
                generateMap.mapArray[x][y] = new Mossy();
                generateMap.mapArray[x][y].setHasCollision(true);
                blocksCollision.updateCollision(generateMap.mapArray, playerBlockCordX, playerBlockCordY - 1, false);
            }
        }
        else{
            player.playerState = PlayerStates.STANDING;
            if (backpackToggle) {
                backpackToggle = false;
                backpackUI.backpackOpen = !backpackUI.backpackOpen;
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
        player.draw(myGdxGame.batch);
        if (!selectedBlock.isZero())
            myGdxGame.batch.draw(selectionBlock,
                    (selectedBlock.x + playerBlockCordX) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE,
                    (selectedBlock.y + playerBlockCordY) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE,
                    GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE, GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE);

        jumpButton.draw(myGdxGame.batch, myGdxGame.camera.position);
        breakingButton.draw(myGdxGame.batch, myGdxGame.camera.position);
        placeButton.draw(myGdxGame.batch, myGdxGame.camera.position);

        if (keepTouching) {
            Vector2 touch = new Vector2(Gdx.input.getX(indexJoystick(countOfTouching())),
                    Gdx.input.getY(indexJoystick(countOfTouching()))
            );
            joystick.draw(myGdxGame.batch, myGdxGame.camera.position, touch);
        }

        backpackUI.draw(myGdxGame.batch);


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
