package tetris;

import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Rysowanie planszy Tetris, dziedziczy po GameWindowComponent
// Dziedziczy po GameWindowComponent.
public class BoardComponent extends GameWindowComponent {
    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 10;
    public static final int HEIGHT = 20;
    public static final int BLOCK_SIZE = 30;

    private Color[][] grid = new Color[WIDTH][HEIGHT];
    private Tetromino current;
    private Tetromino next;
    public Timer timer;       // public, zeby mozna było pauzować
    private boolean gameOver = false;

    private int score = 0;
    private int level = 1;
    private int linesClearedTotal = 0;

    // Liczniki, ile zostało postawione
    private int countI = 0;
    private int countJ = 0;
    private int countL = 0;
    private int countO = 0;
    private int countS = 0;
    private int countT = 0;
    private int countZ = 0;

    private final Random rnd = new Random();

    private ScorePanelComponent scorePanel;
    private NextTetrominoComponent nextPanel;

    public BoardComponent(ScorePanelComponent scorePanel, NextTetrominoComponent nextPanel) {
        setPreferredSize(new Dimension(WIDTH * BLOCK_SIZE, HEIGHT * BLOCK_SIZE));
        setBackground(Color.BLACK);

        this.scorePanel = scorePanel;
        this.nextPanel = nextPanel;

        spawnNewTetromino();
        next = createRandomTetromino();

        // Timer, co computeDelay() ms spada klocek
        timer = new Timer(computeDelay(), e -> {
            if (!step()) {
                timer.stop();
                gameOver = true;
            }
            // Odświezamy po kazdym kroku
            refresh();
            scorePanel.setScore(score);
            scorePanel.setLevel(level);
            scorePanel.refresh();
            // Przekazujemy statystyki
            nextPanel.setNext(next);
            nextPanel.setStats(linesClearedTotal, countI, countJ, countL, countO, countS, countT, countZ);
            nextPanel.refresh();
        });
        timer.start();

        setFocusable(true);
        // Po stworazeniu planszy nasłuchujemy klawiatury
        requestFocusInWindow();
    }

    // Losowanie tetromino
    private Tetromino createRandomTetromino() {
        int choice = rnd.nextInt(7);
        return switch (choice) {
            case 0 -> new IShape(WIDTH / 2, 0);
            case 1 -> new JShape(WIDTH / 2, 0);
            case 2 -> new LShape(WIDTH / 2, 0);
            case 3 -> new OShape(WIDTH / 2, 0);
            case 4 -> new SShape(WIDTH / 2, 0);
            case 5 -> new TShape(WIDTH / 2, 0);
            default -> new ZShape(WIDTH / 2, 0);
        };
    }

    // Opóźnienie ruchu
    private int computeDelay() {
        int base = 500;
        int dec = (level - 1) * 50;
        return Math.max(100, base - dec);
    }

    // Generuje pierwszy klocek i kazdy kolejny
    private void spawnNewTetromino() {
        if (next == null) {
            current = createRandomTetromino();
            next = createRandomTetromino();
        } else {
            current = next;
            current.pivotX = WIDTH / 2;
            current.pivotY = 0;
            next = createRandomTetromino();
        }
        // Ustawia pivot środka
        current.pivotX = WIDTH / 2;
        current.pivotY = 0;

        if (!canPlace(current, 0, 0)) {
            gameOver = true;
        }
    }

    // Pojedynczy krok
    public boolean step() {
        if (gameOver) return false;

        if (canPlace(current, 0, 1)) {
            current.move(0, 1);
        } else {
            // Przed przegraną zwieksza statystykę
            incrementCounter(current);

            lockCurrent();
            int lines = clearLines();
            if (lines > 0) {
                updateScore(lines);
                linesClearedTotal += lines;
                int newLevel = linesClearedTotal / 1 + 1;
                if (newLevel != level) {
                    level = newLevel;
                    timer.setDelay(computeDelay());
                }
            }
            spawnNewTetromino();
        }
        return !gameOver;
    }

