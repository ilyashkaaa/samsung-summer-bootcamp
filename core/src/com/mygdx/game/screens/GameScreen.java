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
import com.mygdx.game.AudioManager;
import com.mygdx.game.BodyCreator;

import com.mygdx.game.ButtonHandlerInterface;
import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MemoryManager;
import com.mygdx.game.map.blocks.MapBorder;
import com.mygdx.game.MovingBackground;
import com.mygdx.game.MyGdxGame;

import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.PlayerStates;
import com.mygdx.game.map.blocks.BasicBlock;
import com.mygdx.game.map.blocks.BlocksCollision;
import com.mygdx.game.map.blocks.GenerateMap;
import com.mygdx.game.markets.UpdateMarket;
import com.mygdx.game.pickaxes.Stick;
import com.mygdx.game.pickaxes.BasicPickaxe;
import com.mygdx.game.uis.Button;
import com.mygdx.game.uis.CameraMovement;
import com.mygdx.game.uis.Joystick;
import com.mygdx.game.uis.MoneyManager;
import com.mygdx.game.uis.TextEdit;
import com.mygdx.game.uis.TextView;
import com.mygdx.game.uis.backpack.BackpackUI;

import java.lang.reflect.InvocationTargetException;

import com.mygdx.game.markets.*;


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
    MovingBackground movingBackgroundSky;
    Texture selectionBlock = new Texture("textures/blocks/selected_block.png");
    Button pauseButton;
    Button jumpButton;
    Button actionButton;
    BackpackUI backpackUI;
    CameraMovement cameraMovement;
    BasicMarket[] markets;
    BasicMarket actionClassName;
    MoneyManager money;
    TextEdit textEdit;

    int playerBlockCordX;
    int playerBlockCordY;
    long lastHit;
    boolean keepTouching;
    boolean backpackToggle;
    boolean toggleActionButton;
    Boolean needToResetActionButton;

    ButtonHandlerInterface buttonHandle = button -> {
        for (int i = 0; i <= joystick.countOfTouching(); i++) {
            if (button.isPressed(new Vector2(Gdx.input.getX(i), Gdx.input.getY(i)))) {
                return true;
            }
        }
        return false;
    };


    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        textEdit = new TextEdit();
        money = new MoneyManager(500, 400, (int) (80*GameSettings.SCALE), (int) (80*GameSettings.SCALE));
        cameraMovement = new CameraMovement(myGdxGame);
        movingBackgroundSky = new MovingBackground(GameResources.SKY);

        markets = new BasicMarket[]{new SellMarket(94.5f, buttonHandle), new FoodMarket(82.5f, buttonHandle), new UpdateMarket(88.5f, buttonHandle)};

//        sellMarket = new SellMarket(6.5f, generateMap.mapArray);
//        foodMarket = new FoodMarket(10.5f, generateMap.mapArray);

        jumpButton = new Button(GameResources.JUMP_BUTTON, 700, -300, (int) (200 * GameSettings.OBJECT_SCALE));
        actionButton = new Button(GameResources.JOYSTICK_BACKGROUND_TEXTURE, GameResources.DIAMOND_PICKAXE,
                700, -50, (int) (200 * GameSettings.OBJECT_SCALE), (int) (100 * GameSettings.OBJECT_SCALE), (int) (100 * GameSettings.OBJECT_SCALE));
