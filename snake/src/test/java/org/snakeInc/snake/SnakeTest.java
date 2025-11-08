package org.snakeInc.snake;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.snakeinc.snake.exception.OutOfPlayException;
import org.snakeinc.snake.exception.SelfCollisionException;
import org.snakeinc.snake.model.Game;
import org.snakeinc.snake.model.Snake;

public class SnakeTest {


    @Test
    public void snakeEatApplesAfterMove_ReturnsCorrectBodySize() throws OutOfPlayException, SelfCollisionException {
        Snake snake = new Snake();
        Game.getCurrentGame().getBasket().addApple(Game.getCurrentGame().getGrid().getTile(5, 4));
        snake.move('U');
        Assertions.assertEquals(2, snake.getSize());
    }

    @Test
    void snakeMovesUp_ReturnCorrectHead() throws OutOfPlayException, SelfCollisionException {
        Snake snake = new Snake();
        snake.move('U');
        Assertions.assertEquals(5, snake.getHead().getX());
        Assertions.assertEquals(5, snake.getHead().getY());
    }

}
