package com.mygdx.game.map.blocks;

import static com.mygdx.game.GameSettings.MAP_HEIGHT;
import static com.mygdx.game.GameSettings.MAP_WIDTH;

import com.mygdx.game.MemoryManager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class GenerateMap {
    private static Random random = new Random();

    public BasicBlock[][] mapArray;

    public GenerateMap() {
        if (MemoryManager.getMap() == null) {
            mapArray = new BasicBlock[MAP_WIDTH][MAP_HEIGHT];

            for (int i = 0; i < MAP_HEIGHT; i++)
                for (int j = 0; j < MAP_WIDTH; j++) {
                    if (random.nextInt(map(0, MAP_HEIGHT - 1, 1, 60, i)) == 0 && i <= MAP_HEIGHT - 20)
                        generateCluster(j, i, Ruby.class, mapArray, 10);
                    if (random.nextInt(map(0, MAP_HEIGHT - 1, 16, 55, i)) == 0 && i <= MAP_HEIGHT - 15)
                        generateCluster(j, i, Amethyst.class, mapArray, 15);
                    if (random.nextInt(map(0, MAP_HEIGHT - 1, 15, 50, i)) == 0 && i <= MAP_HEIGHT - 12)
                        generateCluster(j, i, Emerald.class, mapArray, 20);
                    if (random.nextInt(map(0, MAP_HEIGHT - 1, 14, 45, i)) == 0 && i <= MAP_HEIGHT - 10)
                        generateCluster(j, i, Diamond.class, mapArray, 25);
                    if (random.nextInt(map(0, MAP_HEIGHT - 1, 12, 40, i)) == 0 && i <= MAP_HEIGHT - 8)
                        generateCluster(j, i, Gold.class, mapArray, 30);
                    if (random.nextInt(map(0, MAP_HEIGHT - 1, 11, 35, i)) == 0 && i <= MAP_HEIGHT - 6)
                        generateCluster(j, i, Lazurite.class, mapArray, 35);
                    if (random.nextInt(map(0, MAP_HEIGHT - 1, 10, 30, i)) == 0 && i <= MAP_HEIGHT - 5)
                        generateCluster(j, i, Iron.class, mapArray, 40);
                    if (random.nextInt(map(0, MAP_HEIGHT - 1, 25, 25, i)) == 0)
                        generateCluster(j, i, Coal.class, mapArray, 50);
                }

            for (int i = 0; i < MAP_WIDTH; i++) {
                mapArray[i][MAP_HEIGHT - 1] = new Grass();
            }

            for (int i = 1; i < 3; i++) {
                for (int j = 0; j < MAP_WIDTH; j++) {
                    if (random.nextInt(i + 1) == 0 && mapArray[j][MAP_HEIGHT - 11 + i] == null)
                        mapArray[j][MAP_HEIGHT - 11 + i] = new Stone();
                    if (random.nextInt(i + 1) == 0 && mapArray[j][MAP_HEIGHT - 10 - i] == null)
                        mapArray[j][MAP_HEIGHT - 10 - i] = new Dirt();
                }
            }

            for (int i = 0; i < MAP_WIDTH; i++)
                for (int j = 0; j < MAP_HEIGHT; j++) {
                    if (mapArray[i][j] == null) {
                        if (MAP_HEIGHT - j <= 10)
                            mapArray[i][j] = new Dirt();
                        else
                            mapArray[i][j] = new Stone();
                    }
                    mapArray[i][j].setDestroyed(false);
                }
            MemoryManager.saveMap(mapArray);
        } else {
            mapArray = MemoryManager.getMap();
        }
    }

    private static void generateCluster(int x, int y,/* BasicBlock block,*/ Class<? extends BasicBlock> block, BasicBlock[][] map, int maxAmountOfBLocks) {
        if (map[x][y] == null) {
            ArrayList<Integer> blocksX = new ArrayList<>();
            ArrayList<Integer> blocksY = new ArrayList<>();
            try {
                map[x][y] = block.getConstructor().newInstance();
                blocksX.add(x);
                blocksY.add(y);
                int countOfUnGenerated = 1;

                int counter = 0;
                while (countOfUnGenerated != 0 && maxAmountOfBLocks > blocksY.size()) {
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
                                map[clusterX][clusterY] = block.getConstructor().newInstance();
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
