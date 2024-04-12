package ru.nsu.zelenin.snake;

import javafx.animation.Animation;
import javafx.fxml.FXML;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class-controller for a Game class.
 */
public class MainController {
    private Game game;
    private final Stage resize = new Stage();
    private final Stage settings = new Stage();

    private int imageIdx = Game.getImgId();
    private int soundIdx = Game.getSndId();

    private final File imagesFolder = new File("src/main/resources/ru/nsu/zelenin/snake/images");
    private final List<Image> images = new ArrayList<>();

    private final File soundsFolder = new File("src/main/resources/ru/nsu/zelenin/snake/sounds");
    private final List<String> sounds = new ArrayList<>();

    /**
     * Method initializes images and sounds considering folders given above.
     * Also, it sets current image and sound
     */
    public void initialize() {
        String[] iFileNames = Objects.requireNonNull(imagesFolder.list());
        String[] sFileNames = Objects.requireNonNull(soundsFolder.list());
        for (var file : iFileNames) {
            images.add(new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream("images/" + file))));
        }
        for (var file : sFileNames) {
            sounds.add("sounds/" + file);
        }
        currentSound = new Sound(sounds.get(soundIdx));
        currentImage = images.get(imageIdx);
    }

    private Sound currentSound;
    private Image currentImage;

    @FXML
    private Canvas myCanvas = new Canvas();

    @FXML
    private ImageView myImageView;

    @FXML
    public Canvas getMyCanvas() {
        return myCanvas;
    }

    /**
     * Method sets image on background.
     *
     * @param canvas - our canvas
     */
    @FXML
    public void setMyImageView(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        myImageView.setImage(currentImage);
        myImageView.setPreserveRatio(false);
        myImageView.setFitHeight(canvas.getHeight());
        myImageView.setFitWidth(canvas.getWidth());
    }

    /**
     * Method sets next picture.
     */
    @FXML
    public void nextPic() {
        if (imageIdx == images.size() - 1) {
            imageIdx = -1;
        }
        currentImage = images.get(++imageIdx);
        Game.setImgId(imageIdx);
    }

    /**
     * Method sets previous picture.
     */
    @FXML
    public void prevPic() {
        if (imageIdx == 0) {
            imageIdx = images.size();
        }
        currentImage = images.get(--imageIdx);
        Game.setImgId(imageIdx);
    }

    /**
     * Method stops current sound.
     */
    @FXML
    public void stopSound() {
        currentSound.stopStuff();
    }

    /**
     * Method starts current sound.
     */
    @FXML
    public void playSound() {
        if (!currentSound.getSound().isPlaying()) {
            currentSound.playStuff();
        }
    }

    /**
     * Method plays next sound.
     */
    @FXML
    public void playNext() {
        if (soundIdx == sounds.size() - 1) {
            soundIdx = -1;
        }
        stopSound();
        currentSound.setPathname(sounds.get(++soundIdx));
        playSound();
        Game.setSndId(soundIdx);
    }

    /**
     * Method plays previous sound.
     */
    @FXML
    public void playPrev() {
        if (soundIdx == 0) {
            soundIdx = sounds.size();
        }
        stopSound();
        currentSound.setPathname(sounds.get(--soundIdx));
        playSound();
        Game.setSndId(soundIdx);
    }

    /**
     * Method restarts the game.
     */
    @FXML
    public void restart() {
        Game.getTimeline().setCycleCount(Animation.INDEFINITE);
        if (Game.getTimeline().getStatus() == Animation.Status.STOPPED) {
            Game.getTimeline().play();
            Game.setGameOver(false);
        }
        if (Game.isPaused()) {
            pause();
        }
        if (!currentSound.getSound().isPlaying()) {
            currentSound.playStuff();
        }
        int r = game.getROWS();
        int c = game.getCOLUMNS();
        Game.updateSpeed(Game.getSpeed());
        Game.getScore().clearScore();
        Game.setFood(Food.newFood(c, r, Game.getSnake()));
        Game.initializeSnake(c, r);
    }

    /**
     * Method pauses the game.
     */
    @FXML
    public void pause() {
        Game.setIsPaused(!Game.isPaused());
    }

    /**
     * Method calls another screen-application for changing the size of the game.
     */
    @FXML
    public void changeSize() {
        if (!Game.isPaused()) {
            pause();
        }
        Resize one = new Resize(game);
        try {
            one.start(resize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method calls another screen-application for changing settings.
     */
    @FXML
    public void openSettings() {
        if (!Game.isPaused()) {
            pause();
        }
        Settings set = new Settings(game);
        try {
            set.start(settings);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Method changes both sound and image.
     */
    @FXML
    public void nextCombo() {
        nextPic();
        soundIdx = imageIdx;
        currentSound.setPathname(sounds.get(soundIdx));
        stopSound();
        playSound();
    }

    /**
     * Method changes both sound and image.
     */
    @FXML
    public void prevCombo() {
        prevPic();
        soundIdx = imageIdx;
        currentSound.setPathname(sounds.get(soundIdx));
        stopSound();
        playSound();
    }
    /**
     * Simple setter.
     *
     * @param game - our game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Simple getter.
     *
     * @return current sound
     */
    public Sound getCurrentSound() {
        return currentSound;
    }

}
