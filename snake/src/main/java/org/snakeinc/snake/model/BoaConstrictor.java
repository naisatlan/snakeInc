package org.snakeinc.snake.model;
import java.awt.Color;
import org.snakeinc.snake.exception.DiedOfMalnutrition;

public final class BoaConstrictor extends Snake {
    private Color color;

    public BoaConstrictor(FoodEatenListener listener, Grid grid) {
        super(listener, grid);
        this.color = Color.BLUE;
    }
    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void eat(Food food, Cell cell) throws DiedOfMalnutrition {
        super.eat(food, cell);
        body.addFirst(cell);
        cell.addSnake(this);

        switch (food) {
            case Brocoli b -> {
                body.getLast().removeSnake();
                body.removeLast();
            }
            case Apple a -> {
                int sizeBeforeMove = body.size() - 1;
                if (sizeBeforeMove <= 1) {
                    throw new DiedOfMalnutrition();
                }

                for (int i = 0; i < 2; i++) {
                    Cell tail = body.getLast();
                    tail.removeSnake();
                    body.removeLast();
                }
            }
        }

        onFoodEatenListener.onFoodEaten(food, cell);
    }

    
}