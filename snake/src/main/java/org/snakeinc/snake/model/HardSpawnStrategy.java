package org.snakeinc.snake.model;
import java.util.Random;

import org.snakeinc.snake.GameParams;

public class HardSpawnStrategy implements FoodSpawnStrategy {
    private final Random random = new Random();

    @Override
    public Cell chooseSpawnCell(Cell cell, Grid grid, Snake snake) {
        int distanceFromEdge = random.nextInt(1, 5);

        int maxX = GameParams.TILES_X - distanceFromEdge;
        int maxY = GameParams.TILES_Y - distanceFromEdge;

        for (int attempt = 0; attempt < 100; attempt++) {
            boolean verticalSide = random.nextBoolean();
            int x, y;

            if (verticalSide) {
                x = (random.nextBoolean()) ? 0 : maxX;
                y = random.nextInt(0, GameParams.TILES_Y);
            } else {
                y = (random.nextBoolean()) ? 0 : maxY;
                x = random.nextInt(0, GameParams.TILES_X);
            }

            Cell c = grid.getTile(x, y);

            if (c != null && !c.containsASnake()) {
                return c;
            }
        }

        return new RandomSpawnStrategy().chooseSpawnCell(cell, grid, snake);
    }
}