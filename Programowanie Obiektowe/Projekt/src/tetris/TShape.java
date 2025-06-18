package tetris;

import java.awt.Color;

public class TShape extends Tetromino {
    private static final int[][] T_COORDS = {
        {0, -1}, {-1, 0}, {0, 0}, {1, 0}
    };
    private static final Color T_COLOR = Color.MAGENTA;

    public TShape(int startX, int startY) {
        super(startX, startY, T_COORDS, T_COLOR);
    }
}