package ru.nsu.zelenin.snake;

import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Class-controller for lose application.
 */
public class LoseController {
    private Game game;
    private Stage stage;

    /**
     * Method restarts a game.
     */
    @FXML
    public void restartGame() {
        if (Game.getTimeline().getStatus() == Animation.Status.STOPPED) {
            Game.getTimeline().play();
            Game.setGameOver(false);
        }
        if (Game.isPaused()) {
            Game.setIsPaused(false);
        }
        Sound currSound = Game.getMyController().getCurrentSound();
        if (!currSound.getSound().isPlaying()) {
            currSound.playStuff();
        }
        int r = game.getROWS();
        int c = game.getCOLUMNS();
        Game.updateSpeed(Game.getSpeed());
        Game.getScore().clearScore();
        Game.setFood(Food.newFood(c, r, Game.getSnake()));
        Game.initializeSnake(c, r);
        stage.close();
    }

    /**
     * Method exit from game - closes all windows.
     */
    @FXML
    public void quitGame() {
        try {
            game.getStage().close();
            game.stop();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Simple setter.
     *
     * @param stage - stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Simple setter.
     *
     * @param game - our game
     */
    public void setGame(Game game) {
        this.game = game;
    }
}
