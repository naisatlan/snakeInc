package org.snakeInc.snake;

import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.snakeinc.snake.GameParams;
import org.snakeinc.snake.exception.DiedOfMalnutrition;
import org.snakeinc.snake.exception.OutOfPlayException;
import org.snakeinc.snake.exception.SelfCollisionException;
import org.snakeinc.snake.model.Game;
import org.snakeinc.snake.model.Grid;
import org.snakeinc.snake.model.Direction;
import org.snakeinc.snake.model.EasySpawnStrategy;
import org.snakeinc.snake.model.FoodSpawnStrategy;
import org.snakeinc.snake.model.BoaConstrictor;
import org.snakeinc.snake.model.Anaconda;
import org.snakeinc.snake.model.Python;
import org.snakeinc.snake.model.Snake;
import org.snakeinc.snake.model.Apple;
import org.snakeinc.snake.model.Basket;
import org.snakeinc.snake.model.Brocoli;
import org.snakeinc.snake.model.RandomSpawnStrategy;
import org.snakeinc.snake.model.HardSpawnStrategy;
import org.snakeinc.snake.model.EasySpawnStrategy;
import org.snakeinc.snake.model.Cell;

public class SnakeTest {
    Random random = new Random();

    Snake snakeAnaconda;
    Game gameAnaconda;

    Snake snakePython;
    Game gamePython;

    Snake snakeBoa;
    Game gameBoa;

    Game genericGame;

    Game easyGame;
    Game hardGame;
    Game randomGame;

    Apple apple;
    Brocoli brocoli;

    @BeforeEach
    public void setup() {
        Grid grid1 = new Grid();
        snakeAnaconda = new Anaconda((apple, cell) -> {}, grid1);
        gameAnaconda = new Game(snakeAnaconda, grid1, new RandomSpawnStrategy());

        Grid grid2 = new Grid();
        snakePython = new Python((apple, cell) -> {}, grid2);
        gamePython = new Game(snakePython, grid2, new RandomSpawnStrategy());

        Grid grid3 = new Grid();
        snakeBoa = new BoaConstrictor((apple, cell) -> {}, grid3);
        gameBoa = new Game(snakeBoa, grid3, new RandomSpawnStrategy());

        genericGame = new Game();

        apple = new Apple(false);
        brocoli = new Brocoli(true);
    }


    @Test
    public void anacondaEatsApple_ShouldGrow() throws Exception {
        gameAnaconda.getBasket().addSpecificFood(gameAnaconda.getGrid().getTile(5, 4), apple);
        snakeAnaconda.move(Direction.U);

        Assertions.assertEquals(4, snakeAnaconda.getSize());
    }

    @Test
    public void anacondaEatsBroccoli_ShouldShrinkOrDie() throws Exception {
        gameAnaconda.getBasket().addSpecificFood(gameAnaconda.getGrid().getTile(5, 4), brocoli);

        snakeAnaconda.move(Direction.U);
        Assertions.assertEquals(1, snakeAnaconda.getSize());

        gameAnaconda.getBasket().addSpecificFood(gameAnaconda.getGrid().getTile(5, 3), brocoli);

        Assertions.assertThrows(DiedOfMalnutrition.class, () -> {
            snakeAnaconda.move(Direction.U);
        });
    }

    @Test
    public void pythonEatsApple_ShouldStay() throws Exception {
        gamePython.getBasket().addSpecificFood(gamePython.getGrid().getTile(5, 4), apple);
        snakePython.move(Direction.U);

        Assertions.assertEquals(3, snakePython.getSize());
    }

    @Test
    public void pythonEatsBroccoli_ShouldShrinkOrDie() throws Exception {
        gamePython.getBasket().addSpecificFood(gamePython.getGrid().getTile(5, 4), brocoli);

        Assertions.assertThrows(DiedOfMalnutrition.class, () -> {
            snakePython.move(Direction.U);
        });
    }

