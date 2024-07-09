package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

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
}
