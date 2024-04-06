package ru.nsu.zelenin.snake;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Game extends Application {
    private final int SQUARE_SIZE = 30;

    private int ROWS;
    private int COLUMNS;

    private static boolean isPaused = false;
    private static int imgId = 0;
    private static int sndId = 0;
    private static final Stage loseStage = new Stage();
    private static Stage stage;
    private static Food food;
    private static Timeline timeline;
    private static Snake snake;
    private static MainController myController;
    private static final Score score = new Score();
    private static final BooleanProperty gameOver = new SimpleBooleanProperty(false);

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        AnchorPane root = loader.load();
        myController = loader.getController();
        myController.getCurrentSound().playStuff();
        myController.setGame(this);
        Canvas canvas = myController.getMyCanvas();
        int WIDTH = (int) canvas.getWidth();
        int HEIGHT = (int) canvas.getHeight();

        ROWS = HEIGHT / SQUARE_SIZE;
        COLUMNS = WIDTH / SQUARE_SIZE;

        stage = primaryStage;
        snake = new Snake(ROWS, COLUMNS);
        Game.initializeSnake(ROWS, COLUMNS);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake");

        scene.setOnKeyPressed(Keys.getKeys());
        food = Food.newFood(ROWS, COLUMNS, snake);

        timeline = new Timeline(new KeyFrame(
                Duration.millis(100),
                event -> snake.show(canvas, SQUARE_SIZE, food, myController)));
        timeline.setCycleCount(Animation.INDEFINITE);

        gameOver.addListener(((observable, oldValue, newValue) -> {
            if (newValue) {
                timeline.stop();
                myController.getCurrentSound().stopStuff();
                try {
                    Lose lost = new Lose(this, loseStage);
                    lost.start(loseStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                timeline.play();
            }
        }));
        timeline.play();
        primaryStage.show();

    }

    public static void initializeSnake(int r, int c) {
        snake.clear();
        snake.setHead(r, c);
        snake.setCurrentDirection(Direction.LEFT);
        var list = snake.getSnake();
        snake.addPoint(list.get(0).x + 1, list.get(0).y);
        snake.addPoint(list.get(0).x + 2, list.get(0).y);
        snake.addPoint(list.get(0).x + 3, list.get(0).y);
    }

    public static Score getScore() {
        return score;
    }

    public static void setFood(Food food) {
        Game.food = food;
    }

    public static void setGameOver(boolean var) {
        Game.gameOver.set(var);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Timeline getTimeline() {
        return Game.timeline;
    }

    public static Snake getSnake() {
        return Game.snake;
    }

    public static void setSnake(Snake snake) {
        Game.snake = snake;
    }

    public Stage getStage() {
        return Game.stage;
    }

    public static void setSndId(int sndId) {
        Game.sndId = sndId;
    }

    public static void setImgId(int imgId) {
        Game.imgId = imgId;
    }

    public static int getImgId() {
        return Game.imgId;
    }

    public static int getSndId() {
        return Game.sndId;
    }

    public static boolean isPaused() {
        return Game.isPaused;
    }

    public static void setIsPaused(boolean var) {
        Game.isPaused = var;
    }

    public int getROWS() {
        return ROWS;
    }

    public int getCOLUMNS() {
        return COLUMNS;
    }

    public static MainController getMyController() {
        return Game.myController;
    }
}