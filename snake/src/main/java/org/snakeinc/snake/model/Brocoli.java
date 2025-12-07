package org.snakeinc.snake.model;
import java.awt.Color;

public final class Brocoli implements Food {
    protected final boolean steamed;
    private Color color;

    public Brocoli(boolean steamed) {
        this.steamed = steamed;
        this.color = steamed ? Color.YELLOW : Color.GREEN;
    }

    @Override
    public Color getColor() {
        return this.color;
    }
    
}
