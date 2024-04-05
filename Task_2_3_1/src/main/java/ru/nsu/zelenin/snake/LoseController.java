package ru.nsu.zelenin.snake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;


public class LoseController {
    private Game game;
    private Timeline timeline;
    private Stage primaryStage;


    @FXML
    private Button againButton;

    @FXML
    public void restartGame() {

        primaryStage.close();
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
