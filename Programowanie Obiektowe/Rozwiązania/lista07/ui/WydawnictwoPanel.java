package ui;

import javax.swing.*;
import model.Wydawnictwo;
import java.awt.*;

public class WydawnictwoPanel extends JPanel {
    private JTextField symbolField    = new JTextField(2);
    private JTextField rokField       = new JTextField(5);

    public WydawnictwoPanel() {
        setLayout(new GridLayout(2,2));
        add(new JLabel("Symbol:"));
        add(symbolField);
        add(new JLabel("Rok założenia:"));
        add(rokField);
    }

    public void loadFrom(Wydawnictwo w) {
        symbolField.setText(Character.toString(w.symbol));
        rokField.setText(Integer.toString(w.rokZalozenia));
    }

    public void saveTo(Wydawnictwo w) {
        w.symbol = symbolField.getText().charAt(0);
        w.rokZalozenia = Integer.parseInt(rokField.getText());
    }
}
