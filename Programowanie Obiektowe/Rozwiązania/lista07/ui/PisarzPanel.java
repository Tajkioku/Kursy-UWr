package ui;

import javax.swing.*;
import model.Pisarz;
import java.awt.*;

public class PisarzPanel extends JPanel {
    private JTextField pseudonimField = new JTextField(20);
    private JTextField rokField       = new JTextField(5);

    public PisarzPanel() {
        setLayout(new GridLayout(2,2));
        add(new JLabel("Pseudonim:"));
        add(pseudonimField);
        add(new JLabel("Rok urodzenia:"));
        add(rokField);
    }

    public void loadFrom(Pisarz p) {
        pseudonimField.setText(p.pseudonim);
        rokField.setText(Integer.toString(p.rokUrodzenia));
    }

    public void saveTo(Pisarz p) {
        p.pseudonim = pseudonimField.getText();
        p.rokUrodzenia = Integer.parseInt(rokField.getText());
    }
}
