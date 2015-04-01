package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Andreas on 2/25/2015.
 */
public class SoundManager {

    public SoundManager() {
    }

    /**
     * Plays a sound using the LIBGDX sound library
     *
     * @param sound Sound to be played
     */
    public void playSound(Sound sound) {
        sound.play();
    }

    /**
     * Loops a sound using the LIBGDX sound library
     *
     * @param sound Sound to be looped
     */
    public void loopSound(Sound sound) {
        sound.loop();
    }

    /**
     * Plays or loops a music file using the LIBGDX music library
     *
     * @param music Music to be played/looped
     * @param loop  Decides to loop or not
     */
    public void playMusic(Music music, boolean loop) {
        if (loop && !music.isLooping()) music.setLooping(true);
        if (!music.isPlaying()) music.play();
    }

    /**
     * Get music from the library with string as identifier
     *
     * @param music identifier string
     * @return music object file
     */
    public FadeableMusic getMusic(String music) {
        return SoundLibrary.getInstance().getMusicFromString(music);
    }

    /**
     * Stops a sound from playing
     *
     * @param sound object to stop
     */
    public void stopSound(Sound sound) {
        sound.stop();
    }

    /**
     * Stops a music object from playing
     *
     * @param music object to stop
     */
    public void stopMusic(Music music) {
        music.stop();
    }

    public void update(float delta) {
        for (int i = 0; i < SoundLibrary.musicList.size(); i++) {
            SoundLibrary.musicList.get(i).update(delta);
        }
    }

    public void silence() {
        for (int i = 0; i < SoundLibrary.musicList.size(); i++) {
            SoundLibrary.musicList.get(i).getMusicObject().setVolume(0);
        }
    }

    public void stopAll() {
        for (int i = 0; i < SoundLibrary.musicList.size(); i++) {
            SoundLibrary.musicList.get(i).getMusicObject().stop();
        }
    }

    public boolean isPlaying(Music musicObject) {
        return musicObject.isPlaying();
    }
}
