package ru.nsu.zelenin.snake;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Class-controller for settings application.
 */
public class SettingsController {
    private Game game;
    private Stage stage;
    private static DIFF difficulty = DIFF.MEDIUM;
    private static String diffString = "Difficulty:";

    @FXML
    MenuButton myDifficulty;
    @FXML
    TextField myGoal;

    /**
     * Method for showing current settings.
     */
    public void showData() {
        myDifficulty.setText(diffString);
        int goal = game.getGoalScore();
        myGoal.setText(String.valueOf(goal));
    }

    /**
     * Simple setter.
     *
     * @param stage - settings stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method for changing settings and saving them.
     */
    @FXML
    public void saveSettings() {
        int newGoal = Integer.parseInt(myGoal.getText());
        switch (difficulty) {
            case EASY -> {
                Game.updateSpeed(130);
            }
            case MEDIUM -> {
                Game.updateSpeed(100);
            }
            case HARD -> {
                Game.updateSpeed(70);
            }
        }
        game.setGoalScore(newGoal);
        stage.close();
        Game.getMyController().restart();
    }

    /**
     * Method checks if easy difficulty was chosen.
     */
    @FXML
    public void easyDiff() {
        difficulty = DIFF.EASY;
        diffString = "Easy";
        myDifficulty.setText(diffString);
    }

    /**
     * Method checks if medium difficulty was chosen.
     */
    @FXML
    public void mediumDiff() {
        difficulty = DIFF.MEDIUM;
        diffString = "Medium";
        myDifficulty.setText(diffString);
    }

    /**
     * Method checks if hard difficulty was chosen.
     */
    @FXML
    public void hardDiff() {
        difficulty = DIFF.HARD;
        diffString = "Hard";
        myDifficulty.setText(diffString);

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
     * Simple private enum for difficulty.
     */
    private enum DIFF {
        EASY, MEDIUM, HARD
    }
}
