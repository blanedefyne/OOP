package ru.nsu.zelenin.snake;

import javafx.animation.Animation;
import javafx.fxml.FXML;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Objects;

public class MainController {
    private Game game;

    private int imageIdx = Game.getImgId();
    private int soundIdx = Game.getSndId();
    private final Image[] images = new Image[] {
            new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/lion.jpg"))),
            new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/gf.jpeg"))),
            new Image(Objects.requireNonNull(getClass().
                    getResourceAsStream("images/phineas.jpg"))),
            new Image(Objects.requireNonNull(getClass().
                    getResourceAsStream("images/apapa.jpg"))),
    };

    private final String[] sounds = new String[] {
            "sounds/timon & pumba.mp3",
            "sounds/chillout.mp3",
            "sounds/deceptacon.mp3",
            "sounds/voronins.mp3",
            "sounds/freestyle.mp3"
    };

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
        Game.setImgId(imageIdx);
    }

    @FXML
    public void prevPic() {
        if (imageIdx == 0) {
            imageIdx = images.length;
        }
        currentImage = images[--imageIdx];
        Game.setImgId(imageIdx);
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

    @FXML
    public void playNext() {
        if (soundIdx == sounds.length - 1) {
            soundIdx = -1;
        }
        stopSound();
        currentSound.setPathname(sounds[++soundIdx]);
        playSound();
        Game.setSndId(soundIdx);
    }

    @FXML
    public void playPrev() {
        if (soundIdx == 0) {
            soundIdx = sounds.length;
        }
        stopSound();
        currentSound.setPathname(sounds[--soundIdx]);
        playSound();
        Game.setSndId(soundIdx);
    }

    @FXML
    public void restart() {
        if (Game.getTimeline().getStatus() == Animation.Status.STOPPED) {
            Game.getTimeline().play();
            Game.setGameOver(false);
        }
        currentSound.stopStuff();
        int r = game.getROWS();
        int c = game.getCOLUMNS();
        Game.getScore().clearScore();
        Game.setFood(Food.newFood(r, c, Game.getSnake()));
        Game.initializeSnake(r, c);
        currentSound.playStuff();
    }

    @FXML
    public void pause() {
        Game.setIsPaused(!Game.isPaused());
    }

    public void setGame(Game game) {
        this.game = game;
    }


    public Sound getCurrentSound() {
        return currentSound;
    }
}
