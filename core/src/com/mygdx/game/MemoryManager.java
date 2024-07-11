package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.map.blocks.BasicBlock;
import com.mygdx.game.uis.MoneyManager;

import java.util.ArrayList;

public class MemoryManager {
    private static final Preferences preferences = Gdx.app.getPreferences("User saves");

    public static void saveMusicSettings(int musicVolume) {
        Json json = new Json();
        String tableInString = json.toJson(musicVolume);
        preferences.putString("musicVolume", tableInString);
        preferences.flush();
    }

    public static int loadMusicVolume() {
        if (!preferences.contains("musicVolume"))
            return 100;
        String musicVolume = preferences.getString("musicVolume");
        Json json = new Json();
        Integer table = json.fromJson(Integer.class, musicVolume);
        return table;
    }

    public static void saveName(String name){
        preferences.putString("name", name);
        preferences.flush();
    }

    public static String loadName(){return preferences.getString("name");}

    public static void saveSoundSettings(int soundVolume) {
        Json json = new Json();
        String tableInString = json.toJson(soundVolume);
        preferences.putString("soundVolume", tableInString);
        preferences.flush();
    }

    public static int loadSoundVolume() {
        if (!preferences.contains("soundVolume"))
            return 100;
        String soundVolume = preferences.getString("soundVolume");
        Json json = new Json();
        Integer table = json.fromJson(Integer.class, soundVolume);
        return table;
    }
    public static void saveOverallVolumeSettings(int overallVolume) {
        Json json = new Json();
        String tableInString = json.toJson(overallVolume);
        preferences.putString("overallVolume", tableInString);
        preferences.flush();
    }

    public static int loadOverallVolume() {
        if (!preferences.contains("overallVolume"))
            return 100;
        String soundVolume = preferences.getString("overallVolume");
        Json json = new Json();
        Integer table = json.fromJson(Integer.class, soundVolume);
        return table;
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
    public  static void saveMoney(int money) {
        Json json = new Json();
        String tableInString = json.toJson(money);
        preferences.putString("money", tableInString);
        preferences.flush();
    }
    public static int getMoney() {
        if (!preferences.contains("money"))
            return 0;
        String money = preferences.getString("money");
        Json json = new Json();
        Integer table = json.fromJson(Integer.class, money);
        return table;
    }

    public  static void savePoints(int points) {
        Json json = new Json();
        String tableInString = json.toJson(points);
        preferences.putString("points", tableInString);
        preferences.flush();
    }
    public static int getPoints() {
        if (!preferences.contains("points"))
            return 0;
        String points = preferences.getString("points");
        Json json = new Json();
        Integer table = json.fromJson(Integer.class, points);
        return table;
    }


}