    // Sprawdza kolizję
    private boolean canPlace(Tetromino tetro, int dx, int dy) {
        for (int[] cell : tetro.getCells()) {
            int x = cell[0] + dx;
            int y = cell[1] + dy;
            if (x < 0 || x >= WIDTH || y >= HEIGHT) return false;
            if (y >= 0 && grid[x][y] != null) return false;
        }
        return true;
    }

    // Postawienie klocka
    private void lockCurrent() {
        for (int[] cell : current.getCells()) {
            int x = cell[0], y = cell[1];
            if (y >= 0 && y < HEIGHT && x >= 0 && x < WIDTH) {
                grid[x][y] = current.getColor();
            }
        }
    }

    // Usuwanie linii
    private int clearLines() {
        List<Integer> fullRows = new ArrayList<>();
        for (int y = 0; y < HEIGHT; y++) {
            boolean full = true;
            for (int x = 0; x < WIDTH; x++) {
                if (grid[x][y] == null) {
                    full = false;
                    break;
                }
            }
            if (full) fullRows.add(y);
        }
        if (fullRows.isEmpty()) return 0;

        // Usuwamy wiersze od dołu do góry:
        for (int row : fullRows) {
            for (int x = 0; x < WIDTH; x++) grid[x][row] = null;
            for (int y = row - 1; y >= 0; y--) {
                for (int x = 0; x < WIDTH; x++) {
                    grid[x][y + 1] = grid[x][y];
                    grid[x][y] = null;
                }
            }
        }
        return fullRows.size();
    }

    // Dodawanie punktów
    private void updateScore(int lines) {
        switch (lines) {
            case 1 -> score += 100;
            case 2 -> score += 300;
            case 3 -> score += 500;
            case 4 -> score += 800;
        }
    }

    // Zwiększanie statystyk
    private void incrementCounter(Tetromino t) {
        if (t instanceof IShape)      countI++;
        else if (t instanceof JShape) countJ++;
        else if (t instanceof LShape) countL++;
        else if (t instanceof OShape) countO++;
        else if (t instanceof SShape) countS++;
        else if (t instanceof TShape) countT++;
        else if (t instanceof ZShape) countZ++;
    }

    // Nasłuchiwanie klawiatury
    public void handleKey(int keyCode) {
        if (gameOver) return;
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> {
                if (canPlace(current, -1, 0)) current.move(-1, 0);
            }
            case KeyEvent.VK_RIGHT -> {
                if (canPlace(current, 1, 0)) current.move(1, 0);
            }
            case KeyEvent.VK_DOWN -> {
                // +1 punkt
                if (canPlace(current, 0, 1)) {
                    current.move(0, 1);
                    score += 1;
                }
            }
            case KeyEvent.VK_UP -> {
                current.rotate();
                if (!canPlace(current, 0, 0)) {
                    current.rotateBack();
                }
            }
        }
        // Odświezamy panele
        scorePanel.setScore(score);
        scorePanel.setLevel(level);
        scorePanel.refresh();

        nextPanel.setNext(next);
        nextPanel.setStats(linesClearedTotal, countI, countJ, countL, countO, countS, countT, countZ);
        nextPanel.refresh();

        refresh();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Rysuje siatkę:
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Color c = grid[x][y];
                if (c != null) {
                    g.setColor(c);
                    g.fillRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    g.setColor(Color.DARK_GRAY);
                    g.drawRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                } else {
                    g.setColor(Color.GRAY);
                    g.drawRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }

        // Rysuje bieżące Tetromino:
        if (!gameOver && current != null) {
            g.setColor(current.getColor());
            for (int[] cell : current.getCells()) {
                int x = cell[0], y = cell[1];
                if (y >= 0) {
                    g.fillRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    g.setColor(Color.DARK_GRAY);
                    g.drawRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    g.setColor(current.getColor());
                }
            }
        }

        // Game Over wyświetla komunikat
        if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("SansSerif", Font.BOLD, 24));
            g.drawString("GAME OVER", WIDTH * BLOCK_SIZE / 2 - 70, HEIGHT * BLOCK_SIZE / 2);
        }
    }

    @Override
    public void refresh() {
        repaint();
    }

    public int getScore() {
        return score;
    }
    public int getLevel() {
        return level;
    }
    public boolean isGameOver() {
        return gameOver;
    }
}