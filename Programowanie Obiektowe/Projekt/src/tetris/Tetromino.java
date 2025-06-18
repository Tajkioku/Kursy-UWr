package tetris;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

// Klasa reprezentująca wspólną logikę dla wszystkich Tetromino.
// Każda figura dziedziczy po tej klasie i dostarcza własną tablicę współrzędnych (shapeCoords) oraz kolor.

public abstract class Tetromino {
    protected int[][] shapeCoords; // współrzędne bloków
    protected Color color;         // kolor figury
    protected int pivotX, pivotY;  // pozycja pivotu (punkt obrotu)

    protected Tetromino(int startX, int startY, int[][] shapeCoords, Color color) {
        this.pivotX = startX;
        this.pivotY = startY;
        // Skopiuj tablicę, aby nie modyfikować oryginału
        this.shapeCoords = new int[4][2];
        for (int i = 0; i < 4; i++) {
            this.shapeCoords[i][0] = shapeCoords[i][0];
            this.shapeCoords[i][1] = shapeCoords[i][1];
        }
        this.color = color;
    }

    // Zwraca listę współrzędnych (x, y) dla czterech bloków figury. 
    public List<int[]> getCells() {
        List<int[]> cells = new ArrayList<>();
        for (int[] rel : shapeCoords) {
            cells.add(new int[]{pivotX + rel[0], pivotY + rel[1]});
        }
        return cells;
    }

    public Color getColor() {
        return color;
    }

    // Przesunięcie wektorowe pivotu o (dx, dy).
    public void move(int dx, int dy) {
        pivotX += dx;
        pivotY += dy;
    }

    // Obrót klocka wokół pivotu: (x, y) -> (-y, x).
    public void rotate() {
        for (int i = 0; i < shapeCoords.length; i++) {
            int x = shapeCoords[i][0];
            int y = shapeCoords[i][1];
            shapeCoords[i][0] = -y;
            shapeCoords[i][1] = x;
        }
    }

    // Obrót w przeciwną stronę: (x, y) -> (y, -x).
    public void rotateBack() {
        for (int i = 0; i < shapeCoords.length; i++) {
            int x = shapeCoords[i][0];
            int y = shapeCoords[i][1];
            shapeCoords[i][0] = y;
            shapeCoords[i][1] = -x;
        }
    }
}