    @Test
    public void boaConstrictorEatsApple_ShouldShrinkOrDie() throws Exception {
        gameBoa.getBasket().addSpecificFood(gameBoa.getGrid().getTile(5, 4), apple);
        snakeBoa.move(Direction.U);
        Assertions.assertEquals(2, snakeBoa.getSize());

        gameBoa.getBasket().addSpecificFood(gameBoa.getGrid().getTile(5, 3), apple);
        snakeBoa.move(Direction.U);
        
        gameBoa.getBasket().addSpecificFood(gameBoa.getGrid().getTile(5, 2), apple);

        Assertions.assertThrows(DiedOfMalnutrition.class, () -> {
            snakeBoa.move(Direction.U);
        });
    }

    @Test
    public void boaConstrictorEatsBroccoli_ShouldStay() throws Exception {
        gameBoa.getBasket().addSpecificFood(gameBoa.getGrid().getTile(5, 4), brocoli);
        snakeBoa.move(Direction.U);

        Assertions.assertEquals(3, snakeBoa.getSize());
    }

    @Test
    public void snakeMovesUp_ReturnCorrectHead() throws OutOfPlayException, SelfCollisionException, DiedOfMalnutrition {
        genericGame.getSnake().move(Direction.U);
        Assertions.assertEquals(5, genericGame.getSnake().getHead().getX());
        Assertions.assertEquals(4, genericGame.getSnake().getHead().getY());
    }

    @Test
    public void snakeOutOfPlay() {
        Assertions.assertThrows(OutOfPlayException.class, () -> {
            while (true) {
                genericGame.getSnake().move(Direction.U);
            }
        });
    }

    @Test
    public void snakesSelfCollision() {
        Assertions.assertThrows(SelfCollisionException.class, () -> {
            gameAnaconda.getBasket().addSpecificFood(gameAnaconda.getGrid().getTile(5, 4), apple);
            gameAnaconda.getSnake().move(Direction.U);
            gameAnaconda.getBasket().addSpecificFood(gameAnaconda.getGrid().getTile(6, 4), apple);
            gameAnaconda.getSnake().move(Direction.R);
            gameAnaconda.getBasket().addSpecificFood(gameAnaconda.getGrid().getTile(6, 5), apple);
            gameAnaconda.getSnake().move(Direction.D);
            gameAnaconda.getSnake().move(Direction.L);
        });
    }

    @Test
    public void scoreCalculationTest() throws Exception {
        // Anaconda: apple not poisoned +2
        gameAnaconda.resetScore();
        gameAnaconda.getBasket().addSpecificFood(gameAnaconda.getGrid().getTile(5, 4), new Apple(false));
        snakeAnaconda.move(Direction.U);
        Assertions.assertEquals(2, Game.score);

        // Anaconda: apple poisoned +0
        gameAnaconda.resetScore();
        gameAnaconda.getBasket().addSpecificFood(gameAnaconda.getGrid().getTile(5, 3), new Apple(true));
        snakeAnaconda.move(Direction.U);
        Assertions.assertEquals(0, Game.score);

        // Python: apple not poisoned +2
        gamePython.resetScore();
        gamePython.getBasket().addSpecificFood(gamePython.getGrid().getTile(5, 4), new Apple(false));
        snakePython.move(Direction.U);
        Assertions.assertEquals(2, Game.score);

        // Anaconda: 1 brocoli not steamed + 1 brocoli not steamed +1
        gameBoa.resetScore();
        gameBoa.getBasket().addSpecificFood(gameBoa.getGrid().getTile(5, 4), new Brocoli(false));
        snakeBoa.move(Direction.U);
        Assertions.assertEquals(1, Game.score);
        gameBoa.getBasket().addSpecificFood(gameBoa.getGrid().getTile(5, 3), new Brocoli(true));
        snakeBoa.move(Direction.U);
        Assertions.assertEquals(1, Game.score);
    }

