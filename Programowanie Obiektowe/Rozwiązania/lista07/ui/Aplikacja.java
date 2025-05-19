package ui;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;

public class Aplikacja {
    private static final String FILENAME = "dane.ser";

    public static void main(String[] args) {
        if (args.length != 1 ||
            !(args[0].equals("Ksiazka") || args[0].equals("Pisarz") || args[0].equals("Wydawnictwo"))) {
            System.err.println("Użycie: java ui.Aplikacja Ksiazka|Pisarz|Wydawnictwo");
            System.exit(1);
        }
        String klasa = args[0];

        // 1. Wczytanie danych
        DataStore ds = loadData();
        if (ds == null) {
            JOptionPane.showMessageDialog(null, "Brak danych, uruchom wersję konsolową najpierw.");
            System.exit(1);
        }

        // 2. Przygotowanie komponentów
        JFrame frame = new JFrame("Edycja " + klasa);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JComboBox<Object> combo;
        JPanel editorPanel;
        JButton btnSave = new JButton("Zapisz");

        switch (klasa) {
            case "Ksiazka":
                combo = new JComboBox<>(ds.ksiazki.toArray());
                editorPanel = new KsiazkaPanel();
                combo.addActionListener(e -> ((KsiazkaPanel)editorPanel)
                        .loadFrom((Ksiazka)combo.getSelectedItem()));
                btnSave.addActionListener(e -> {
                    ((KsiazkaPanel)editorPanel).saveTo((Ksiazka)combo.getSelectedItem());
                    saveData(ds);
                });
                break;
            case "Pisarz":
                combo = new JComboBox<>(ds.pisarze.toArray());
                editorPanel = new PisarzPanel();
                combo.addActionListener(e -> ((PisarzPanel)editorPanel)
                        .loadFrom((Pisarz)combo.getSelectedItem()));
                btnSave.addActionListener(e -> {
                    ((PisarzPanel)editorPanel).saveTo((Pisarz)combo.getSelectedItem());
                    saveData(ds);
                });
                break;
            default: // Wydawnictwo
                combo = new JComboBox<>(ds.wydawnictwa.toArray());
                editorPanel = new WydawnictwoPanel();
                combo.addActionListener(e -> ((WydawnictwoPanel)editorPanel)
                        .loadFrom((Wydawnictwo)combo.getSelectedItem()));
                btnSave.addActionListener(e -> {
                    ((WydawnictwoPanel)editorPanel).saveTo((Wydawnictwo)combo.getSelectedItem());
                    saveData(ds);
                });
        }

        // 3. Układ okna
        frame.add(combo, BorderLayout.NORTH);
        frame.add(editorPanel, BorderLayout.CENTER);
        frame.add(btnSave, BorderLayout.SOUTH);

        // 4. Inicjalne załadowanie pierwszego obiektu
        if (combo.getItemCount() > 0) {
            combo.setSelectedIndex(0);
            combo.getActionListeners()[0].actionPerformed(null);
        }

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static DataStore loadData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILENAME))) {
            return (DataStore) in.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    private static void saveData(DataStore ds) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            out.writeObject(ds);
            JOptionPane.showMessageDialog(null, "Zapisano dane.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Błąd zapisu: " + e.getMessage());
        }
    }
}
