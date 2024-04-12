package ru.nsu.zelenin.snake;

import java.util.Objects;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

/**
 * Class for playing funny sounds and songs.
 */
public class Sound {
    private AudioClip sound;
    private Timeline soundline;
    private String pathname;

    public Sound(String pathname) {
        this.pathname = pathname;
    }

    /**
     * Method plays certain sound infinitely.
     */
    public void playStuff() {
        sound = new AudioClip(
                Objects.requireNonNull(Sound.class.getResource(pathname)).toExternalForm());
        sound.play();
        soundline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), event -> {
                    if (!sound.isPlaying()) {
                        sound.play();
                    }
                })
        );
        soundline.setCycleCount(Animation.INDEFINITE);
        soundline.play();
    }

    /**
     * Method stops the sound.
     */
    public void stopStuff() {
        this.sound.stop();
        this.soundline.stop();
    }

    /**
     * Method plays sound once.
     */
    public void playStuffOnce() {
        sound = new AudioClip(
                Objects.requireNonNull(Sound.class.getResource(pathname)).toExternalForm());
        sound.play();
    }

    /**
     * Simple getter.
     *
     * @return sound
     */
    public AudioClip getSound() {
        return sound;
    }

    /**
     * Simple setter.
     *
     * @param pathname - path to sound
     */
    public void setPathname(String pathname) {
        this.pathname = pathname;
    }
}