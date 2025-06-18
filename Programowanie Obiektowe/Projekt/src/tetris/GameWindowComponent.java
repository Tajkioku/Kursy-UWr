package tetris;

import javax.swing.JComponent;

// Abstrakcyjna klasa bazowa dla wszystkich elementów GUI.
// Rozszerza JComponent, dzięki czemu każda podklasa może sama się rysować.
public abstract class GameWindowComponent extends JComponent {
    private static final long serialVersionUID = 1L;

    public abstract void refresh();
}