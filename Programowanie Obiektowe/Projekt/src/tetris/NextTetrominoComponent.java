package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

//  Wyświetlanie miniaturki nadchodzącego Tetromino oraz statystyki:
// - liczba usuniętych linii
// - ile każdego kształtu (I, J, L, O, S, T, Z) zostało postawionych
public class NextTetrominoComponent extends GameWindowComponent {
    private static final long serialVersionUID = 1L;

    private Tetromino next;       // figura, którą rysujemy

    // Nowe statystyki:
    private int linesCleared = 0;
    private int countI = 0;
    private int countJ = 0;
    private int countL = 0;
    private int countO = 0;
    private int countS = 0;
    private int countT = 0;
    private int countZ = 0;

    public NextTetrominoComponent() {
        // Mniejsze rozmiary
        setPreferredSize(new Dimension(6 * BoardComponent.BLOCK_SIZE, 12 * BoardComponent.BLOCK_SIZE));
        setBackground(Color.BLACK);
    }

    // Wyświetlanie następnego tetromino
    public void setNext(Tetromino t) {
        this.next = t;
    }

    // Ustawiam statystyki
    public void setStats(int linesCleared, int countI, int countJ, int countL,
                         int countO, int countS, int countT, int countZ) {
        this.linesCleared = linesCleared;
        this.countI = countI;
        this.countJ = countJ;
        this.countL = countL;
        this.countO = countO;
        this.countS = countS;
        this.countT = countT;
        this.countZ = countZ;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // tło
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        // Napis "Next:" i miejsce na klocek
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        g.drawString("Next:", 10, 20);

        if (next != null) {
            int offsetX = (getWidth() - 4 * BoardComponent.BLOCK_SIZE) / 2;
            int offsetY = 30;
            g.translate(offsetX, offsetY);
            g.setColor(next.getColor());
            for (int[] cell : next.getCells()) {
                // Przeskalowanie z szerokości planszy do miniaturki
                int x = cell[0] - BoardComponent.WIDTH / 2 + 2;
                int y = cell[1];
                int drawX = x * BoardComponent.BLOCK_SIZE;
                int drawY = y * BoardComponent.BLOCK_SIZE;
                g.fillRect(drawX, drawY, BoardComponent.BLOCK_SIZE, BoardComponent.BLOCK_SIZE);
                g.setColor(Color.DARK_GRAY);
                g.drawRect(drawX, drawY, BoardComponent.BLOCK_SIZE, BoardComponent.BLOCK_SIZE);
                g.setColor(next.getColor());
            }
            g.translate(-offsetX, -offsetY);
        }

        // Poniżej ramki z klockiem wypisujemy statystyki
        int baseY = 4 * BoardComponent.BLOCK_SIZE + 40;
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        // Ununięte wiersze
        g.drawString("Lines: " + linesCleared, 10, baseY);

        // Ile I, J, L
        int line2Y = baseY + 20;
        g.drawString("I: " + countI + "   J: " + countJ + "   L: " + countL, 10, line2Y);

        // Ile O, S, T, Z
        int line3Y = line2Y + 20;
        g.drawString("O: " + countO + "   S: " + countS + "   T: " + countT + "   Z: " + countZ, 10, line3Y);
    }

    @Override
    public void refresh() {
        repaint();
    }
}