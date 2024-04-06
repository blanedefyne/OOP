package ru.nsu.zelenin.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Lose extends Application {
    private final Game game;
    private Stage stage;

    public Lose(Game game, Stage stage) {
        this.game = game;
        this.stage = stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("lose.fxml"));
        Pane root = loader.load();
        LoseController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        controller.setLose(this);
        controller.setGame(game);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Stage getStage() {
        return stage;
    }
}
