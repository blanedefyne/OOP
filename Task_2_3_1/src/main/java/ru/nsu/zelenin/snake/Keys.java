package ru.nsu.zelenin.snake;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Class for handling keys pressing.
 */
public class Keys {
    private static Direction currDirection = Direction.LEFT;

    /**
     * Method sets a direction of snake and pause it when pressing space.
     *
     * @return eventHandler
     */
    public static EventHandler<KeyEvent> getKeys() {
        return event -> {
            KeyCode code = event.getCode();
                switch (code) {
                    case RIGHT -> {
                        currDirection = Direction.RIGHT;
                    }
                    case LEFT -> {
                        currDirection = Direction.LEFT;
                    }
                    case UP -> {
                        currDirection = Direction.UP;
                    }
                    case DOWN -> {
                        currDirection = Direction.DOWN;
                    }
                    case SPACE -> {
                        Game.setIsPaused(!Game.isPaused());
                    }
                }
        };
    }

    /**
     * Simple getter.
     *
     * @return direction
     */
    public static Direction getCurrDirection() {
        return Keys.currDirection;
    }
}
