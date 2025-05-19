package ui;

import javax.swing.*;
import model.Ksiazka;
import java.awt.*;

public class KsiazkaPanel extends JPanel {
    private JTextField tytulField = new JTextField(20);
    private JTextField rokField   = new JTextField(5);

    public KsiazkaPanel() {
        setLayout(new GridLayout(2,2));
        add(new JLabel("Tytuł:"));
        add(tytulField);
        add(new JLabel("Rok wydania:"));
        add(rokField);
    }

    public void loadFrom(Ksiazka k) {
        tytulField.setText(k.tytul);
        rokField.setText(Integer.toString(k.rokWydania));
    }

    public void saveTo(Ksiazka k) {
        k.tytul = tytulField.getText();
        k.rokWydania = Integer.parseInt(rokField.getText());
    }
}
