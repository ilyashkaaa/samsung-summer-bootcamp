package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
    public Music walkingOnGrassSound;
   public Sound jumpAndFallSound;
    public float musicVolume, soundVolume, overallVolume;
    public AudioManager() {
        walkingOnGrassSound = Gdx.audio.newMusic(Gdx.files.internal(GameResources.WALK_ON_GRASS_SOUND));
        jumpAndFallSound = Gdx.audio.newSound(Gdx.files.internal(GameResources.JUMP_AND_FALL_SOUND));
        walkingOnGrassSound.setLooping(true);
    }
    public void updateVolume() {
        musicVolume = MemoryManager.loadMusicVolume()/100f;
        soundVolume = MemoryManager.loadSoundVolume()/100f;
        overallVolume = MemoryManager.loadOverallVolume()/100f;
    }

}
