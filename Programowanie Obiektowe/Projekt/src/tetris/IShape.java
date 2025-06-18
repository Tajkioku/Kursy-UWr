package tetris;

import java.awt.Color;

public class IShape extends Tetromino {
    private static final int[][] I_COORDS = {
        {0, -1}, {0, 0}, {0, 1}, {0, 2}
    };
    private static final Color I_COLOR = Color.CYAN;

    public IShape(int startX, int startY) {
        super(startX, startY, I_COORDS, I_COLOR);
    }
}