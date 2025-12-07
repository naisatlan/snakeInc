package org.snakeinc.snake.model;

import lombok.Getter;

import java.util.Random;

import org.snakeinc.snake.exception.DiedOfMalnutrition;
import org.snakeinc.snake.exception.OutOfPlayException;
import org.snakeinc.snake.exception.SelfCollisionException;

@Getter
public class Game {

    private final Grid grid;
    private final Basket basket;
    private final Snake snake;
    public static int score = 0;

    public Game() {
        grid = new Grid();
        basket = new Basket(grid, new RandomSpawnStrategy());

        int snakeType = (int) (Math.random() * 3);
        snake = switch (snakeType) {
            case 0 -> new Anaconda((food, cell) -> basket.removeFoodInCell(food, cell), grid);
            case 1 -> new Python((food, cell) -> basket.removeFoodInCell(food, cell), grid);
            case 2 -> new BoaConstrictor((food, cell) -> basket.removeFoodInCell(food, cell), grid);
            default -> throw new IllegalStateException("Unexpected value: " + snakeType);
        };

        FoodSpawnStrategy strategy = switch (new Random().nextInt(3)) {
            case 0 -> new RandomSpawnStrategy();
            case 1 -> new EasySpawnStrategy(snake);
            case 2 -> new HardSpawnStrategy();
            default -> new RandomSpawnStrategy();
        };
        basket.setStrategy(strategy);

        basket.refillIfNeeded(1, snake);

    }

    public Game(Snake snake, Grid grid, FoodSpawnStrategy strategy) {
        this.grid = grid;
        this.basket = new Basket(grid, strategy);
        this.snake = snake;
        this.basket.refillIfNeeded(1, snake);
    }

    public void iterate(Direction direction) throws OutOfPlayException, SelfCollisionException, DiedOfMalnutrition {
        snake.move(direction);
        basket.refillIfNeeded(1, snake);
    }

    public void resetScore() {
        score = 0;
    }

}
