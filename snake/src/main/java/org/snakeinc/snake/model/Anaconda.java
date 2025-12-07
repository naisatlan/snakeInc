package org.snakeinc.snake.model;
import java.awt.Color;

import org.snakeinc.snake.exception.DiedOfMalnutrition;

public final class Anaconda extends Snake {
    private Color color;

    public Anaconda(FoodEatenListener listener, Grid grid) {
        super(listener, grid);
        this.color = Color.GRAY;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void eat(Food food, Cell cell) throws DiedOfMalnutrition {
        super.eat(food, cell);
        cell.addSnake(this);
        body.addFirst(cell);

        switch (food) {
            case Apple apple -> {
            }
            case Brocoli brocoli -> {
                int sizeBeforeMove = body.size() - 1;
                if (sizeBeforeMove <= 2) {
                    throw new DiedOfMalnutrition();
                }

                for (int i = 0; i < 3; i++) {
                    Cell tail = body.getLast();
                    tail.removeSnake();
                    body.removeLast();
                }
            }
        }

        onFoodEatenListener.onFoodEaten(food, cell);
    }

}