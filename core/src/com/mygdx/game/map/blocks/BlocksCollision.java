package com.mygdx.game.map.blocks;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;
import java.util.Iterator;

public class BlocksCollision {
    public ArrayList<Body> bodyArray;
    MyGdxGame myGdxGame;

    public BlocksCollision(MyGdxGame myGdxGame) {
        bodyArray = new ArrayList<>();
        this.myGdxGame = myGdxGame;

    }

    public void updateCollision(BasicBlock[][] mapArray, int x, int y, boolean needCollision) {
        if (needCollision) {
            if (x + 1 < GameSettings.MAP_WIDTH)
                if (!mapArray[x + 1][y].isDestroyed()) mapArray[x + 1][y].setHasCollision(true);
            if (x - 1 >= 0)
                if (!mapArray[x - 1][y].isDestroyed()) mapArray[x - 1][y].setHasCollision(true);
            if (y + 1 < GameSettings.MAP_HEIGHT)
                if (!mapArray[x][y + 1].isDestroyed()) mapArray[x][y + 1].setHasCollision(true);
            if (y - 1 >= 0)
                if (!mapArray[x][y - 1].isDestroyed()) mapArray[x][y - 1].setHasCollision(true);
        }
        else {
            for (int i = 0; i < 4; i++) {
                int blockX = x + (1 - i) * ((i + 1) % 2);
                int blockY = y + (2 - i) * (i % 2);
                if (blockX >= 0 && blockX < GameSettings.MAP_WIDTH && blockY >= 0 && blockY < GameSettings.MAP_HEIGHT)
                    if (!hasBlockNearBy(mapArray, blockX, blockY) && !mapArray[blockX][blockY].isDestroyed() && blockY != GameSettings.MAP_HEIGHT - 1)
                        mapArray[blockX][blockY].setHasCollision(false);
            }
        }
    }
    private boolean hasBlockNearBy(BasicBlock[][] mapArray, int x, int y){
        for (int i = 0; i < 4; i++) {
            int nearX = x + (1 - i) * ((i + 1) % 2);
            int nearY = y + (2 - i) * (i % 2);
            if (nearX >= 0 && nearX < GameSettings.MAP_WIDTH && nearY >= 0 && nearY < GameSettings.MAP_HEIGHT)
                if (mapArray[nearX][nearY].isDestroyed())
                    return true;
        }
        return false;
    }
    public void generateCollision(BasicBlock[][] mapArray) {
        for (int i = 0; i < GameSettings.MAP_WIDTH; i++) {
            mapArray[i][GameSettings.MAP_HEIGHT-1].setHasCollision(true);
        }

    }

    public void deleteBlocks() {
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
