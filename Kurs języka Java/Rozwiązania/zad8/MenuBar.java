import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class MenuBar extends JMenuBar {
    private LabiryntGra gra;

    private JMenuItem startItem;
    private JMenuItem rezygnacjaItem;
    private JMenuItem koniecItem;

    public MenuBar(LabiryntGra gra) {
        this.gra = gra;
        initMenu();
    }

    public void ustawStanGry(boolean wTrakcieGry) {
        startItem.setEnabled(!wTrakcieGry);
        rezygnacjaItem.setEnabled(wTrakcieGry);
    }

    private void initMenu() {
        JMenu menuGra = new JMenu("Gra");
        menuGra.setMnemonic(KeyEvent.VK_G);

        startItem = new JMenuItem("Start");
        startItem.setMnemonic(KeyEvent.VK_S);
        startItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        startItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gra.rozpocznijGre();
            }
        });

        rezygnacjaItem = new JMenuItem("Rezygnacja");
        rezygnacjaItem.setMnemonic(KeyEvent.VK_R);
        rezygnacjaItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        rezygnacjaItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gra.zakonczGre(false);
            }
        });

        koniecItem = new JMenuItem("Koniec");
        koniecItem.setMnemonic(KeyEvent.VK_K);
        koniecItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
        koniecItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuGra.add(startItem);
        menuGra.add(rezygnacjaItem);
        menuGra.addSeparator();
        menuGra.add(koniecItem);

        JMenu menuUstawienia = new JMenu("Ustawienia");
        menuUstawienia.setMnemonic(KeyEvent.VK_U);

        JCheckBoxMenuItem oznaczeniePolItem = new JCheckBoxMenuItem("Oznaczenie pól");
        oznaczeniePolItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean selected = oznaczeniePolItem.isSelected();
                gra.getUstawienia().setOznaczeniePol(selected);
                gra.aktualizujUstawienia();
            }
        });

        JMenu menuKolory = new JMenu("Kolory");

        JMenuItem kolorTlaItem = new JMenuItem("Tło");
        kolorTlaItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color nowyKolor = JColorChooser.showDialog(gra, "Wybierz kolor tła", gra.getUstawienia().getKolorTla());
                if (nowyKolor != null) {
                    gra.getUstawienia().setKolorTla(nowyKolor);
                    gra.aktualizujUstawienia();
                }
            }
        });

        JMenuItem kolorScianItem = new JMenuItem("Ściany");
        kolorScianItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color nowyKolor = JColorChooser.showDialog(gra, "Wybierz kolor ścian", gra.getUstawienia().getKolorScian());
                if (nowyKolor != null) {
                    gra.getUstawienia().setKolorScian(nowyKolor);
                    gra.aktualizujUstawienia();
                }
            }
        });

        menuKolory.add(kolorTlaItem);
        menuKolory.add(kolorScianItem);

        JMenu menuRozmiary = new JMenu("Rozmiary");

        JMenuItem szerokoscItem = new JMenuItem("Szerokość");
        szerokoscItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(gra, "Podaj szerokość (5-26):", gra.getUstawienia().getSzerokosc());
                try {
                    int szer = Integer.parseInt(input);
                    if (szer >= 5 && szer <= 26) {
                        gra.getUstawienia().setSzerokosc(szer);
                        gra.aktualizujUstawienia();
                    } else {
                        JOptionPane.showMessageDialog(gra, "Nieprawidłowa wartość!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(gra, "Nieprawidłowa wartość!");
                }
            }
        });

        JMenuItem wysokoscItem = new JMenuItem("Wysokość");
        wysokoscItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(gra, "Podaj wysokość (5-26):", gra.getUstawienia().getWysokosc());
                try {
                    int wys = Integer.parseInt(input);
                    if (wys >= 5 && wys <= 26) {
                        gra.getUstawienia().setWysokosc(wys);
                        gra.aktualizujUstawienia();
                    } else {
                        JOptionPane.showMessageDialog(gra, "Nieprawidłowa wartość!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(gra, "Nieprawidłowa wartość!");
                }
            }
        });

        menuRozmiary.add(szerokoscItem);
        menuRozmiary.add(wysokoscItem);

        menuUstawienia.add(oznaczeniePolItem);
        menuUstawienia.add(menuKolory);
        menuUstawienia.add(menuRozmiary);

        JMenu menuRuchy = new JMenu("Ruchy");
        menuRuchy.setMnemonic(KeyEvent.VK_R);

        JMenuItem wGoreItem = new JMenuItem("W górę");
        wGoreItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
        wGoreItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gra.obsluzRuch(Kierunek.GORA);
            }
        });

        JMenuItem wPrawoItem = new JMenuItem("W prawo");
        wPrawoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
        wPrawoItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gra.obsluzRuch(Kierunek.PRAWO);
            }
        });

        JMenuItem wDolItem = new JMenuItem("W dół");
        wDolItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
        wDolItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gra.obsluzRuch(Kierunek.DOL);
            }
        });

        JMenuItem wLewoItem = new JMenuItem("W lewo");
        wLewoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
        wLewoItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gra.obsluzRuch(Kierunek.LEWO);
            }
        });

        menuRuchy.add(wGoreItem);
        menuRuchy.add(wPrawoItem);
        menuRuchy.add(wDolItem);
        menuRuchy.add(wLewoItem);

        JMenu menuPomoc = new JMenu("Pomoc");
        menuPomoc.setMnemonic(KeyEvent.VK_P);

        JMenuItem oAplikacjiItem = new JMenuItem("O aplikacji");
        oAplikacjiItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(gra, "Celem gry jest doprowadzenie Podróżnika do wyjścia z labiryntu.\nUnikaj zamurowanych przez Ducha przejść!", "O aplikacji", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JMenuItem oAutorzeItem = new JMenuItem("O autorze");
        oAutorzeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(gra, "Autor: Kamil Wozniak", "O autorze", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        menuPomoc.add(oAplikacjiItem);
        menuPomoc.add(oAutorzeItem);

        add(menuGra);
        add(menuUstawienia);
        add(menuRuchy);
        add(Box.createHorizontalGlue()); // Przesunięcie menu Pomoc na prawą stronę
        add(menuPomoc);
    }
}