//        placeButton = new Button("textures/joystick/joystick.png", "textures/blocks/stone/mossyblock.png",
//                700, 200, (int) (200 * GameSettings.OBJECT_SCALE), (int) (100 * GameSettings.OBJECT_SCALE), (int) (100 * GameSettings.OBJECT_SCALE));
        pauseButton = new Button(GameResources.PAUSE_BUTTON, (int) (-GameSettings.SCR_WIDTH / 2 + GameResources.PAUSE_BUTTON.getWidth() / 2), (int) (GameSettings.SCR_HEIGHT / 2 - GameResources.PAUSE_BUTTON.getHeight() / 2), GameResources.PAUSE_BUTTON.getWidth() * GameSettings.SCALE, GameResources.PAUSE_BUTTON.getHeight() * GameSettings.SCALE);

        joystick = new Joystick();

        blocksCollision = new BlocksCollision(myGdxGame);

        mapBorder = new MapBorder(myGdxGame.world);

        backpackUI = new BackpackUI(myGdxGame);

        lastHit = TimeUtils.millis();

        Body playerBody = BodyCreator.createBody(
                GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE * 100, ((GameSettings.MAP_HEIGHT + 1) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE),
                GameSettings.PLAYER_WIDTH * GameSettings.OBJECT_SCALE, GameSettings.PLAYER_HEIGHT * GameSettings.OBJECT_SCALE, false,
                myGdxGame.world
        );
        player = new Player(GameSettings.PLAYER_WIDTH, GameSettings.PLAYER_HEIGHT, playerBody, myGdxGame, Stick.class);
        // myGdxGame.camera.position.set(0, GameSettings.MAP_HEIGHT * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE, 0);
        mapBorder.createMapBorder(GameSettings.MAP_WIDTH * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE, (GameSettings.MAP_HEIGHT + 10) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE);

        backpackUI.addItemInInventory(player.pickaxe);

    }

    @Override
    public void show() {
        generateMap = new GenerateMap();
        blocksCollision.generateCollision(generateMap.mapArray);
        player.updateCamera();
        if (MyGdxGame.name == null || MyGdxGame.name.equals("")){
            Gdx.input.getTextInput(textEdit, "Enter your name", "", "Please, enter your name");
        }
    }

    @Override
    public void render(float delta) {
        //System.out.println(MyGdxGame.name);
        myGdxGame.stepWorld();
        draw(delta);
        player.playSounds();

        if (!player.isJumping && !player.falling && !player.fell)
            player.playerState = PlayerStates.STANDING;

        cameraMovement.move(player.getBody().getPosition());

        actionClassName = nameOfMarketNearBy(markets);

        if (actionClassName == null) {
            toggleActionButton = false;
            if (backpackUI.getCurrentItem().item instanceof BasicBlock) {
                actionButton.changeItem(backpackUI.getCurrentItem().item.getTexture());
            } else if (backpackUI.getCurrentItem().item instanceof BasicPickaxe) {
                actionButton.changeItem(backpackUI.getCurrentItem().item.getTexture());
            }
        } else {
            toggleActionButton = true;
            actionButton.changeItem(GameResources.ACTION_BUTTON);
        }

        playerBlockCordX = (int) (player.getBody().getPosition().x / GameSettings.BLOCK_SIDE / GameSettings.OBJECT_SCALE);
        playerBlockCordY = (int) ((player.getBody().getPosition().y - 5) / GameSettings.BLOCK_SIDE / GameSettings.OBJECT_SCALE);

        if (!backpackUI.backpackOpen && !isOneInMarket(markets)) {
            keepTouching = joystick.joystickUpdate(player);
        }
//        player.updateCamera();
        player.playerBreak();

        for (BasicMarket market : markets) {
            if (market.inMarket) {
                market.doThing(player, backpackUI);
            }
        }

        if (Gdx.input.isTouched()) {
            isTouchUpdate();
        } else {
            for (BasicMarket market: markets) {
                market.needToReset = false;
            }
            if (backpackToggle) {
                backpackToggle = false;
                backpackUI.backpackOpen = !backpackUI.backpackOpen;
            }

        }

        if (playerBlockCordX >= 0 && playerBlockCordX < GameSettings.MAP_WIDTH &&
                playerBlockCordY >= 1 && playerBlockCordY <= GameSettings.MAP_HEIGHT) {
            if (generateMap.mapArray[playerBlockCordX][playerBlockCordY - 1] != null && player.getBody().getLinearVelocity().y == 0) {
                player.setJumpClickClack(true);
            }
        } else
            player.setJumpClickClack(false);
        movingBackgroundSky.move();
    }

    public void draw(float delta) {
        Vector3 cameraPos = myGdxGame.camera.position;

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        blocksCollision.deleteBlocks();

        myGdxGame.batch.begin();
        movingBackgroundSky.draw(myGdxGame.batch, myGdxGame);
        drawBlocks();

        for (BasicMarket market : markets) {
            market.draw(myGdxGame.batch);
        }

        player.draw(myGdxGame.batch, delta);
        if (!joystick.selectedBlock.isZero())
            myGdxGame.batch.draw(selectionBlock,
                    (joystick.selectedBlock.x + playerBlockCordX) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE,
                    (joystick.selectedBlock.y + playerBlockCordY) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE,
                    GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE, GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE);

        jumpButton.draw(myGdxGame.batch, cameraPos);
        actionButton.draw(myGdxGame.batch, cameraPos);
//        placeButton.draw(myGdxGame.batch, cameraPos);
        if (isOneInMarket(markets))
            money.draw(myGdxGame.batch, cameraPos);
        pauseButton.draw(myGdxGame.batch, cameraPos);

        if (keepTouching) {
            Vector2 touch = new Vector2(Gdx.input.getX(joystick.indexJoystick()),
                    Gdx.input.getY(joystick.indexJoystick())
            );
            joystick.draw(myGdxGame.batch, cameraPos, touch);
        }

        backpackUI.draw(myGdxGame.batch, cameraPos);

        for (BasicMarket market : markets) {
            if (market.inMarket) {
                market.drawInterface(cameraPos, myGdxGame);
            }
        }


//****************** FOR FPS *******************************
//      font.draw(myGdxGame.batch, (1 / delta) + "", myGdxGame.camera.position.x, myGdxGame.camera.position.y);
//**********************************************************
//        if (MyGdxGame.name != null)
            font.draw(myGdxGame.batch, MyGdxGame.name /*(1 / delta) + ""*/, myGdxGame.camera.position.x, myGdxGame.camera.position.y);


        myGdxGame.batch.end();
        // box2DDebugRenderer.render(myGdxGame.world, myGdxGame.camera.combined);

    }

    private void drawBlocks() {
        for (int i = 0; i < viewBlocksX; i++) {
            for (int k = 0; k < viewBlocksY; k++) {
                float size = GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE;
                int x = playerBlockCordX - viewBlocksX / 2 + i;
                int y = playerBlockCordY - viewBlocksY / 2 + k;

                if (x >= 0 && x < GameSettings.MAP_WIDTH && y >= 0 && y < GameSettings.MAP_HEIGHT) {
                    generateMap.mapArray[x][y].draw(myGdxGame.batch, x * size, y * size);
                    if (!generateMap.mapArray[x][y].isDestroyed()) {
                        if (generateMap.mapArray[x][y].getHasCollision()) {
                            blocksCollision.bodyArray.add(BasicBlock.createStaticBody(x, y, myGdxGame));
                        }
                    } else {
                        myGdxGame.batch.draw(GameResources.STONE_BLOCK_BACKGROUND, x * size, y * size, size, size);
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

    private BasicMarket nameOfMarketNearBy(BasicMarket[] markets) {
        for (BasicMarket market : markets)
            if (market.isNearBy(player.getBody().getPosition()))
                return market;
        return null;
    }

    private boolean isExitButtonInMarketPressed(BasicMarket[] markets) {
        for (BasicMarket market : markets) {
            if (buttonHandle.buttonHandler(market.exitButton))
                return true;
        }
        return false;
    }

    private boolean isOneInMarket(BasicMarket[] markets) {
        for (BasicMarket market : markets) {
            if (market.inMarket)
                return true;
        }
        return false;
    }

    @Override
    public void dispose() {
        selectionBlock.dispose();

    }

    private void isTouchUpdate() {
        int x = (int) (playerBlockCordX + joystick.selectedBlock.x);
        int y = (int) (playerBlockCordY + joystick.selectedBlock.y);

        if (backpackUI.backpackOpen) {
            for (int i = 0; i < backpackUI.backpackSlots.length; i++)
                if (buttonHandle.buttonHandler(backpackUI.backpackSlots[i]) && i < backpackUI.slotsInventoryItem.size()) {
                    backpackUI.cancelSelection();
                    backpackUI.backpackSlots[i].changeButtonTexture(GameResources.SELECTED_BLOCK);
                    backpackUI.selectionIndex = i;
                    break;
                }
        }

        if (buttonHandle.buttonHandler(backpackUI.backpackButton)) backpackToggle = true;
        if (!buttonHandle.buttonHandler(backpackUI.backpackButton) && backpackToggle) {
            backpackToggle = false;
            backpackUI.backpackOpen = !backpackUI.backpackOpen;
        }
        if (buttonHandle.buttonHandler(pauseButton)) {
            myGdxGame.pauseScreen.returnToPause = true;
            myGdxGame.setScreen(myGdxGame.pauseScreen);
        }

        if (!backpackUI.backpackOpen) {
            if (buttonHandle.buttonHandler(jumpButton))
                player.jump();

            if (buttonHandle.buttonHandler(actionButton) && player.getBody().getLinearVelocity().y == 0) {
                if (!needToResetActionButton) {
                    if (!toggleActionButton) {
                        if (backpackUI.getCurrentItem().item instanceof BasicPickaxe) {
                            if (player.getBody().getLinearVelocity().y == 0) {
                                player.drawDigging(joystick.selectedBlock.x, joystick.selectedBlock.y);
                            } else if (!player.isJumping && !player.falling) {
//                                            player.playerState = PlayerStates.STANDING;
                            }
                            if (x >= 0 && x < GameSettings.MAP_WIDTH && y >= 0 && y < GameSettings.MAP_HEIGHT
                                    && !generateMap.mapArray[x][y].isDestroyed() && TimeUtils.millis() - lastHit >= 200
                                    && player.playerState != PlayerStates.JUMPING && player.playerState != PlayerStates.FALLING) {
                                lastHit = TimeUtils.millis();
                                if (!generateMap.mapArray[x][y].hit(player.pickaxe.getDamage())) {
//                            backpackUI.blocksInventory.add(generateMap.mapArray[x][y].getTexture());

                                    backpackUI.addItemInInventory(generateMap.mapArray[x][y]);

                                    generateMap.mapArray[x][y].setDestroyed(true);
                                    Gdx.input.vibrate(30);
                                    blocksCollision.updateCollision(generateMap.mapArray, x, y, true);
                                }
                            }
                        } else if (backpackUI.getCurrentItem().item instanceof BasicBlock) {
                            if (!joystick.selectedBlock.isZero() &&
                                    x >= 0 && x < GameSettings.MAP_WIDTH && y >= 0 && y < GameSettings.MAP_HEIGHT)
                                if (generateMap.mapArray[x][y].isDestroyed())
                                    try {
                                        generateMap.mapArray[x][y] = ((BasicBlock) backpackUI.getCurrentItem().item).getClass().getConstructor().newInstance();
                                        needToResetActionButton = backpackUI.removeItemFromInventory(generateMap.mapArray[x][y].getClass());
                                        generateMap.mapArray[x][y].setHasCollision(true);
                                        blocksCollision.updateCollision(generateMap.mapArray, playerBlockCordX, playerBlockCordY - 1, false);
                                    } catch (InstantiationException |
                                             IllegalAccessException |
                                             InvocationTargetException |
                                             NoSuchMethodException e) {
                                        e.printStackTrace();
                                    }
                        }

                    } else {
                        if (actionClassName != null) {
                            actionClassName.inMarket = true;
                        }
//                    System.out.println(generateMap.mapArray[0][0].getClass().getGenericSuperclass());
//                    System.out.println(generateMap.mapArray[0][0].getClass().getSuperclass().getSimpleName());

                    }
                }
            } else
                needToResetActionButton = false;

//            if (buttonHandler(placeButton) && !selectedBlock.isZero() &&
//                    x >= 0 && x < GameSettings.MAP_WIDTH && y >= 0 && y < GameSettings.MAP_HEIGHT) {
//                generateMap.mapArray[x][y] = new Mossy();
//                generateMap.mapArray[x][y].setHasCollision(true);
//                blocksCollision.updateCollision(generateMap.mapArray, playerBlockCordX, playerBlockCordY - 1, false);
//            }
        }
    }
}
