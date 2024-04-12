package ru.nsu.zelenin.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Another class-application for representing a win-screen.
 */
public class Win extends Application {
    private final Game game;

    public Win(Game game) {
        this.game = game;
    }

    /**
    * Start() method.
    *
    * @param primaryStage the primary stage for this application, onto which
    * the application scene can be set.
    * Applications may create other stages, if needed, but they will not be
    * primary stages.
    * @throws Exception - exception
    */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("win.fxml"));
        AnchorPane root = loader.load();
        WinController winController = loader.getController();
        winController.setStage(primaryStage);
        winController.setGame(game);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("icons/winner.png"))));
        primaryStage.show();
    }
}
