package ru.nsu.zelenin.snake;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.util.Objects;

public class Sound {
    private AudioClip sound;
    private Timeline soundline;
    private String pathname;

    public Sound(String pathname) {
        this.pathname = pathname;
    }

    public void playStuff() {
        sound = new AudioClip(
                Objects.requireNonNull(Sound.class
                                .getResource(pathname))
                        .toExternalForm());
        sound.play();
        soundline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), e -> {
                    if (!sound.isPlaying()) {
                        sound.play();
                    }
                })
        );
        soundline.setCycleCount(Animation.INDEFINITE);
        soundline.play();
    }


    public void stopStuff() {
        this.sound.stop();
        this.soundline.stop();
    }

    public Timeline getSoundline() {
        return soundline;
    }

    public AudioClip getSound() {
        return sound;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }
}