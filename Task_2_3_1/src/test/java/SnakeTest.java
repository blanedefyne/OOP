import org.junit.jupiter.api.Test;
import ru.nsu.zelenin.snake.Food;
import ru.nsu.zelenin.snake.Score;
import ru.nsu.zelenin.snake.Snake;
import ru.nsu.zelenin.snake.Sound;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SnakeTest {

    @Test
    public void testSnakeHead() {
        Snake snake = new Snake(20, 20);
        int x = snake.getSnake().get(0).x;
        int y = snake.getSnake().get(0).y;
        assertTrue(x == 9 && y == 9);
    }

    @Test
    public void testFood() {
        Snake snake = new Snake(10, 10);
        snake.addPoint(new Point(4, 5));
        snake.addPoint(new Point(4, 6));
        snake.addPoint(new Point(4, 7));
        Food food = Food.newFood(10, 10, snake);
        boolean isOn = false;
        for (var point : snake.getSnake()) {
            if (point.x == food.place().x
                    && point.y == food.place().y) {
                isOn = true;
                break;
            }
        }
        assertFalse(isOn);
    }

    @Test
    public void moveSnake() {
        Snake snake = new Snake(10, 10);
        snake.addPoint(new Point(4, 5));
        snake.addPoint(new Point(4, 6));
        snake.addPoint(new Point(4, 7));
        snake.moveUp(10);
        snake.moveUp(10);
        snake.moveUp(10);
        var head = snake.getSnake().get(0);
        assertTrue(head.x == 4 && head.y == 1);
    }

    @Test
    public void clearSnake() {
       Snake snake = new Snake(130, 145);
       snake.addPoint(new Point(64, 73));
       snake.addPoint(new Point(64, 74));
       snake.addPoint(new Point(64, 75));
       snake.clear();
        assertEquals(1, snake.getSnake().size());
    }

    @Test
    public void scoreTest() {
        Score score = new Score();
        score.updateScore();
        score.updateScore();
        score.updateScore();
        score.updateScore();
        score.updateScore();
        assertEquals(5, score.getScore().get());
    }

    @Test
    public void scoreTest1() {
        Score score = new Score();
        score.updateScore();
        score.updateScore();
        score.updateScore();
        score.clearScore();
        assertEquals(0, score.getScore().get());
    }

    @Test
    public void soundTest() {
        Sound sound = new Sound("sounds/siu.mp3");
        sound.playStuffOnce();
        assertTrue(sound.getSound().isPlaying());
    }
}

