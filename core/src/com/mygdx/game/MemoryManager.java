package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.map.blocks.BasicBlock;

import java.util.ArrayList;

public class MemoryManager {
    private static final Preferences preferences = Gdx.app.getPreferences("User saves");

    public static void saveMusicSettings(int musicVolume) {
        preferences.putInteger("musicVolume", musicVolume);
        preferences.flush();
    }

    public static int loadMusicVolume() {
        return preferences.getInteger("musicVolume", 100);
    }

    public static void saveSoundSettings(int soundVolume) {
        preferences.putInteger("soundVolume", soundVolume);
        preferences.flush();
    }

    public static int loadSoundVolume() {
        return preferences.getInteger("soundVolume", 100);
    }
    public static void saveOverallVolumeSettings(int overallVolume) {
        preferences.putInteger("overallVolume", overallVolume);
        preferences.flush();
    }

    public static int loadOverallVolume() {
        return preferences.getInteger("overallVolume", 100);
    }

    public static void saveMap(BasicBlock[][] mapArray) {
        Json json = new Json();
        String tableInString = json.toJson(mapArray);
        preferences.putString("map", tableInString);
        preferences.flush();
    }
    public static BasicBlock[][] getMap() {
        if (!preferences.contains("map"))
            return null;
        String map = preferences.getString("map");
        Json json = new Json();
        BasicBlock[][] table = json.fromJson(BasicBlock[][].class, map);
        return table;
    }
    public static void savePlayerPos(Vector2 playerPos) {
        Json json = new Json();
        String tableInString = json.toJson(playerPos);
        preferences.putString("playerPos", tableInString);
        preferences.flush();
    }
    public static Vector2 getPlayerPos() {
        if (!preferences.contains("playerPos"))
            return null;
        String playerPos = preferences.getString("playerPos");
        Json json = new Json();
        Vector2 table = json.fromJson(Vector2.class, playerPos);
        return table;
    }
}
