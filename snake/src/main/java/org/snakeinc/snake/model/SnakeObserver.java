package org.snakeinc.snake.model;

public interface SnakeObserver {
    void onSnakeMoved(int headX, int headY);
}
