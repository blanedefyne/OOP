package ru.nsu.zelenin.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Score {
    private int score = 0;

    public void printScore(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(20d));
        gc.fillText("SCORE : " + score, 6, 20);
    }

    public void updateScore() {
        score++;
    }

    public void clearScore() {
        score = 0;
    }
}
