package ru.nsu.zelenin.snake;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import static ru.nsu.zelenin.snake.Food.newFood;

public class Snake {
    private final List<Point> snake = new ArrayList<>();
    private Direction currentDirection;
    private final Point head;

    public Snake(int r, int c) {
        head = new Point((r - 1) / 2, (c - 1) / 2);
        snake.add(head);
        currentDirection = Direction.LEFT;
    }

    public void moveRight(int columns) {
        head.x++;
        if (head.x >= columns) {
            head.x = 0;
        }
    }

    public void moveLeft(int columns) {
        head.x--;
        if (head.x < 0) {
            head.x = columns - 1;
        }
    }

    public void moveUp(int rows) {
        head.y--;
        if (head.y < 0) {
            head.y = rows - 1;
        }
    }

    public void moveDown(int rows) {
        head.y++;
        if (head.y >= rows) {
            head.y = 0;
        }
    }

    public void move(Canvas canvas, int size, Food food, MainController myController) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int rows = (int) (canvas.getWidth() / size);
        int columns = (int) (canvas.getHeight() / size);
        for (int i = snake.size() - 1; i > 0; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        switch (currentDirection) {
            case UP -> moveUp(rows);
            case DOWN -> moveDown(rows);
            case LEFT -> moveLeft(columns);
            case RIGHT -> moveRight(columns);
        }

        myController.setMyImageView(canvas);
        Coloring.drawBackground(size, rows, columns, gc);
        Coloring.drawSnake(gc, size, this);
        Coloring.drawFood(gc, food, size);
        eatFood(food, rows, columns);
        killYourself();
        Game.getScore().printScore(gc);
    }

    public void eatFood(Food food, int rows, int columns) {
        if (head.x == food.getPlace().x
                && head.y == food.getPlace().y) {
            snake.add(new Point(0, 0));
            Game.setFood(newFood(rows, columns, this));
            Game.getScore().updateScore();
            //Game.updateSpeed(timeline);
        }
    }

    public void killYourself() {
        for (int i = 1; i < snake.size(); ++i) {
            if (head.x == snake.get(i).x && head.y == snake.get(i).y) {
                Game.setGameOver();
                break;
            }
        }
    }

    public List<Point> getSnake() {
        return snake;
    }

    public void addPoint(int x, int y) {
        snake.add(new Point(x, y));
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

}
