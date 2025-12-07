package org.snakeinc.snake.model;

public class PoisonedState implements SnakeState {

    @Override
    public Cell moveUp(Snake snake) {
        // Up and Down are swapped when poisoned
        int x = snake.getHead().getX();
        int y = snake.getHead().getY();
        return snake.grid.getTile(x, y + 1);
    }

    @Override
    public Cell moveDown(Snake snake) {
        int x = snake.getHead().getX();
        int y = snake.getHead().getY();
        return snake.grid.getTile(x, y - 1);
    }

    @Override
    public Cell moveLeft(Snake snake) {
        int x = snake.getHead().getX();
        int y = snake.getHead().getY();
        return snake.grid.getTile(x - 1, y);
    }

    @Override
    public Cell moveRight(Snake snake) {
        int x = snake.getHead().getX();
        int y = snake.getHead().getY();
        return snake.grid.getTile(x + 1, y);
    }

}
