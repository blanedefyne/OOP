package ru.nsu.zelenin.snake;

import javafx.fxml.FXML;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Objects;

public class MainController {

    private int imageIdx = 0;
    private int soundIdx = 0;
    private Image[] images = new Image[] {
            new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/lion.jpg"))),
            new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/gf.jpeg"))),
            new Image(Objects.requireNonNull(getClass().
                    getResourceAsStream("images/phineas.jpg"))),
            new Image(Objects.requireNonNull(getClass().
                    getResourceAsStream("images/invincible_s5.jpg"))),
    };

    private String[] sounds = new String[] {
            "sounds/timon & pumba.mp3",
            "sounds/chillout.mp3",
            "sounds/deceptacon.mp3",
            "sounds/voronins.mp3",
            "sounds/freestyle.mp3"
    };

    private Game game;
    private Stage primaryStage;
    private Sound currentSound = new Sound(sounds[soundIdx]);
    @FXML
    private Canvas myCanvas = new Canvas();

    @FXML
    private ImageView myImageView;

    @FXML
    public Canvas getMyCanvas() {
        return myCanvas;
    }

    Image currentImage = images[imageIdx];

    @FXML
    public void setMyImageView(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        myImageView.setImage(currentImage);
    }

    @FXML
    public void nextPic() {
        if (imageIdx == images.length - 1) {
            imageIdx = -1;
        }
        currentImage = images[++imageIdx];
    }

    @FXML
    public void prevPic() {
        if (imageIdx == 0) {
            imageIdx = images.length;
        }
        currentImage = images[--imageIdx];
    }

    @FXML
    public void stopSound() {
        currentSound.stopStuff();
    }

    @FXML
    public void playSound() {
        if (!currentSound.getSound().isPlaying()) {
            currentSound.playStuff();
        }
    }

    public void setCurrentSound(Sound sound) {
        currentSound = sound;
    }

    @FXML
    public void playNext() {
        if (soundIdx == sounds.length - 1) {
            soundIdx = -1;
        }
        stopSound();
        currentSound.setPathname(sounds[++soundIdx]);
        playSound();
    }

    @FXML
    public void playPrev() {
        if (soundIdx == 0) {
            soundIdx = sounds.length;
        }
        stopSound();
        currentSound.setPathname(sounds[--soundIdx]);
        playSound();
    }

    @FXML
    public void restart() {
        try {
            currentSound.stopStuff();
            Game.getTimeline().stop();
            Game.getScore().clearScore();
            game.stop();
            game.start(primaryStage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
