package org.snakeinc.snake.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Data;

@Data
public class Basket implements SnakeObserver {

    private Grid grid;
    private List<Cell> foodCells;
    private FoodSpawnStrategy strategy;
    private Random random = new Random();
    private Snake observedSnake;

    public Basket(Grid grid, FoodSpawnStrategy strategy) {
        foodCells = new ArrayList<>();
        this.grid = grid;
        this.strategy = strategy;
    }

    // Construteur pour les tests unitaires (pour contrôler le Random)
    public Basket(Grid grid, FoodSpawnStrategy strategy, Random random) {
        foodCells = new ArrayList<>();
        this.grid = grid;
        this.strategy = strategy;
        this.random = random == null ? new Random() : random;
    }

    public void addFood(Cell cell, Snake snake) {
        Cell spawnCell = strategy.chooseSpawnCell(cell, grid, snake);
        FoodFactory.createFoodInCell(spawnCell);
        foodCells.add(spawnCell);
    }

    public void addSpecificFood(Cell cell, Food food) {
        cell.addFood(food);
        foodCells.add(cell);
    }


    public void removeFoodInCell(Food food, Cell cell) {
        cell.removeFood();
        foodCells.remove(cell);
    }

    public boolean isEmpty() {
        return foodCells.isEmpty();
    }

    private void refill(int nFoods, Snake snake) {
        for (int i = 0; i < nFoods; i++) {
            addFood(null, snake);
        }
    }

    public void refillIfNeeded(int nFoods, Snake snake) {
        if (this.observedSnake != snake) {
            this.observedSnake = snake;
            try {
                snake.addObserver(this);
            } catch (Exception ignored) {
            }
        }

        int missingFood = nFoods - foodCells.size();
        if (missingFood > 0) {
            refill(missingFood, snake);
        }
    }

    @Override
    public void onSnakeMoved(int headX, int headY) {
        List<Cell> snapshot = new ArrayList<>(foodCells);
        for (Cell c : snapshot) {
            if (c == null) continue;
            int dx = Math.abs(headX - c.getX());
            int dy = Math.abs(headY - c.getY());
            double dist = Math.sqrt(dx * dx + dy * dy);
            if (dist < 2 && random.nextDouble() < 0.05) {
                moveFoodRandomly(c);
            }
        }
    }

    private void moveFoodRandomly(Cell cell) {
        if (cell == null) return;
        Food f = cell.getFood();
        if (f == null) return;
        cell.removeFood();
        foodCells.remove(cell);

        Cell target = strategy.chooseSpawnCell(cell, grid, observedSnake);
        if (target == null) return;
        FoodFactory.createFoodInCell(target);
        foodCells.add(target);
    }

}
