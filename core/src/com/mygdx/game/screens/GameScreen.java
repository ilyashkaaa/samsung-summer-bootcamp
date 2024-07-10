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

import com.mygdx.game.GameResources;
import com.mygdx.game.GameSettings;
import com.mygdx.game.map.blocks.MapBorder;
import com.mygdx.game.MovingBackground;
import com.mygdx.game.MyGdxGame;

import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.PlayerStates;
import com.mygdx.game.map.blocks.BasicBlock;
import com.mygdx.game.map.blocks.BlocksCollision;
import com.mygdx.game.map.blocks.GenerateMap;
import com.mygdx.game.markets.UpdateMarket;
import com.mygdx.game.pickaxes.BasicPickaxe;
import com.mygdx.game.uis.Button;
import com.mygdx.game.uis.CameraMovement;
import com.mygdx.game.uis.Joystick;
import com.mygdx.game.uis.backpack.BackpackUI;

import java.lang.reflect.InvocationTargetException;

import com.mygdx.game.markets.BasicMarket;
import com.mygdx.game.markets.FoodMarket;
import com.mygdx.game.markets.SellMarket;
import com.mygdx.game.pickaxes.DiamondPickaxe;
import com.mygdx.game.pickaxes.GoldPickaxe;


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
    Vector2 selectedBlock;
    Texture selectionBlock = new Texture("textures/blocks/selected_block.png");
    Button pauseButton;
    Button jumpButton;
    Button actionButton;
    BackpackUI backpackUI;
    CameraMovement cameraMovement;
    BasicMarket[] markets;
    String actionClassName;

    int playerBlockCordX;
    int playerBlockCordY;
    long lastHit;
    boolean keepTouching;
    boolean backpackToggle;
    boolean toggleActionButton;
    boolean needToResetActionButton;
    boolean needToResetExitInMarketButton;



    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        cameraMovement = new CameraMovement(myGdxGame);
        movingBackgroundSky = new MovingBackground(GameResources.SKY);

        generateMap = new GenerateMap();

        markets = new BasicMarket[]{new SellMarket(24.5f), new FoodMarket(12.5f), new UpdateMarket(18.5f)};

//        sellMarket = new SellMarket(6.5f, generateMap.mapArray);
//        foodMarket = new FoodMarket(10.5f, generateMap.mapArray);

        jumpButton = new Button(GameResources.JUMP_BUTTON, 700, -300, (int) (200 * GameSettings.OBJECT_SCALE));
        actionButton = new Button(GameResources.JOYSTICK_BACKGROUND_TEXTURE, GameResources.DIAMOND_PICKAXE,
                700, -50, (int) (200 * GameSettings.OBJECT_SCALE), (int) (100 * GameSettings.OBJECT_SCALE), (int) (100 * GameSettings.OBJECT_SCALE));
