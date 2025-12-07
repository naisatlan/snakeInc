package org.snakeinc.snake.ui;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import lombok.AllArgsConstructor;
import org.snakeinc.snake.model.Cell;

@AllArgsConstructor
public class CellUI {

    private Cell cell;
    private int upperPixelX;
    private int upperPixelY;

    public void drawRectangle(Graphics g) {
        g.fillRect(upperPixelX, upperPixelY, GamePanel.TILE_PIXEL_SIZE, GamePanel.TILE_PIXEL_SIZE);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(cell.getSnake().getColor().darker());
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(upperPixelX, upperPixelY, GamePanel.TILE_PIXEL_SIZE, GamePanel.TILE_PIXEL_SIZE);
    }

    public void drawOval(Graphics g) {
        g.fillOval(upperPixelX, upperPixelY, GamePanel.TILE_PIXEL_SIZE, GamePanel.TILE_PIXEL_SIZE);
    }

    public void draw(Graphics g) {

        if (cell.containsASnake()) {
            g.setColor(cell.getSnake().getColor());
            drawRectangle(g);
        }
        if (cell.containsFood()) {
            g.setColor(cell.getFood().getColor());
            drawOval(g);
        }

    }

}
