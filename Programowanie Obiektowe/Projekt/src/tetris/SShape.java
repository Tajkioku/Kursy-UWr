package tetris;

import java.awt.Color;

public class SShape extends Tetromino {
    private static final int[][] S_COORDS = {
        {0, 0}, {1, 0}, {-1, 1}, {0, 1}
    };
    private static final Color S_COLOR = Color.GREEN;

    public SShape(int startX, int startY) {
        super(startX, startY, S_COORDS, S_COLOR);
    }
}