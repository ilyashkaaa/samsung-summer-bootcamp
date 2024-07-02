package com.mygdx.game.map.blocks;

import static com.mygdx.game.GameSettings.MAP_HEIGHT;
import static com.mygdx.game.GameSettings.MAP_WIDTH;

import com.mygdx.game.screens.GameScreen;

import java.util.ArrayList;
import java.util.Random;

public class GenerateMap {
    GameScreen gameScreen;
    private static Random random;

    private static int mapWidth = MAP_WIDTH, mapHeight = MAP_HEIGHT;
    public BasicBlock[][] mapArray;

    public GenerateMap(GameScreen gameScreen) {
        random = new Random();

        mapArray = new BasicBlock[mapWidth][mapHeight];

        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                mapArray[i][j] = new Dirt();
            }
        }

        for (int i = 0; i < mapHeight; i++)
            for (int j = 0; j < mapWidth; j++)
                if (random.nextInt(map(0, mapHeight - 1, 5, 40, i)) == 0)
                    generateCluster(j, i, mapArray);
        mapArray[0][0] = new Grass();
//        this.gameScreen = gameScreen;
    }

    private static void generateCluster(int x, int y, BasicBlock[][] map){
        if (map[x][y].getClass() == Dirt.class) {
            ArrayList<Integer> blocksX, blocksY;
            blocksX = new ArrayList<>();
            blocksY = new ArrayList<>();

            map[x][y] = new Grass();
            blocksX.add(x);
            blocksY.add(y);
            int countOfUnGenerated = 1;

            int counter = 0;
            while (countOfUnGenerated != 0) {
                countOfUnGenerated--;
                for (int i = 0; i < 4; i++) {
                    int xc = blocksX.get(counter) + (1 - i) * ((i + 1) % 2);
                    int yc = blocksY.get(counter) + (2 - i) * (i % 2);
                    if (random.nextInt(4) == 0 && !isThereBlock(xc, yc, blocksX, blocksY) &&
                            xc >= 0 && xc < mapWidth && yc >= 0 && yc < mapHeight) {
                        if (map[xc][yc].getClass() == Dirt.class) {
                            countOfUnGenerated++;
                            blocksX.add(xc);
                            blocksY.add(yc);
                            map[xc][yc] = new Grass();
                        }
                    }
                }
                counter++;
            }
        }
    }

    private static boolean isThereBlock(int x, int y, ArrayList<Integer> blocksX, ArrayList<Integer> blocksY){
        for (int i = 0; i < Math.max(blocksX.size(), blocksY.size()); i++)
            if (blocksX.get(i) == x && blocksY.get(i) == y)
                return true;
        return false;
    }

    private static int map(float getMin,  float getMax, float setMin, float setMax, float value){
        return Math.round((value - getMin + 1) / (getMax - getMin + 1) * (setMax - setMin + 1) + setMin - 1);
    }

}
