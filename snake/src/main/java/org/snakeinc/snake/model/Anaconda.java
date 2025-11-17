package org.snakeinc.snake.model;
import awt.Color;

public class Anaconda extends Snake {
    private Color color;

    public Anaconda(AppleEatenListener listener, Grid grid) {
        super(listener, grid);
        this.color = Color.GREY;
    }

    @Override
    public void eat(Apple apple, Cell cell) {
        super.eat(apple, cell);
        body.addFirst(cell);
        cell.addSnake(this);
        onAppleEatenListener.onAppleEaten(apple, cell);
    } 
}