//        placeButton = new Button("textures/joystick/joystick.png", "textures/blocks/stone/mossyblock.png",
//                700, 200, (int) (200 * GameSettings.OBJECT_SCALE), (int) (100 * GameSettings.OBJECT_SCALE), (int) (100 * GameSettings.OBJECT_SCALE));
        pauseButton = new Button(GameResources.PAUSE_BUTTON, 900, 470, GameResources.PAUSE_BUTTON.getWidth()*GameSettings.SCALE, GameResources.PAUSE_BUTTON.getHeight()*GameSettings.SCALE);

        joystick = new Joystick();

        blocksCollision = new BlocksCollision(myGdxGame);

        mapBorder = new MapBorder(myGdxGame.world);

        backpackUI = new BackpackUI(myGdxGame);

        selectedBlock = new Vector2();

        lastHit = TimeUtils.millis();

        blocksCollision.generateCollision(generateMap.mapArray);
        Body playerBody = BodyCreator.createBody(
                GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE * 20, ((GameSettings.MAP_HEIGHT + 1) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE),
                GameSettings.PLAYER_WIDTH * GameSettings.OBJECT_SCALE, GameSettings.PLAYER_HEIGHT * GameSettings.OBJECT_SCALE, false,
                myGdxGame.world
        );
        player = new Player(GameSettings.PLAYER_WIDTH, GameSettings.PLAYER_HEIGHT, playerBody, myGdxGame, GoldPickaxe.class);
        // myGdxGame.camera.position.set(0, GameSettings.MAP_HEIGHT * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE, 0);
        mapBorder.createMapBorder(GameSettings.MAP_WIDTH * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE, (GameSettings.MAP_HEIGHT + 10) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE);

        backpackUI.addItemInInventory(player.pickaxe);


    }

    @Override
    public void show(){
        player.updateCamera();
    }

    @Override
    public void render(float delta) {
        myGdxGame.stepWorld();
        draw(delta);

        if (!player.isJumping && !player.falling && !player.fell)
            player.playerState = PlayerStates.STANDING;

        cameraMovement.move(player.getBody().getPosition());

        actionClassName = nameOfMarketNearBy(markets);

        if (nameOfMarketNearBy(markets) == null) {
            toggleActionButton = false;
            if (backpackUI.slotsInventoryItem.get(backpackUI.selectionIndex).item instanceof BasicBlock) {
                actionButton.changeItem(backpackUI.slotsInventoryItem.get(backpackUI.selectionIndex).item.getTexture());
            } else if (backpackUI.slotsInventoryItem.get(backpackUI.selectionIndex).item instanceof BasicPickaxe) {
                actionButton.changeItem(backpackUI.slotsInventoryItem.get(backpackUI.selectionIndex).item.getTexture());
            }
        } else {
            toggleActionButton = true;
            actionButton.changeItem(GameResources.ACTION_BUTTON);
        }

        playerBlockCordX = (int) (player.getBody().getPosition().x / GameSettings.BLOCK_SIDE / GameSettings.OBJECT_SCALE);
        playerBlockCordY = (int) ((player.getBody().getPosition().y - 5) / GameSettings.BLOCK_SIDE / GameSettings.OBJECT_SCALE);

        Vector3 touch = new Vector3(Gdx.input.getX(indexJoystick(countOfTouching())),
                Gdx.input.getY(indexJoystick(countOfTouching())), 0
        );
        if (!backpackUI.backpackOpen && !isOneInMarket(markets)) {
            if (Gdx.input.isTouched(indexJoystick(countOfTouching())) && touch.x <= GameSettings.SCR_WIDTH / 2f) {
                if (!keepTouching)
                    joystick.changeCords(new Vector2(touch.x, touch.y));
                selectedBlock = player.setMoveVector(joystick.getDirection(new Vector2(touch.x, touch.y)));
                keepTouching = true;
                if (player.playerState == PlayerStates.STANDING && !player.isJumping) {
                    player.playerState = PlayerStates.WALKING;
                }
            } else {
                if (player.playerState == PlayerStates.WALKING && !player.isJumping) {
                    player.playerState = PlayerStates.STANDING;
                }
//            selectedBlock.setZero();
                keepTouching = false;
            }
        }
//        player.updateCamera();
        player.playerBreak();

        if (Gdx.input.isTouched()) {
            int x = (int) (playerBlockCordX + selectedBlock.x);
            int y = (int) (playerBlockCordY + selectedBlock.y);

            if (markets[0].inMarket) {
                if (((SellMarket) markets[0]).inMenu) {
                    if (buttonHandler(((SellMarket) markets[0]).buyButton)) {
                        ((SellMarket) markets[0]).inMenu = false;
                        ((SellMarket) markets[0]).inBuy = true;
                    } else if (buttonHandler(((SellMarket) markets[0]).sellButton)) {
                        ((SellMarket) markets[0]).inMenu = false;
                        ((SellMarket) markets[0]).inSell = true;
                    } else if (buttonHandler(markets[0].exitButton) && !needToResetExitInMarketButton) {
                        markets[0].inMarket = false;
                        ((SellMarket) markets[0]).inMenu = true;
                        ((SellMarket) markets[0]).inBuy = false;
                        ((SellMarket) markets[0]).inSell = false;
                    }
                } else if (((SellMarket) markets[0]).inBuy) {
                    if (buttonHandler(((SellMarket) markets[0]).diamondPickaxe)) {
                        player.setPickaxe(DiamondPickaxe.class);
                        backpackUI.setItem(0, player.pickaxe);
                    } else if (buttonHandler(((SellMarket) markets[0]).goldPickaxe)) {
                        player.setPickaxe(GoldPickaxe.class);
                        backpackUI.setItem(0, player.pickaxe);
                    } else if (buttonHandler(markets[0].exitButton) && !needToResetExitInMarketButton) {
                        ((SellMarket) markets[0]).inBuy = false;
                        ((SellMarket) markets[0]).inMenu = true;
                    }
                } else if (((SellMarket) markets[0]).inSell) {
                    if (buttonHandler(markets[0].exitButton) && !needToResetExitInMarketButton) {
                        ((SellMarket) markets[0]).inSell = false;
                        ((SellMarket) markets[0]).inMenu = true;
                    }
                }
            } else if (markets[1].inMarket) {
                if (buttonHandler(markets[1].exitButton)) {
                    markets[1].inMarket = false;
                }
            } else if (markets[2].inMarket) {
                if (buttonHandler(markets[2].exitButton)) {
                    markets[2].inMarket = false;
                }
            } else {

                if (backpackUI.backpackOpen) {
                    for (int i = 0; i < backpackUI.backpackSlots.length; i++)
                        if (buttonHandler(backpackUI.backpackSlots[i]) && i < backpackUI.slotsInventoryItem.size()) {
                            backpackUI.cancelSelection();
                            backpackUI.backpackSlots[i].changeButtonTexture(GameResources.SELECTED_BLOCK);
                            backpackUI.selectionIndex = i;
                            break;
                        }
                }

                if (buttonHandler(backpackUI.backpackButton))
                    backpackToggle = true;
                if (!buttonHandler(backpackUI.backpackButton) && backpackToggle) {
                    backpackToggle = false;
                    backpackUI.backpackOpen = !backpackUI.backpackOpen;
                }
                if (buttonHandler(pauseButton)) {
                    myGdxGame.pauseScreen.returnToPause = true;
                    myGdxGame.setScreen(myGdxGame.pauseScreen);
                }

                if (!backpackUI.backpackOpen) {
                    if (buttonHandler(jumpButton))
                        player.jump();

                    if (buttonHandler(actionButton)) {
                        if (!needToResetActionButton) {
                            if (!toggleActionButton) {
                                if (backpackUI.slotsInventoryItem.get(backpackUI.selectionIndex).item instanceof BasicPickaxe) {
                                        if (player.getBody().getLinearVelocity().y == 0) {
                                            player.drawDigging(selectedBlock.x, selectedBlock.y);
                                        } else if (!player.isJumping && !player.falling) {
//                                            player.playerState = PlayerStates.STANDING;
                                        }
                                        if (x >= 0 && x < GameSettings.MAP_WIDTH && y >= 0 && y < GameSettings.MAP_HEIGHT
                                                && generateMap.mapArray[x][y] != null && TimeUtils.millis() - lastHit >= 200
                                                && player.playerState != PlayerStates.JUMPING && player.playerState != PlayerStates.FALLING) {
                                            lastHit = TimeUtils.millis();
                                            if (!generateMap.mapArray[x][y].hit(player.pickaxe.getDamage())) {
//                            backpackUI.blocksInventory.add(generateMap.mapArray[x][y].getTexture());

                                                backpackUI.addItemInInventory(generateMap.mapArray[x][y]);

                                                generateMap.mapArray[x][y] = null;
                                                blocksCollision.updateCollision(generateMap.mapArray, x, y, true);
                                            }
                                        }
                                } else if (backpackUI.slotsInventoryItem.get(backpackUI.selectionIndex).item instanceof BasicBlock) {
                                    if (!selectedBlock.isZero() &&
                                            x >= 0 && x < GameSettings.MAP_WIDTH && y >= 0 && y < GameSettings.MAP_HEIGHT)
                                        if (generateMap.mapArray[x][y] == null)
                                            try {
                                                generateMap.mapArray[x][y] = ( (BasicBlock)backpackUI.slotsInventoryItem.get(backpackUI.selectionIndex).item).getClass().getConstructor().newInstance();
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
                                switch (actionClassName) {
                                    case "SellMarket":
//                                    player.setPickaxe(DiamondPickaxe.class);
                                        markets[0].inMarket = true;
                                        System.out.println(0);
//                                    System.out.println("sell");
                                        break;
                                    case "FoodMarket":
//                                    player.setPickaxe(GoldPickaxe.class);
                                        markets[1].inMarket = true;
                                        System.out.println(1);
//                                    System.out.println("food");
                                        break;
                                    case "UpdateMarket":
//                                    player.setPickaxe(GoldPickaxe.class);
                                        markets[2].inMarket = true;
                                        System.out.println(2);
//                                    System.out.println("food");
                                        break;
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
            needToResetExitInMarketButton = isExitButtonInMarketPressed(markets);
        } else {

            needToResetExitInMarketButton = false;
            needToResetActionButton = false;
            if (backpackToggle) {
                backpackToggle = false;
                backpackUI.backpackOpen = !backpackUI.backpackOpen;
            }

        }


        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
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
            if (generateMap.mapArray[playerBlockCordX][playerBlockCordY - 1] != null && player.getBody().getLinearVelocity().y == 0) {
                player.setJumpClickClack(true);
            }
        }
        else
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
        if (!selectedBlock.isZero())
            myGdxGame.batch.draw(selectionBlock,
                    (selectedBlock.x + playerBlockCordX) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE,
                    (selectedBlock.y + playerBlockCordY) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE,
                    GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE, GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE);

        jumpButton.draw(myGdxGame.batch, cameraPos);
        actionButton.draw(myGdxGame.batch, cameraPos);
//        placeButton.draw(myGdxGame.batch, cameraPos);
        pauseButton.draw(myGdxGame.batch, cameraPos);

        if (keepTouching) {
            Vector2 touch = new Vector2(Gdx.input.getX(indexJoystick(countOfTouching())),
                    Gdx.input.getY(indexJoystick(countOfTouching()))
            );
            joystick.draw(myGdxGame.batch, cameraPos, touch);
        }

        backpackUI.draw(myGdxGame.batch, cameraPos);

        if (markets[0].inMarket)
            markets[0].drawInterface(cameraPos, myGdxGame);
        if (markets[1].inMarket)
            markets[1].drawInterface(cameraPos, myGdxGame);
        if (markets[2].inMarket)
            markets[2].drawInterface(cameraPos, myGdxGame);

//****************** FOR FPS *******************************
//      font.draw(myGdxGame.batch, (1 / delta) + "", myGdxGame.camera.position.x, myGdxGame.camera.position.y);
//**********************************************************


        myGdxGame.batch.end();
       // box2DDebugRenderer.render(myGdxGame.world, myGdxGame.camera.combined);

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
                    } else {
                        myGdxGame.batch.draw(GameResources.STONE_BLOCK_BACKGROUND,
                                (playerBlockCordX - viewBlocksX / 2f + i) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE,
                                (playerBlockCordY - viewBlocksY / 2f + k) * GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE,
                                GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE,
                                GameSettings.BLOCK_SIDE * GameSettings.OBJECT_SCALE);
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

    private String nameOfMarketNearBy(BasicMarket[] markets) {
        for (BasicMarket market : markets)
            if (market.isNearBy(player.getBody().getPosition()))
                return market.getClass().getSimpleName();
        return null;
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

    private boolean isExitButtonInMarketPressed(BasicMarket[] markets) {
        for (BasicMarket market : markets) {
            if (buttonHandler(market.exitButton))
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

    private int countOfTouching() {
        int i = 0;
        while (i < Gdx.input.getMaxPointers()) {
            i++;
            if (Gdx.input.getPressure(i) == 0) break;
        }
        return i - 1;
    }

    public boolean buttonHandler(Button button) {
        for (int i = 0; i <= countOfTouching(); i++) {
            if (button.isPressed(new Vector2(Gdx.input.getX(i), Gdx.input.getY(i))))
                return true;
        }
        return false;
    }
}
