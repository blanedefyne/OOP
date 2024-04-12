package ru.nsu.zelenin.snake;

import java.util.Objects;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class for running snake application.
 */
public class Game extends Application {
    private int size = 30;
    private int rows = 20;
    private int columns = 20;
    private int goalScore = 30;
    private static final Score score = new Score();

    private static double speed = 100;
    private static boolean isPaused = false;
    private static int imgId;
    private static int sndId;
    private final Stage loseStage = new Stage();
    private final Stage winStage = new Stage();
    private static Stage stage;
    private static Food food;
    private static Timeline timeline;
    private static Snake snake;
    private static MainController myController;
    private static final BooleanProperty gameOver = new SimpleBooleanProperty(false);

    /**
     * Start() method.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception - exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        myController = loader.getController();
        myController.initialize();
        myController.getCurrentSound().playStuff();
        myController.setGame(this);
        Canvas canvas = myController.getMyCanvas();
        int width = columns * size;
        int height = rows * size;
        canvas.setWidth(width);
        canvas.setHeight(height);

        stage = primaryStage;
        snake = new Snake(columns, rows);
        initializeSnake(columns, rows);

        VBox root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake");
        primaryStage.getIcons().add(
                new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream("icons/snake.png"))));

        scene.setOnKeyPressed(Keys.getKeys());
        food = Food.newFood(rows, columns, snake);

        timeline = new Timeline(new KeyFrame(
                Duration.millis(speed),
                event -> snake.show(this)));
        timeline.setCycleCount(Animation.INDEFINITE);

        gameOver.addListener(((observable, oldValue, newValue) -> {
            if (newValue) {
                timeline.stop();
                myController.getCurrentSound().stopStuff();
                try {
                    Lose lost = new Lose(this);
                    lost.start(loseStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                timeline.play();
            }
        }));

        score.getScore().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == goalScore) {
                timeline.stop();
                timeline.setCycleCount(0);
                myController.getCurrentSound().stopStuff();
                Sound winSound = new Sound("win.mp3");
                winSound.playStuffOnce();
                try {
                    Win win = new Win(this);
                    win.start(winStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        timeline.play();
        primaryStage.show();

    }

    /**
     * main() method.
     *
     * @param args - arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Method for initializing snake.
     *
     * @param r - rows
     * @param c - columns
     */
    public static void initializeSnake(int r, int c) {
        if (!snake.getSnake().isEmpty()) {
            snake.clear();
        }
        snake.setHead(r, c);
        snake.setCurrentDirection(Direction.LEFT);
    }

    /**
     * Simple getter.
     *
     * @return score
     */
    public static Score getScore() {
        return Game.score;
    }

    /**
     * Simple setter.
     *
     * @param food - new food
     */
    public static void setFood(Food food) {
        Game.food = food;
    }

    /**
     * Simple setter.
     *
     * @param var - new value
     */
    public static void setGameOver(boolean var) {
        Game.gameOver.set(var);
    }

    /**
     * Simple getter.
     *
     * @return timeline
     */
    public static Timeline getTimeline() {
        return Game.timeline;
    }

    /**
     * Simple getter.
     *
     * @return snake
     */
    public static Snake getSnake() {
        return Game.snake;
    }

    /**
     * Simple getter.
     *
     * @return stage
     */
    public Stage getStage() {
        return Game.stage;
    }

    /**
     * Simple setter.
     *
     * @param sndId - sound index
     */
    public static void setSndId(int sndId) {
        Game.sndId = sndId;
    }

    /**
     * Simple setter.
     *
     * @param imgId - image index
     */
    public static void setImgId(int imgId) {
        Game.imgId = imgId;
    }

    /**
     * Simple getter.
     *
     * @return image index
     */
    public static int getImgId() {
        return Game.imgId;
    }

    /**
     * Simple getter.
     *
     * @return sound index
     */
    public static int getSndId() {
        return Game.sndId;
    }

    /**
     * Simple getter.
     *
     * @return boolean - isPaused or not
     */
    public static boolean isPaused() {
        return Game.isPaused;
    }

    /**
     * Simple setter.
     *
     * @param var - new value
     */
    public static void setIsPaused(boolean var) {
        Game.isPaused = var;
    }

    /**
     * Simple getter.
     *
     * @return rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Simple getter.
     *
     * @return columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Simple setter.
     *
     * @param size - new size of a square
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Simple getter.
     *
     * @return MainController of this game
     */
    public static MainController getMyController() {
        return Game.myController;
    }

    /**
     * Method for changing snake's speed.
     *
     * @param newSpeed - new speed
     */
    public static void updateSpeed(double newSpeed) {
        timeline.stop();
        Game.speed = newSpeed;
        Duration newDuration = Duration.millis(newSpeed);
        KeyFrame keyFrame = timeline.getKeyFrames().get(0);
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().add(new KeyFrame(newDuration, keyFrame.getOnFinished()));
        timeline.play();
    }

    /**
     * Simple getter.
     *
     * @return speed
     */
    public static double getSpeed() {
        return Game.speed;
    }

    /**
     * Simple setter.
     *
     * @param rows - new rows
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Simple setter.
     *
     * @param columns - new columns
     */
    public void setColumns(int columns) {
        this.columns = columns;
    }

    /**
     * Simple getter.
     *
     * @return square size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Simple getter.
     *
     * @return goal score
     */
    public int getGoalScore() {
        return this.goalScore;
    }

    /**
     * Simple setter.
     *
     * @param goalScore - new score goal
     */
    public void setGoalScore(int goalScore) {
        this.goalScore = goalScore;
    }

    /**
     * Simple getter.
     *
     * @return food
     */
    public static Food getFood() {
        return food;
    }


}