package org.snakeinc.snake.model;

import lombok.Getter;

@Getter
public class Game {

    private final Grid grid;
    private final Basket basket;

    private static Game instance;

    public static Game getCurrentGame() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    private Game() {
        grid = new Grid();
        basket = new Basket();
    }


}
