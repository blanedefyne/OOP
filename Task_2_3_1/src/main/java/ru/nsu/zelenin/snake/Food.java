package ru.nsu.zelenin.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.Random;

public class Food {
    private Color color;
    private Point place;

    public Food(Color color, Point place) {
        this.color = color;
        this.place = place;
    }

    public static Food newFood(int rows, int columns, Snake snake) {

        Color[] colors = new Color[] {
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

    public Color getColor() {
        return color;
    }

    public Point getPlace() {
        return place;
    }
}
