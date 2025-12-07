package org.snakeinc.snake.model;

public interface SnakeState {
    Cell moveUp(Snake snake);
    Cell moveDown(Snake snake);
    Cell moveLeft(Snake snake);
    Cell moveRight(Snake snake);
}
