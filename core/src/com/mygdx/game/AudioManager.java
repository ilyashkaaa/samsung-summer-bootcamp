package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
    public Music walkingOnGrassSound;
    public Music backgroundMusic;
   public Sound jumpSound;
   public Sound fallSound;
    public float musicVolume, soundVolume, overallVolume;
    public AudioManager() {
        musicVolume = MemoryManager.loadMusicVolume()/100f;
        soundVolume = MemoryManager.loadSoundVolume()/100f;
        overallVolume = MemoryManager.loadOverallVolume()/100f;
        walkingOnGrassSound = Gdx.audio.newMusic(Gdx.files.internal(GameResources.WALK_ON_GRASS_SOUND));
        walkingOnGrassSound.setLooping(true);
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(GameResources.BACKGROUND_MUSIC));
        backgroundMusic.setLooping(true);
        jumpSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.JUMP_SOUND));
        fallSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.FALL_SOUND));

    }
    public void updateVolume() {
        musicVolume = MemoryManager.loadMusicVolume()/100f;
        soundVolume = MemoryManager.loadSoundVolume()/100f;
        overallVolume = MemoryManager.loadOverallVolume()/100f;
    }

}
