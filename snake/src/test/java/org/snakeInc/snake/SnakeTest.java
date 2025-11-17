package org.snakeInc.snake;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.snakeinc.snake.exception.OutOfPlayException;
import org.snakeinc.snake.exception.SelfCollisionException;
import org.snakeinc.snake.model.Game;
import org.snakeinc.snake.model.Direction;

public class SnakeTest {

    Game game = new Game();

    @Test
    public void snakeEatApplesAfterMove_ReturnsCorrectBodySize() throws OutOfPlayException, SelfCollisionException {
        game.getBasket().addApple(game.getGrid().getTile(5, 4));
        game.getSnake().move(Direction.U);
        Assertions.assertEquals(2, game.getSnake().getSize());
    }

    @Test
    public void snakeMovesUp_ReturnCorrectHead() throws OutOfPlayException, SelfCollisionException {
        game.getSnake().move(Direction.U);
        Assertions.assertEquals(5, game.getSnake().getHead().getX());
        Assertions.assertEquals(4, game.getSnake().getHead().getY());
    }

    @Test
    public void snakeOutOfPlay() {
        Assertions.assertThrows(OutOfPlayException.class, () -> {
            while (true) {
                game.getSnake().move(Direction.U);
            }
        });
    }

    @Test
    public void selfCollisionExceptionIsThrown_WhenSnakeCollidesWithItself() {
        Assertions.assertThrows(SelfCollisionException.class, () -> {
            game.getBasket().addApple(game.getGrid().getTile(5, 4));
            game.getSnake().move(Direction.U); // Eat apple
            game.getBasket().addApple(game.getGrid().getTile(6, 4));
            game.getSnake().move(Direction.R); // Eat apple
            game.getBasket().addApple(game.getGrid().getTile(6, 5));
            game.getSnake().move(Direction.D); // Eat apple
            game.getSnake().move(Direction.L); // This move should cause self-collision
        });
    }

}
