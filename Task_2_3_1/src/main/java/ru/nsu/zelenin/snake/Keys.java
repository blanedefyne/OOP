package ru.nsu.zelenin.snake;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Keys {

    public static EventHandler<KeyEvent> getKeys(Snake snake, Timeline timeline) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                switch (code) {
                    case RIGHT -> {
                        if (snake.getCurrentDirection() != Direction.LEFT) {
                            snake.setCurrentDirection(Direction.RIGHT);
                        }
                    }
                    case LEFT -> {
                        if (snake.getCurrentDirection() != Direction.RIGHT) {
                            snake.setCurrentDirection(Direction.LEFT);
                        }
                    }
                    case UP -> {
                        if (snake.getCurrentDirection() != Direction.DOWN) {
                            snake.setCurrentDirection(Direction.UP);
                        }
                    }
                    case DOWN -> {
                        if (snake.getCurrentDirection() != Direction.UP) {
                            snake.setCurrentDirection(Direction.DOWN);
                        }
                    }
                    case SPACE -> {
                        if (timeline.getStatus() == Animation.Status.PAUSED) {
                            timeline.play();
                        } else if (timeline.getStatus() == Animation.Status.RUNNING) {
                            timeline.pause();
                        }
                    }
                }
            }
        };
    }
}
