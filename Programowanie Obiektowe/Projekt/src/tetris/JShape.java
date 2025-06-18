package tetris;

import java.awt.Color;

public class JShape extends Tetromino {
    private static final int[][] J_COORDS = {
        {-1, -1}, {0, -1}, {0, 0}, {0, 1}
    };
    private static final Color J_COLOR = Color.BLUE;

    public JShape(int startX, int startY) {
        super(startX, startY, J_COORDS, J_COLOR);
    }
}