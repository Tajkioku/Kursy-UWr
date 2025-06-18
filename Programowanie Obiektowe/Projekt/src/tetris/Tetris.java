package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Główna klasa uruchamiająca okno gry i składająca wszystkie komponenty w layout.
// KeyListener jest przypięty do BoardComponent.
public class Tetris {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScorePanelComponent scorePanel = new ScorePanelComponent();
            NextTetrominoComponent nextPanel = new NextTetrominoComponent();

            BoardComponent boardPanel = new BoardComponent(scorePanel, nextPanel);

            PauseMenuComponent pauseMenu = new PauseMenuComponent();

            // Placeholder
            HighScoreComponent highScorePanel = new HighScoreComponent();

            // Składamy wszystko w JFrame
            JFrame frame = new JFrame("Tetris");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setLayout(new BorderLayout());

            // NORTH – pasek wyników
            frame.add(scorePanel, BorderLayout.NORTH);

            // CENTER – warstwowy panel zawierający planszę i pauzę
            JLayeredPane layered = new JLayeredPane();
            layered.setPreferredSize(new Dimension(BoardComponent.WIDTH * BoardComponent.BLOCK_SIZE,
                                                   BoardComponent.HEIGHT * BoardComponent.BLOCK_SIZE));
            boardPanel.setBounds(0, 0, BoardComponent.WIDTH * BoardComponent.BLOCK_SIZE,
                                      BoardComponent.HEIGHT * BoardComponent.BLOCK_SIZE);
            pauseMenu.setBounds(0, 0, BoardComponent.WIDTH * BoardComponent.BLOCK_SIZE,
                                      BoardComponent.HEIGHT * BoardComponent.BLOCK_SIZE);
            layered.add(boardPanel, Integer.valueOf(0));
            layered.add(pauseMenu, Integer.valueOf(1));
            frame.add(layered, BorderLayout.CENTER);

            // EAST – panel nadchodzącego klocka
            frame.add(nextPanel, BorderLayout.EAST);

            // SOUTH – panel High Scores (placeholder)
            frame.add(highScorePanel, BorderLayout.SOUTH);

            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);

            // Obsługa klawiatury
            boardPanel.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int code = e.getKeyCode();
                    // pauza / wznowienie
                    if (code == KeyEvent.VK_P) {
                        if (!boardPanel.isGameOver()) {
                            pauseMenu.setVisiblePause(!pauseMenu.visible);
                            if (pauseMenu.visible) {
                                boardPanel.timer.stop();
                            } else {
                                boardPanel.timer.start();
                            }
                        }
                    } else {
                        // Przekazujemy inne klawisze do BoardComponent, jeśli nie pauza
                        if (!pauseMenu.visible && !boardPanel.isGameOver()) {
                            boardPanel.handleKey(code);
                        }
                    }
                }
            });

            frame.setVisible(true);

            // Po wyświetleniu okna wymuszamy fokus na BoardComponent:
            boardPanel.requestFocusInWindow();
        });
    }
}