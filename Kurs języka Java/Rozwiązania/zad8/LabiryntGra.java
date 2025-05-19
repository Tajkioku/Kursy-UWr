import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LabiryntGra extends JFrame {
    private Labirynt labirynt;
    private Podroznik podroznik;
    private Duch duch;
    private LabiryntPanel panel;
    private MenuBar menuBar;
    private JTextField statusField;
    private Ustawienia ustawienia;

    private long czasStart;
    private int liczbaRuchow;

    public LabiryntGra() {
        ustawienia = new Ustawienia();
        initUI();
    }

    private void initUI() {
        setTitle("Labirynt");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        labirynt = new Labirynt(ustawienia.getSzerokosc(), ustawienia.getWysokosc());
        labirynt.generujLabirynt();

        podroznik = new Podroznik(0, 0);
        duch = new Duch(labirynt, this);

        panel = new LabiryntPanel(labirynt, podroznik, ustawienia);
        add(panel, BorderLayout.CENTER);

        statusField = new JTextField("Rozpocznij grę, wybierając 'Start' z menu 'Gra'.");
        statusField.setEditable(false);
        add(statusField, BorderLayout.SOUTH);

        menuBar = new MenuBar(this);
        setJMenuBar(menuBar);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                obsluzRuchKlawiatury(e);
            }
        });

        ustawStanGry(false);
    }

    public void rozpocznijGre() {
        labirynt = new Labirynt(ustawienia.getSzerokosc(), ustawienia.getWysokosc());
        labirynt.generujLabirynt();
        podroznik = new Podroznik(0, 0);
        duch = new Duch(labirynt, this);
        liczbaRuchow = 0;
        czasStart = System.currentTimeMillis();

        panel.aktualizujLabirynt(labirynt, podroznik);

        ustawStanGry(true);
        duch.start();
        statusField.setText("Gra w toku");
    }

    public void zakonczGre(boolean sukces) {
        duch.stop();
        ustawStanGry(false);

        long czasTrwania = (System.currentTimeMillis() - czasStart) / 1000;

        if (sukces) {
            statusField.setText("Gratulacje! Wygrałeś! Czas: " + czasTrwania + " s, Ruchy: " + liczbaRuchow);
        } else {
            statusField.setText("Poddajesz się. Czas: " + czasTrwania + " s, Ruchy: " + liczbaRuchow);
        }
    }

    private void ustawStanGry(boolean wTrakcieGry) {
        menuBar.ustawStanGry(wTrakcieGry);
        panel.setGraAktywna(wTrakcieGry);
    }

    public void obsluzRuch(Kierunek kierunek) {
        if (!panel.isGraAktywna()) return;

        int x = podroznik.getX();
        int y = podroznik.getY();

        Komnata obecna = labirynt.getKomnata(x, y);

        if (obecna.czyPrzejscie(kierunek)) {
            switch (kierunek) {
                case GORA:
                    podroznik.przemiesc(0, -1);
                    break;
                case DOL:
                    podroznik.przemiesc(0, 1);
                    break;
                case LEWO:
                    podroznik.przemiesc(-1, 0);
                    break;
                case PRAWO:
                    podroznik.przemiesc(1, 0);
                    break;
            }
            liczbaRuchow++;
            panel.repaint();
            sprawdzKoniecGry();
        }
    }

    private void sprawdzKoniecGry() {
        int x = podroznik.getX();
        int y = podroznik.getY();

        if (x == labirynt.getSzerokosc() - 1 && y == labirynt.getWysokosc() - 1) {
            zakonczGre(true);
        }
    }

    private void obsluzRuchKlawiatury(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            obsluzRuch(Kierunek.GORA);
        } else if (key == KeyEvent.VK_DOWN) {
            obsluzRuch(Kierunek.DOL);
        } else if (key == KeyEvent.VK_LEFT) {
            obsluzRuch(Kierunek.LEWO);
        } else if (key == KeyEvent.VK_RIGHT) {
            obsluzRuch(Kierunek.PRAWO);
        }
    }

    public Ustawienia getUstawienia() {
        return ustawienia;
    }

    public JTextField getStatusField() {
        return statusField;
    }

    public void aktualizujUstawienia() {
        panel.aktualizujUstawienia(ustawienia);
        panel.repaint();
    }
}
