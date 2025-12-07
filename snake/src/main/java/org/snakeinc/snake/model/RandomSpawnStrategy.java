package org.snakeinc.snake.model;

import java.util.Random;

import org.snakeinc.snake.GameParams;

public class RandomSpawnStrategy implements FoodSpawnStrategy {

    @Override
    public Cell chooseSpawnCell(Cell cell, Grid grid, Snake snake) {
        if (cell == null) {
            while (cell == null || cell.containsASnake()) {
                    var random = new Random();
                    cell = grid.getTile(random.nextInt(0, GameParams.TILES_X), random.nextInt(0, GameParams.TILES_Y));
            }
        }
        return cell;
    }
}
