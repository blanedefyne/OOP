package ru.nsu.zelenin.snake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Class for representing snake, its movement and displaying.
 */
public class Snake {
    private final List<Point> snake = new ArrayList<>();
    private Direction currentDirection;
    private final Point head;

    /**
     * Simple constructor.
     *
     * @param c - columns
     * @param r - rows
     */
    public Snake(int c, int r) {
        head = new Point((c - 1) / 2, (r - 1) / 2);
        snake.add(head);
        currentDirection = Direction.LEFT;
    }

    /**
     * Method moves head to the right.
     *
     * @param columns - columns
     */
    public void moveRight(int columns) {
        head.x++;
        if (head.x >= columns) {
            head.x = 0;
        }
    }

    /**
     * Method moves head to the left.
     *
     * @param columns - columns
     */
    public void moveLeft(int columns) {
        head.x--;
        if (head.x < 0) {
            head.x = columns - 1;
        }
    }

    /**
     * Method moves head up.
     *
     * @param rows - rows
     */
    public void moveUp(int rows) {
        head.y--;
        if (head.y < 0) {
            head.y = rows - 1;
        }
    }

    /**
     * Method moves head down.
     *
     * @param rows - rows
     */
    public void moveDown(int rows) {
        head.y++;
        if (head.y >= rows) {
            head.y = 0;
        }
    }

    /**
     * Method moves the whole snake - its head, depending on the direction, and rest of the body.
     *
     * @param canvas - our canvas
     * @param size - square size
     */
    public void move(Canvas canvas, int size) {
        int rows = (int) (canvas.getHeight() / size);
        int columns = (int) (canvas.getWidth() / size);

        for (int i = snake.size() - 1; i > 0; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        switch (currentDirection) {
            case UP -> moveUp(rows);
            case DOWN -> moveDown(rows);
            case LEFT -> moveLeft(columns);
            case RIGHT -> moveRight(columns);
            default -> {
            }
        }

    }

    /**
     * Method for displaying the snake on the screen.
     *
     * @param game - our game
     */
    public void show(Game game) {
        Canvas canvas = Game.getMyController().getMyCanvas();
        int size = game.getSize();

        controlTheMovement();

        if (!Game.isPaused()) {
            this.move(canvas, size);
        }

        MainController myController = Game.getMyController();
        myController.setMyImageView(canvas);
        Food food = Game.getFood();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int rows = (int) (canvas.getHeight() / size);
        int columns = (int) (canvas.getWidth() / size);
        Coloring.drawBackground(size, rows, columns, gc);
        Coloring.drawFood(gc, food, size);
        Coloring.drawSnake(gc, size, this);
        eatFood(game);
        killYourself();
        Game.getScore().printScore(game);
    }

    /**
     * Method for eating food.
     * It increases snakes' length, updates score and increases speed
     *
     * @param game - our game
     */
    public void eatFood(Game game) {
        Canvas canvas = Game.getMyController().getMyCanvas();
        int size = game.getSize();
        Food food = Game.getFood();
        int rows = (int) (canvas.getHeight() / size);
        int columns = (int) (canvas.getWidth() / size);
        if (head.x == food.place().x
                && head.y == food.place().y) {
            snake.add(new Point(0, 0));
            Game.setFood(Food.newFood(columns, rows, this));
            Game.getScore().updateScore();
            Game.updateSpeed(Game.getSpeed() * 0.99);
        }
    }

    /**
     * Method for checking if the snake killed itself.
     */
    public void killYourself() {
        for (int i = 1; i < snake.size(); ++i) {
            if (head.x == snake.get(i).x && head.y == snake.get(i).y) {
                Game.setGameOver(true);
                break;
            }
        }
    }

    /**
     * Simple getter.
     *
     * @return snake
     */
    public List<Point> getSnake() {
        return snake;
    }

    /**
     * Method adds a point to snake's body.
     *
     * @param point - given point
     */
    public void addPoint(Point point) {
        snake.add(point);
    }

    /**
     * Method clears snake's body - EXCEPT for its head.
     */
    public void clear() {
        snake.clear();
        snake.add(head);
    }

    /**
     * Simple setter.
     *
     * @param currentDirection - changed current direction
     */
    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    /**
     * Method sets snake's head - for its initializing.
     *
     * @param r  - rows
     * @param c - columns
     */
    public void setHead(int r, int c) {
        head.x = (r - 1) / 2;
        head.y = (c - 1) / 2;
    }

    /**
     * Method for helping with too fast keys-clicking.
     * It gets only last saved direction from Keys class
     * So if we clicked UP and then RIGHT faster then frames frequency
     * ONLY RIGHT direction will be set to snake
     */
    private void controlTheMovement() {
        switch (currentDirection) {
            case RIGHT -> {
                if (Keys.getCurrDirection() != Direction.LEFT) {
                    currentDirection = Keys.getCurrDirection();
                }
            }
            case LEFT -> {
                if (Keys.getCurrDirection() != Direction.RIGHT) {
                    currentDirection = Keys.getCurrDirection();
                }
            }
            case UP -> {
                if (Keys.getCurrDirection() != Direction.DOWN) {
                    currentDirection = Keys.getCurrDirection();
                }
            }
            case DOWN -> {
                if (Keys.getCurrDirection() != Direction.UP) {
                    currentDirection = Keys.getCurrDirection();
                }
            }
            default -> {

            }

        }
    }

}
