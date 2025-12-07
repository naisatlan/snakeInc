package org.snakeinc.snake.model;
import java.awt.Color;

public sealed interface Food permits Apple, Brocoli {
    public abstract Color getColor();
}
