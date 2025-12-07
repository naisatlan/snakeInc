package org.snakeinc.snake.model;

import java.util.Random;

public class FoodFactory {

    public static Food createFoodInCell(Cell cell) {
        var random = new Random();
        int foodType = random.nextInt(0, 2);
        switch(foodType) {
            case 0 -> {
                Apple apple = new Apple(Math.random() < 0.1);
                cell.addFood(apple);
                return apple;
            }
            case 1 -> {
                Brocoli brocoli = new Brocoli(Math.random() < 0.3);
                cell.addFood(brocoli);
                return brocoli;
            }
            default -> throw new IllegalStateException("Unexpected value: " + foodType);
        }
    }

}
