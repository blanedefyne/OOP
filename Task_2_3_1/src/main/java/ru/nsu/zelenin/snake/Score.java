package ru.nsu.zelenin.snake;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Class for representing score.
 */
public class Score {
    private final IntegerProperty score = new SimpleIntegerProperty(0);

    /**
     * Method prints score on the screen.
     *
     * @param game - our game
     */
    public void printScore(Game game) {
        GraphicsContext gc = Game.getMyController().getMyCanvas().getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        int size = game.getSQUARE_SIZE();
        gc.setFont(new Font(size * 0.67));
        gc.fillText("SCORE : " + score.get(), size * 0.2, size * 0.67);
    }

    /**
     * Method updates the score.
     */
    public void updateScore() {
        score.set(score.get() + 1);
    }

    /**
     * Method clears the score.
     */
    public void clearScore() {
        score.set(0);
    }

    /**
     * Simple getter.
     *
     * @return integerProperty - score
     */
    public IntegerProperty getScore() {
        return score;
    }

}
