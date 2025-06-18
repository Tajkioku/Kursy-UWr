package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

// Wstrzymanie gry po naciśnieciu P
public class PauseMenuComponent extends GameWindowComponent {
    private static final long serialVersionUID = 1L;

    public boolean visible = false; // public, by Tetris mógł sprawdzać stan

    public PauseMenuComponent() {
        setOpaque(false);
    }

    public void setVisiblePause(boolean vis) {
        this.visible = vis;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!visible) return;

        int w = getWidth(), h = getHeight();
        // półprzezroczyste tło
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, w, h);

        // napis PAUSE na środku
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 36));
        g.drawString("PAUSE", w / 2 - 70, h / 2);
    }

    @Override
    public void refresh() {
        repaint();
    }
}