package ru.nsu.zelenin.snake;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;


public class LoseController {
    private Game game;
    private Stage primaryStage;
    private Lose lose;


    @FXML
    public void restartGame() {
        if (Game.getTimeline().getStatus() == Animation.Status.STOPPED) {
            Game.getTimeline().play();
            Game.setGameOver(false);
        }
        Game.getMyController().getCurrentSound().stopStuff();
        int r = game.getROWS();
        int c = game.getCOLUMNS();
        Game.getScore().clearScore();
        Game.setFood(Food.newFood(r, c, Game.getSnake()));
        Game.initializeSnake(r, c);
        Game.getMyController().getCurrentSound().playStuff();
        primaryStage.close();
        lose.getStage().close();
    }

    @FXML
    public void quitGame() {
        try {
            game.getStage().close();
            game.stop();
            primaryStage.close();
            lose.stop();
            lose.getStage().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setLose(Lose lose) {
        this.lose = lose;
    }
}
