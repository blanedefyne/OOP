package ru.nsu.zelenin.snake;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;

/**
 * Class-controller for resizing application.
 */
public class ResizeController {
    private Game game;
    private Stage stage;

    @FXML
    private TextField mySize;
    @FXML
    private TextField myRows;
    @FXML
    private TextField myColumns;

    /**
     * Simple setter.
     *
     * @param game - our game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Method for showing current size when opening window.
     */
    public void setData() {
        int size = game.getSQUARE_SIZE();
        int rows = game.getROWS();
        int columns = game.getCOLUMNS();
        mySize.setText(String.valueOf(size));
        myRows.setText(String.valueOf(rows));
        myColumns.setText(String.valueOf(columns));
    }

    /**
     * Method for changing size.
     * If needs - it will replace new food
     */
    @FXML
    public void changeSize() {
        int newSize = Integer.parseInt(mySize.getText());
        int newRows = Integer.parseInt(myRows.getText());
        int newColumns = Integer.parseInt(myColumns.getText());
        Canvas canvas = Game.getMyController().getMyCanvas();
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        canvas.setHeight(newRows * newSize);
        canvas.setWidth(newColumns * newSize);
        boolean biggerWidth = (newColumns * newSize) > w;
        boolean biggerHeight = (newRows * newSize) > h;
        if (Game.getFood().place().x > newColumns - 1 ||
                Game.getFood().place().y > newRows - 1) {
            Game.setFood(Food.newFood(newColumns, newRows, Game.getSnake()));
        }
        if (biggerHeight || biggerWidth) {
            game.getStage().sizeToScene();
        }
        game.setCOLUMNS(newColumns);
        game.setROWS(newRows);
        game.setSQUARE_SIZE(newSize);
        stage.close();
    }

    /**
     * Simple setter.
     *
     * @param stage - stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
