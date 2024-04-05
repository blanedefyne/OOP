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

public class Game extends Application {
    private final int SQUARE_SIZE = 30;

    private int ROWS;
    private int COLUMNS;

    private static Food food;
    private static Timeline timeline;
    private static Snake snake;
    private static final Score score = new Score();
    private static final BooleanProperty gameOver = new SimpleBooleanProperty(false);

    @Override
    public void start(Stage primaryStage) throws Exception {
        snake = initializeSnake();

        Sound shaman = new Sound("sounds/timon & pumba.mp3");
        shaman.playStuff();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        AnchorPane root = loader.load();
        MainController myController = loader.getController();
        myController.setCurrentSound(shaman);
        myController.setGame(this);
        myController.setStage(primaryStage);
        Canvas canvas = myController.getMyCanvas();
        int WIDTH = (int) canvas.getWidth();
        int HEIGHT = (int) canvas.getHeight();

        ROWS = HEIGHT / SQUARE_SIZE;
        COLUMNS = WIDTH / SQUARE_SIZE;

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake");

        food = Food.newFood(ROWS, COLUMNS, snake);

        timeline = new Timeline(new KeyFrame(
                Duration.millis(100),
                e -> snake.move(canvas, SQUARE_SIZE, food, myController)));
        timeline.setCycleCount(Animation.INDEFINITE);

        scene.setOnKeyPressed(Keys.getKeys(snake, timeline));

        gameOver.addListener(((observable, oldValue, newValue) -> {
            if (newValue) {
                timeline.stop();
                shaman.stopStuff();
                Lose lost = new Lose();
                try {
                    lost.start(new Stage());
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

    public Snake initializeSnake() {
        Snake snake = new Snake(ROWS, COLUMNS);
        var list = snake.getSnake();
        snake.addPoint(list.get(0).x + 1, list.get(0).y);
        snake.addPoint(list.get(0).x + 2, list.get(0).y);
        snake.addPoint(list.get(0).x + 3, list.get(0).y);
        return snake;
    }

    public static Score getScore() {
        return score;
    }

    public static void setFood(Food food) {
        Game.food = food;
    }

    public static void setGameOver() {
        Game.gameOver.set(true);
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

}