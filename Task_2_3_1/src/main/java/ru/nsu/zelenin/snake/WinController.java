package ru.nsu.zelenin.snake;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Class-controller for win-screen application.
 */
public class WinController {
    private Game game;
    private Stage stage;

    /**
     * Method suggest to change setting after winning - it runs setting application.
     */
    @FXML
    public void playAgain() {
        stage.close();
        Game.getMyController().openSettings();
    }

    /**
     * Method exit from game.
     */
    @FXML
    public void quitGame() {
        game.getStage().close();
        try {
            game.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        stage.close();
    }

    /**
     * Simple setter.
     *
     * @param stage - win stage
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