    @Test
    public void poisonedStateMovementTest() throws Exception {
        gameAnaconda.getBasket().addSpecificFood(gameAnaconda.getGrid().getTile(5, 4), new Apple(true));
        snakeAnaconda.move(Direction.U);
        Assertions.assertEquals(5, snakeAnaconda.getHead().getX());
        Assertions.assertEquals(4, snakeAnaconda.getHead().getY());

        snakeAnaconda.move(Direction.L);
        Assertions.assertEquals(4, snakeAnaconda.getHead().getX());
        Assertions.assertEquals(4, snakeAnaconda.getHead().getY());

        snakeAnaconda.move(Direction.D);
        Assertions.assertEquals(4, snakeAnaconda.getHead().getX());
        Assertions.assertEquals(3, snakeAnaconda.getHead().getY());

        snakeAnaconda.move(Direction.D);

        snakeAnaconda.move(Direction.R);
        Assertions.assertEquals(5, snakeAnaconda.getHead().getX());
        Assertions.assertEquals(2, snakeAnaconda.getHead().getY());

        snakeAnaconda.move(Direction.U);
        Assertions.assertEquals(5, snakeAnaconda.getHead().getX());
        Assertions.assertEquals(3, snakeAnaconda.getHead().getY());
    }

    @Test
    public void damagedStateMovementTest() throws Exception {
        gameAnaconda.getBasket().addSpecificFood(gameAnaconda.getGrid().getTile(5, 4), new Apple(true));
        snakeAnaconda.move(Direction.U);
        gameAnaconda.getBasket().addSpecificFood(gameAnaconda.getGrid().getTile(5, 3), new Apple(true));
        snakeAnaconda.move(Direction.D);

        snakeAnaconda.move(Direction.D);
        Assertions.assertEquals(5, snakeAnaconda.getHead().getX());
        Assertions.assertEquals(2, snakeAnaconda.getHead().getY());

        snakeAnaconda.move(Direction.L);
        Assertions.assertEquals(6, snakeAnaconda.getHead().getX());
        Assertions.assertEquals(2, snakeAnaconda.getHead().getY());

        snakeAnaconda.move(Direction.L);

        snakeAnaconda.move(Direction.U);
        Assertions.assertEquals(7, snakeAnaconda.getHead().getX());
        Assertions.assertEquals(3, snakeAnaconda.getHead().getY());

        snakeAnaconda.move(Direction.R);
        Assertions.assertEquals(6, snakeAnaconda.getHead().getX());
        Assertions.assertEquals(3, snakeAnaconda.getHead().getY());
    }

    @Test
    public void getsBetterBrocoliTest() throws Exception {
        gameAnaconda.getBasket().addSpecificFood(gameAnaconda.getGrid().getTile(5, 4), apple);
        snakeAnaconda.move(Direction.U);
        gameAnaconda.getBasket().addSpecificFood(gameAnaconda.getGrid().getTile(5, 3), new Apple(true));
        snakeAnaconda.move(Direction.U);
        gameAnaconda.getBasket().addSpecificFood(gameAnaconda.getGrid().getTile(6, 3), brocoli);
        snakeAnaconda.move(Direction.R);

        snakeAnaconda.move(Direction.U);
        Assertions.assertEquals(6, snakeAnaconda.getHead().getX());
        Assertions.assertEquals(2, snakeAnaconda.getHead().getY());

        snakeAnaconda.move(Direction.L);
        Assertions.assertEquals(5, snakeAnaconda.getHead().getX());
        Assertions.assertEquals(2, snakeAnaconda.getHead().getY());
    }

    @Test
    public void EasySpawnStrategyTest() {
        EasySpawnStrategy strategy = new EasySpawnStrategy(snakeAnaconda);

        Cell head = snakeAnaconda.getHead();
        int hx = head.getX();
        int hy = head.getY();

        int nearCount = 0;
        int totalRuns = 50;

        for (int i = 0; i < totalRuns; i++) {
            Cell spawnCell = strategy.chooseSpawnCell(null, gameAnaconda.getGrid(), snakeAnaconda);

            int sx = spawnCell.getX();
            int sy = spawnCell.getY();

            boolean isNear =
                    (sx >= hx - 2 && sx <= hx + 2) &&
                    (sy >= hy - 2 && sy <= hy + 2);

            if (isNear) nearCount++;
        }

        Assertions.assertTrue(nearCount >= totalRuns * 0.8);
    }

