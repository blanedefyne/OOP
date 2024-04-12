package ru.nsu.zelenin.snake;

import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * Another class-application for representing resizing-screen.
 */
public class Resize extends Application {
    private final Game game;

    public Resize(Game game) {
        this.game = game;
    }

    /**
    *
    * @param primaryStage the primary stage for this application, onto which
    * the application scene can be set.
    * Applications may create other stages, if needed, but they will not be
    * primary stages.
    * @throws Exception - exception
    */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resize.fxml"));
        AnchorPane root = loader.load();
        ResizeController resizeController = loader.getController();
        resizeController.setGame(game);
        resizeController.setData();
        resizeController.setStage(primaryStage);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("icons/resize.png"))));
        primaryStage.show();
    }
}
