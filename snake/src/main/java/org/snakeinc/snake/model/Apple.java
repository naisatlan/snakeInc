package org.snakeinc.snake.model;

import java.awt.Color;
import lombok.Getter;

@Getter
public final class Apple implements Food {
    private Color color;
    protected final boolean poisoned;

    public Apple(boolean poisoned) {
        this.poisoned = poisoned;
        this.color = poisoned ? new Color(128, 0, 128) : Color.RED;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

}
