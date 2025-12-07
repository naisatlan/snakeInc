package org.snakeinc.snake.model;

public interface FoodSpawnStrategy {
    Cell chooseSpawnCell(Cell cell, Grid grid, Snake snake);    
}
