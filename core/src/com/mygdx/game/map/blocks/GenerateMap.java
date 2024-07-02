package com.mygdx.game.map.blocks;

import com.mygdx.game.screens.GameScreen;

import java.util.Random;

public class GenerateMap {
    GameScreen gameScreen;
    public Byte[][] mapArray;

    public GenerateMap(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        generate();

    }

    public void generate() {
        mapArray = new Byte[200][1000];
        for (int i = 0; i < 200; ++i) {
            for (int k = 0; k < 1000; ++k) {
                Random random = new Random();
                if (random.nextBoolean()) {
                    mapArray[i][k] = 1;
                } else {
                    mapArray[i][k] = 2;
                }
            }
        }

    }

}
