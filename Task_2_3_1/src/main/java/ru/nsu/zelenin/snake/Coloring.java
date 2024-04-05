package ru.nsu.zelenin.snake;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

public class Coloring {

    public static void drawBackground(int size, int rows, int columns, GraphicsContext gc) {
        for (int i = 0; i < columns; ++i) {
            for (int j = 0; j < rows; ++j) {
                gc.setFill(Color.rgb(0, 0, 0, 0.4));
                gc.fillRoundRect(i * size, j * size, size, size, 5, 5);
            }
        }

    }

    public static void drawSnake(GraphicsContext gc, int size, Snake python) {
        var head = python.getSnake().get(0);
        var snake = python.getSnake();
        gc.setFill(Color.web("#521034"));
        gc.fillRoundRect(head.x * size + 1, head.y * size + 1, size - 2, size - 2, 15, 15);
        gc.setFill(Color.web("#8c1b66"));
        for (int i = 1; i < snake.size(); ++i) {
            gc.fillRoundRect(snake.get(i).x * size + 1, snake.get(i).y * size + 1,
                    size - 2, size - 2, 10, 10);
        }
    }

    public static void drawFood(GraphicsContext gc, Food food, int size) {
        gc.setFill(food.getColor());
        gc.fillOval(food.getPlace().x * size, food.getPlace().y * size, size, size);
    }
}
