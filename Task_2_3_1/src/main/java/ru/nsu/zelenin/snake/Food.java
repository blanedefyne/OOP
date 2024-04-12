package ru.nsu.zelenin.snake;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.Random;

/**
 * Record class for representing food.
 *
 * @param color - color of current food
 * @param place - its coordinates
 */
public record Food(Color color, Point place) {

    /**
     * Method generate a new food with its color and place.
     *
     * @param columns - columns
     * @param rows - rows
     * @param snake - our snake
     * @return new food
     */
    public static Food newFood(int columns, int rows, Snake snake) {

        Color[] colors = new Color[]{
                Color.web("#fAA61A"),
                Color.web("#f04747"),
                Color.web("#b05f6d"),
                Color.web("#462446"),
                Color.web("#47b39d"),
                Color.web("#C72C41"),
                Color.web("#a14540"),
                Color.web("#FF5733"),
                Color.web("#FFC30F")
        };
        Random random = new Random();

        Color color = colors[random.nextInt(colors.length)];

        int c = random.nextInt(columns);
        int r = random.nextInt(rows);

        boolean foundPlace = false;

        while (!foundPlace) {
            for (var dot : snake.getSnake()) {
                if (dot.x == c && dot.y == r) {
                    r = random.nextInt(rows);
                    c = random.nextInt(columns);
                    break;
                }
            }
            foundPlace = true;
        }

        return new Food(color, new Point(c, r));
    }
}
