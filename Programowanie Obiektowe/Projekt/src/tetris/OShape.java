package tetris;

import java.awt.Color;

public class OShape extends Tetromino {
    private static final int[][] O_COORDS = {
        {0, 0}, {1, 0}, {0, 1}, {1, 1}
    };
    private static final Color O_COLOR = Color.YELLOW;

    public OShape(int startX, int startY) {
        super(startX, startY, O_COORDS, O_COLOR);
    }

    @Override
    public void rotate() {
        // O-shape się nie obraca (jest kwadratem)
    }

    @Override
    public void rotateBack() {
        // O-shape się nie obraca (jest kwadratem)
    }
}