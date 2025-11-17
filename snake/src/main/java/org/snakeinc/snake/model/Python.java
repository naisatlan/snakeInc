package org.snakeinc.snake.model;
import awt.Color;

public class Python extends Snake {
    private Color color;

    public Python(AppleEatenListener listener, Grid grid) {
        super(listener, grid);
        this.color = Color.GREEN;
    }

    @Override
    public void eat(Apple apple, Cell cell) {
        super.eat(apple, cell);
        onAppleEatenListener.onAppleEaten(apple, cell);
    } 

}