package tetris;

import java.awt.Color;

public class ZShape extends Tetromino {
    private static final int[][] Z_COORDS = {
        {-1, 0}, {0, 0}, {0, 1}, {1, 1}
    };
    private static final Color Z_COLOR = Color.RED;

    public ZShape(int startX, int startY) {
        super(startX, startY, Z_COORDS, Z_COLOR);
    }
}