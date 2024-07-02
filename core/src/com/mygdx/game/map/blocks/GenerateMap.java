package com.mygdx.game.map.blocks;

import static com.mygdx.game.GameSettings.MAP_HEIGHT;
import static com.mygdx.game.GameSettings.MAP_WIDTH;

import com.mygdx.game.screens.GameScreen;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GenerateMap {
    private static Random random;

//    private static int mapWidth = MAP_WIDTH, mapHeight = MAP_HEIGHT;
    public BasicBlock[][] mapArray;

    public GenerateMap(){
        random = new Random();

        mapArray = new BasicBlock[MAP_WIDTH][MAP_HEIGHT];

        for (int i = 0; i < MAP_HEIGHT; i++)
            for (int j = 0; j < MAP_WIDTH; j++)
                switch (random.nextInt(map(0, MAP_HEIGHT - 1, 5, 40, i))) {
                    case 0:
                        generateCluster(j, i, Stone.class, mapArray);
                        break;
                    case 1:
                        generateCluster(j, i, Grass.class, mapArray);
                        break;
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
                Constructor<? extends BasicBlock> constructor = block.getConstructor();

                map[x][y] = constructor.newInstance();
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
                                xc >= 0 && xc < MAP_WIDTH && yc >= 0 && yc < MAP_HEIGHT) {
                            if (map[xc][yc] == null) {
                                countOfUnGenerated++;
                                blocksX.add(xc);
                                blocksY.add(yc);
                                map[xc][yc] = constructor.newInstance();
                            }
                        }
                    }
                    counter++;
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
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

    private static int map(float getMin,  float getMax, float setMin, float setMax, float value){
        return Math.round((value - getMin + 1) / (getMax - getMin + 1) * (setMax - setMin + 1) + setMin - 1);
    }
}
