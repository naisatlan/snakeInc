package org.snakeinc.snake.model;

import java.awt.Color;
import java.util.LinkedList;
import org.snakeinc.snake.GameParams;
import org.snakeinc.snake.exception.DiedOfMalnutrition;
import org.snakeinc.snake.exception.OutOfPlayException;
import org.snakeinc.snake.exception.SelfCollisionException;

public abstract sealed class Snake permits Anaconda, Python, BoaConstrictor {

    protected final LinkedList<Cell> body;
    protected final FoodEatenListener onFoodEatenListener;
    protected final Grid grid;
    protected SnakeState state;

    public Snake(FoodEatenListener listener, Grid grid) {
        this.body = new LinkedList<>();
        this.onFoodEatenListener = listener;
        this.grid = grid;
        int hx = GameParams.SNAKE_DEFAULT_X;
        int hy = GameParams.SNAKE_DEFAULT_Y;
        Cell head = grid.getTile(hx, hy);
        head.addSnake(this);
        body.add(head);
        Cell seg1 = grid.getTile(hx, hy + 1);
        if (seg1 != null) {
            seg1.addSnake(this);
            body.add(seg1);
        }
        Cell seg2 = grid.getTile(hx, hy + 2);
        if (seg2 != null) {
            seg2.addSnake(this);
            body.add(seg2);
        }
        this.state = new HealthyState();
    }

    public void addObserver(SnakeObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public final LinkedList<SnakeObserver> observers = new LinkedList<>();

    public void removeObserver(SnakeObserver observer) {
        observers.remove(observer);
    }

    public int getSize() {
        return body.size();
    }

    public Cell getHead() {
        return body.getFirst();
    }

    public abstract Color getColor();

    public void eat(Food food, Cell cell) throws DiedOfMalnutrition {
        switch(food){
            case Apple apple -> {
                if (!apple.poisoned) {
                    Game.score += 2;
                } else {
                    if (state instanceof HealthyState) {
                        state = new PoisonedState();
                    } else if (state instanceof PoisonedState) {
                        state = new DamagedState();
                    }
                }
            }
            case Brocoli brocoli -> {
                if (!brocoli.steamed) {
                    Game.score += 1;
                }
                if (state instanceof PoisonedState) {
                    state = new HealthyState();
                }
            }
        }
    }

    public void move(Direction direction) throws OutOfPlayException, SelfCollisionException, DiedOfMalnutrition {
        Cell newHead;
        switch (direction) {
            case U -> newHead = state.moveUp(this);
            case D -> newHead = state.moveDown(this);
            case L -> newHead = state.moveLeft(this);
            case R -> newHead = state.moveRight(this);
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }
        if (newHead == null) {
            throw new OutOfPlayException();
        }
        if (newHead.containsASnake()) {
            throw new SelfCollisionException();
        }

        // Eat food :
        if (newHead.containsFood()) {
            this.eat(newHead.getFood(), newHead);
            return;
        }

        // The snake did not eat :
        newHead.addSnake(this);
        body.addFirst(newHead);

        body.getLast().removeSnake();
        body.removeLast();

        for (SnakeObserver o : observers) {
            o.onSnakeMoved(newHead.getX(), newHead.getY());
        }
    }

}
