package com.mygdx.game.map.blocks;

import static com.mygdx.game.GameSettings.MAP_HEIGHT;
import static com.mygdx.game.GameSettings.MAP_WIDTH;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class GenerateMap {
    private static Random random = new Random();

    public BasicBlock[][] mapArray;

    public GenerateMap(){
        mapArray = new BasicBlock[MAP_WIDTH][MAP_HEIGHT];

        for (int i = 0; i < MAP_HEIGHT; i++)
            for (int j = 0; j < MAP_WIDTH; j++) {
                if (random.nextInt(map(0, MAP_HEIGHT - 1, 5, 40, i)) == 0)
                    generateCluster(j, i, Stone.class, mapArray);
                if (random.nextInt(map(0, MAP_HEIGHT - 1, 5, 40, i)) == 0)
                    generateCluster(j, i, Grass.class, mapArray);
            }

        for (int i = 0; i < MAP_WIDTH; i++)
            for (int j = 0; j < MAP_HEIGHT; j++)
                if (mapArray[i][j] == null)
                    mapArray[i][j] = new Dirt();

    }

    private static void generateCluster(int x, int y, Class<? extends BasicBlock> block, BasicBlock[][] map) {
        if (map[x][y] == null) {
            ArrayList<Integer> blocksX = new ArrayList<>();
            ArrayList<Integer> blocksY = new ArrayList<>();
            try {
                BasicBlock blockConstructor = block.getConstructor().newInstance();
                map[x][y] = blockConstructor;
                blocksX.add(x);
                blocksY.add(y);
                int countOfUnGenerated = 1;

                int counter = 0;
                while (countOfUnGenerated != 0) {
                    countOfUnGenerated--;
                    for (int i = 0; i < 4; i++) {
                        int clusterX = blocksX.get(counter) + (1 - i) * ((i + 1) % 2);
                        int clusterY = blocksY.get(counter) + (2 - i) * (i % 2);
                        if (random.nextInt(4) == 0 && !isThereBlock(clusterX, clusterY, blocksX, blocksY) &&
                                clusterX >= 0 && clusterX < MAP_WIDTH && clusterY >= 0 && clusterY < MAP_HEIGHT) {
                            if (map[clusterX][clusterY] == null) {
                                countOfUnGenerated++;
                                blocksX.add(clusterX);
                                blocksY.add(clusterY);
                                map[clusterX][clusterY] = blockConstructor;
                            }
                        }
                    }
                    counter++;
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isThereBlock(int x, int y, ArrayList<Integer> blocksX, ArrayList<Integer> blocksY){
        for (int i = 0; i < Math.max(blocksX.size(), blocksY.size()); i++)
            if (blocksX.get(i) == x && blocksY.get(i) == y)
                return true;
        return false;
    }

    private static int map(int getMin,  int getMax, float setMin, float setMax, float value){
        return Math.round((value - getMin + 1) / (getMax - getMin + 1) * (setMax - setMin + 1) + setMin - 1);
    }
}
