package ru.nsu.zelenin.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Class for coloring the canvas.
 */
public class Coloring {

    /**
     * Method draw rows * columns squares.
     *
     * @param size - square size
     * @param rows - rows
     * @param columns - columns
     * @param gc - graphic context of a canvas
     */
    public static void drawBackground(int size, int rows, int columns, GraphicsContext gc) {
        for (int i = 0; i < columns; ++i) {
            for (int j = 0; j < rows; ++j) {
                gc.setFill(Color.rgb(0, 0, 0, 0.35));
                gc.fillRoundRect(i * size, j * size, size, size, 5, 5);
            }
        }

    }

    /**
     * Method draw the snake itself - head and body.
     *
     * @param gc - graphic context of canvas
     * @param size - square size
     * @param python - our snake
     */
    public static void drawSnake(GraphicsContext gc, int size, Snake python) {
        var head = python.getSnake().get(0);
        gc.setFill(Color.web("#86B71B"));
        gc.fillRoundRect(head.x * size + 1, head.y * size + 1, size - 2, size - 2, 20, 20);
        gc.setFill(Color.web("#117e07"));
        var snake = python.getSnake();
        for (int i = 1; i < snake.size(); ++i) {
            gc.fillRoundRect(snake.get(i).x * size + 1, snake.get(i).y * size + 1,
                    size - 2, size - 2, 10, 10);
        }
    }

    /**
     * Method draws food.
     *
     * @param gc - graphic context of canvas
     * @param food - food
     * @param size - square size
     */
    public static void drawFood(GraphicsContext gc, Food food, int size) {
        gc.setFill(food.color());
        gc.fillOval(food.place().x * size, food.place().y * size, size, size);
    }
}
