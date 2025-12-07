package org.snakeinc.snake.model;
import java.util.Random;

public class EasySpawnStrategy implements FoodSpawnStrategy {
    private final Snake snake;
    private final Random random = new Random();

    public EasySpawnStrategy(Snake snake) {
        this.snake = snake;
    }

    @Override
    public Cell chooseSpawnCell(Cell cell, Grid grid, Snake snake) {
        Cell head = snake.getHead();
        int hx = head.getX();
        int hy = head.getY();

        for (int attempt = 0; attempt < 100; attempt++) {

            int dx = random.nextInt(-2, 3);
            int dy = random.nextInt(-2, 3);

            int x = hx + dx;
            int y = hy + dy;

            Cell c = grid.getTile(x, y);

            if (c != null && !c.containsASnake()) {
                return c;
            }
        }

        return new RandomSpawnStrategy().chooseSpawnCell(cell, grid, snake);
    }
}

