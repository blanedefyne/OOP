package ru.nsu.zelenin.snake;

import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Another class-application for representing lose-screen.
 */
public class Lose extends Application {
    private final Game game;

    public Lose(Game game) {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("lose.fxml"));
        Pane root = loader.load();
        LoseController controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setGame(game);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream("icons/restart.png"))));
        primaryStage.show();
    }

}