    @Test
    public void HardSpawnStrategyTest() {
        HardSpawnStrategy strategy = new HardSpawnStrategy();

        int edgeCount = 0;
        int totalRuns = 50;

        for (int i = 0; i < totalRuns; i++) {
            Cell spawnCell = strategy.chooseSpawnCell(null, gameAnaconda.getGrid(), snakeAnaconda);

            int x = spawnCell.getX();
            int y = spawnCell.getY();

            boolean onLeftOrRightEdge = (x < 5 || x >= GameParams.TILES_X - 5);
            boolean onTopOrBottomEdge = (y < 5 || y >= GameParams.TILES_Y - 5);

            if (onLeftOrRightEdge || onTopOrBottomEdge) {
                edgeCount++;
            }
        }

        Assertions.assertTrue(edgeCount >= totalRuns * 0.8);
    }

    @Test
    public void RandomSpawnStrategyTest() {
        RandomSpawnStrategy strategy = new RandomSpawnStrategy();

        int totalRuns = 50;
        for (int i = 0; i < totalRuns; i++) {
            Cell spawnCell = strategy.chooseSpawnCell(null, gameAnaconda.getGrid(), snakeAnaconda);
            Assertions.assertNotNull(spawnCell, "RandomSpawnStrategy returned null cell");
            Assertions.assertFalse(spawnCell.containsASnake(), "RandomSpawnStrategy returned a cell occupied by snake");
            int x = spawnCell.getX();
            int y = spawnCell.getY();
            Assertions.assertTrue(x >= 0 && x < GameParams.TILES_X, "spawn x out of bounds");
            Assertions.assertTrue(y >= 0 && y < GameParams.TILES_Y, "spawn y out of bounds");
        }
    }

    @Test
    public void crazyBasketMovesFood_WhenPassingCloseAndRandomAllows() throws Exception {
        Grid grid = new Grid();
        Snake snake = new Anaconda((f, c) -> {}, grid);

        FoodSpawnStrategy stubStrategy = (cell, g, s) -> g.getTile(0, 0);
        Random alwaysSmall = new Random() {
            @Override public double nextDouble() { return 0.0; }
        };

        Basket basket = new Basket(grid, stubStrategy, alwaysSmall);

        Cell foodCell = grid.getTile(5, 3);
        basket.addSpecificFood(foodCell, new Apple(false));

        basket.refillIfNeeded(1, snake);

        snake.move(Direction.U);

        Assertions.assertFalse(foodCell.containsFood());
        Assertions.assertTrue(grid.getTile(0, 0).containsFood());

        long count = grid.getTiles().values().stream().filter(Cell::containsFood).count();
        Assertions.assertEquals(1, count);
    }

    @Test
    public void crazyBasketDoesNotMove_WhenPassingFarAndRandomAllows() throws Exception {
        Grid grid = new Grid();
        Snake snake = new Anaconda((f, c) -> {}, grid);

        FoodSpawnStrategy stubStrategy = (cell, g, s) -> g.getTile(0, 0);
        Random alwaysSmall = new Random() {
            @Override public double nextDouble() { return 0.0; }
        };

        Basket basket = new Basket(grid, stubStrategy, alwaysSmall);

        Cell foodCell = grid.getTile(5, 10);
        basket.addSpecificFood(foodCell, new Apple(false));

        basket.refillIfNeeded(1, snake);

        snake.move(Direction.U);

        Assertions.assertTrue(foodCell.containsFood());
        Assertions.assertFalse(grid.getTile(0, 0).containsFood());

        long count = grid.getTiles().values().stream().filter(Cell::containsFood).count();
        Assertions.assertEquals(1, count);
    }

    @Test
    public void crazyBasketDoesNotMove_WhenRandomPrevents() throws Exception {
        Grid grid = new Grid();
        Snake snake = new Anaconda((f, c) -> {}, grid);

        FoodSpawnStrategy stubStrategy = (cell, g, s) -> g.getTile(0, 0);

        Random alwaysLarge = new Random() {
            @Override public double nextDouble() { return 0.99; }
        };

        Basket basket = new Basket(grid, stubStrategy, alwaysLarge);

        Cell foodCell = grid.getTile(5, 3);
        basket.addSpecificFood(foodCell, new Apple(false));

        basket.refillIfNeeded(1, snake);

        snake.move(Direction.U);
    
        Assertions.assertTrue(foodCell.containsFood());
        Assertions.assertFalse(grid.getTile(0, 0).containsFood());

        long count = grid.getTiles().values().stream().filter(Cell::containsFood).count();
        Assertions.assertEquals(1, count);
    }

}
