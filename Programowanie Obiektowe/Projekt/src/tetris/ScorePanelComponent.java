package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

// Wyświetlanie Score i Level
public class ScorePanelComponent extends GameWindowComponent {
    private static final long serialVersionUID = 1L;

    private int score = 0;
    private int level = 1;

    public ScorePanelComponent() {
        setPreferredSize(new Dimension(BoardComponent.WIDTH * BoardComponent.BLOCK_SIZE, 40));
        setBackground(Color.DARK_GRAY);
    }

    public void setScore(int s) {
        this.score = s;
    }

    public void setLevel(int lvl) {
        this.level = lvl;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // tło
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        // tekst: Score, Level
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Level: " + level, 150, 20);
    }

    @Override
    public void refresh() {
        repaint();
    }
}