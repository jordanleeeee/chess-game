package com.game.chess.config;

import com.game.chess.util.Resources;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashSet;
import java.util.Set;

public class AudioManager {
    private static final AudioManager INSTANCE = new AudioManager();
    private final Set<MediaPlayer> soundPool = new HashSet<>();

    /**
     * Enumeration of known sound resources.
     */
    public enum SoundRes {
        WIN, LOSE, MOVE;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    private AudioManager() {
    }

    public static AudioManager getInstance() {
        return INSTANCE;
    }

    private void playFile(final String name) {
        Media audio = Resources.mediaFromPath("audio/" + name + ".mp3");
        MediaPlayer mediaPlayer = new MediaPlayer(audio);
        soundPool.add(mediaPlayer);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.dispose();
            soundPool.remove(mediaPlayer);
        });
    }

    public void playSound(final SoundRes name) {
        playFile(name.toString());
    }
}
