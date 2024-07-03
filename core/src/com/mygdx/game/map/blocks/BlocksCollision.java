package com.mygdx.game.map.blocks;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;
import java.util.Iterator;

public class BlocksCollision {
    public static ArrayList<Body> bodyArray;
    static MyGdxGame myGdxGame;

    public BlocksCollision(MyGdxGame myGdxGame) {
        bodyArray = new ArrayList<>();
        this.myGdxGame = myGdxGame;

    }

    public static void updateCollision(BasicBlock[][] mapArray, int i, int k) {
        if (i + 1 < GameSettings.MAP_WIDTH) mapArray[i + 1][k].setHasCollision(true);
        if (i - 1 >= 0) mapArray[i - 1][k].setHasCollision(true);
        if (k + 1 < GameSettings.MAP_HEIGHT) mapArray[i][k + 1].setHasCollision(true);
        if (k - 1 >= 0) mapArray[i][k - 1].setHasCollision(true);
    }

    public static void generateCollision(BasicBlock[][] mapArray) {
        for (int i = 0; i < 200; i++) {
            mapArray[i][GameSettings.MAP_HEIGHT-1].setHasCollision(true);
        }

    }

    public static void deleteBlocks() {
        if (!bodyArray.isEmpty()) {
            Iterator<Body> iteratorBlock = bodyArray.iterator();
            while (iteratorBlock.hasNext()) {
                Body block = iteratorBlock.next();
                myGdxGame.world.destroyBody(block);
                iteratorBlock.remove();
            }
        }

    }
}
