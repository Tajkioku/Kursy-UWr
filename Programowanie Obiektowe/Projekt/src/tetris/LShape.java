package tetris;

import java.awt.Color;

public class LShape extends Tetromino {
    private static final int[][] L_COORDS = {
        {1, -1}, {0, -1}, {0, 0}, {0, 1}
    };
    private static final Color L_COLOR = Color.ORANGE;

    public LShape(int startX, int startY) {
        super(startX, startY, L_COORDS, L_COLOR);
    }
}