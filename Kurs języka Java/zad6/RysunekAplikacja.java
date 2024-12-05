import java.awt.*;
import java.awt.event.*;

public class RysunekAplikacja extends Frame implements KeyListener, ItemListener {
    private Powierzchnia powierzchnia;
    private CheckboxGroup colorGroup;
    private Panel colorPanel;

    public RysunekAplikacja() {
        super("Rysunek AWT");

        setLayout(new BorderLayout());

        // Utworzenie powierzchniu do rysowania
        powierzchnia = new Powierzchnia();
        add(powierzchnia, BorderLayout.CENTER);

        // Utworzenie panelu wyboru koloru
        colorPanel = new Panel();
        colorPanel.setLayout(new GridLayout(0, 1));

        // Utworzenie przycisków dla kolorów
        colorGroup = new CheckboxGroup();
        Checkbox redCheckbox = new Checkbox("Czerwony", colorGroup, false);
        Checkbox greenCheckbox = new Checkbox("Zielony", colorGroup, false);
        Checkbox blueCheckbox = new Checkbox("Niebieski", colorGroup, false);
        Checkbox blackCheckbox = new Checkbox("Czarny", colorGroup, true);

        // Dodanie nasłuchiwaczy dla przycisków kolorów
        redCheckbox.addItemListener(this);
        greenCheckbox.addItemListener(this);
        blueCheckbox.addItemListener(this);
        blackCheckbox.addItemListener(this);

        // Dodanie przycisków do panelu
        colorPanel.add(redCheckbox);
        colorPanel.add(greenCheckbox);
        colorPanel.add(blueCheckbox);
        colorPanel.add(blackCheckbox);

        add(colorPanel, BorderLayout.EAST);

        // Dodanie nasłuchiwacza zdarzeń klawiatury
        addKeyListener(this);
        powierzchnia.addKeyListener(this);

        // Ustawienie wymiarow okna
        setSize(800, 600);
        setVisible(true);

        // Dodanie nasłuchiwacza zamknięcia okna
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    // Metoda obsługująca zmianę wybranego koloru
    public void itemStateChanged(ItemEvent e) {
        String colorName = ((Checkbox) e.getItemSelectable()).getLabel();
        // Ustawienie aktualnego koloru na podstawie wybranego przycisku
        switch (colorName) {
            case "Czerwony":
                powierzchnia.setCurrentColor(Color.RED);
                break;
            case "Zielony":
                powierzchnia.setCurrentColor(Color.GREEN);
                break;
            case "Niebieski":
                powierzchnia.setCurrentColor(Color.BLUE);
                break;
            case "Czarny":
                powierzchnia.setCurrentColor(Color.BLACK);
                break;
        }
    }

    // Metoda obsługująca naciśnięcia klawiszy
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // Usuwanie kresek na podstawie naciśniętego klawisza
        switch (keyCode) {
            case KeyEvent.VK_BACK_SPACE:
                powierzchnia.removeAllKreski();
                break;
            case KeyEvent.VK_F:
                powierzchnia.removeFirstKreska();
                break;
            case KeyEvent.VK_B:
            case KeyEvent.VK_L:
                powierzchnia.removeLastKreska();
                break;
        }
    }

    // Metody interfejsu KeyListener, które nie są używane
    public void keyReleased(KeyEvent e) { }
    public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        new RysunekAplikacja();
    }
}
