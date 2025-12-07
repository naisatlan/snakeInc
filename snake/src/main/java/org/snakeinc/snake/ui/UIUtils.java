package org.snakeinc.snake.ui;

import java.awt.Graphics;

import org.snakeinc.snake.GameParams;
import org.snakeinc.snake.model.Cell;
import org.snakeinc.snake.model.Game;

public class UIUtils {

    public static void draw(Graphics g, Game game) {
        var grid = game.getGrid();
        for (int y = 0; y < GameParams.TILES_Y; y++) {
            for (int x = 0; x < GameParams.TILES_X; x++) {
                Cell cell = grid.getTile(x, y);
                if (cell.containsFood() || cell.containsASnake()) {
                    new CellUI(cell,
                            x * GamePanel.TILE_PIXEL_SIZE,
                            y * GamePanel.TILE_PIXEL_SIZE)
                            .draw(g);
                }
            }
        }

    }


}
