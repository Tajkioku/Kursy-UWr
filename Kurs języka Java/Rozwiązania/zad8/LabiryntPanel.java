import javax.swing.*;
import java.awt.*;

public class LabiryntPanel extends JPanel {
    private Labirynt labirynt;
    private Podroznik podroznik;
    private Ustawienia ustawienia;
    private ImageIcon podroznikIcon;
    private boolean graAktywna;

    public LabiryntPanel(Labirynt labirynt, Podroznik podroznik, Ustawienia ustawienia) {
        this.labirynt = labirynt;
        this.podroznik = podroznik;
        this.ustawienia = ustawienia;
        podroznikIcon = new ImageIcon("podroznik.jpg");
        graAktywna = false;
        setPreferredSize(new Dimension(600, 600));
    }

    public void aktualizujLabirynt(Labirynt labirynt, Podroznik podroznik) {
        this.labirynt = labirynt;
        this.podroznik = podroznik;
        repaint();
    }

    public void aktualizujUstawienia(Ustawienia ustawienia) {
        this.ustawienia = ustawienia;
    }

    public void setGraAktywna(boolean graAktywna) {
        this.graAktywna = graAktywna;
    }

    public boolean isGraAktywna() {
        return graAktywna;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        rysujLabirynt(g);
        rysujPodroznika(g);
    }

    private void rysujLabirynt(Graphics g) {
        int szer = labirynt.getSzerokosc();
        int wys = labirynt.getWysokosc();

        int szerKomnaty = getWidth() / szer;
        int wysKomnaty = getHeight() / wys;

        g.setColor(ustawienia.getKolorTla());
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(ustawienia.getKolorScian());

        for (int x = 0; x < szer; x++) {
            for (int y = 0; y < wys; y++) {
                Komnata komnata = labirynt.getKomnata(x, y);
                int xPos = x * szerKomnaty;
                int yPos = y * wysKomnaty;

                if (!komnata.czyPrzejscie(Kierunek.GORA)) {
                    g.drawLine(xPos, yPos, xPos + szerKomnaty, yPos);
                } else if (komnata.czyZamurowane(Kierunek.GORA)) {
                    g.setColor(Color.RED);
                    g.drawLine(xPos, yPos, xPos + szerKomnaty, yPos);
                    g.setColor(ustawienia.getKolorScian());
                }

                if (!komnata.czyPrzejscie(Kierunek.LEWO)) {
                    g.drawLine(xPos, yPos, xPos, yPos + wysKomnaty);
                } else if (komnata.czyZamurowane(Kierunek.LEWO)) {
                    g.setColor(Color.RED);
                    g.drawLine(xPos, yPos, xPos, yPos + wysKomnaty);
                    g.setColor(ustawienia.getKolorScian());
                }

                if (x == szer - 1 && !komnata.czyPrzejscie(Kierunek.PRAWO)) {
                    g.drawLine(xPos + szerKomnaty, yPos, xPos + szerKomnaty, yPos + wysKomnaty);
                } else if (komnata.czyZamurowane(Kierunek.PRAWO)) {
                    g.setColor(Color.RED);
                    g.drawLine(xPos + szerKomnaty, yPos, xPos + szerKomnaty, yPos + wysKomnaty);
                    g.setColor(ustawienia.getKolorScian());
                }

                if (y == wys - 1 && !komnata.czyPrzejscie(Kierunek.DOL)) {
                    g.drawLine(xPos, yPos + wysKomnaty, xPos + szerKomnaty, yPos + wysKomnaty);
                } else if (komnata.czyZamurowane(Kierunek.DOL)) {
                    g.setColor(Color.RED);
                    g.drawLine(xPos, yPos + wysKomnaty, xPos + szerKomnaty, yPos + wysKomnaty);
                    g.setColor(ustawienia.getKolorScian());
                }

                if (ustawienia.isOznaczeniePol()) {
                    g.setColor(Color.GRAY);
                    String label = (char) ('A' + y) + "" + (x + 1);
                    g.drawString(label, xPos + 5, yPos + 15);
                    g.setColor(ustawienia.getKolorScian());
                }
            }
        }
    }

    private void rysujPodroznika(Graphics g) {
        int szer = labirynt.getSzerokosc();
        int wys = labirynt.getWysokosc();

        int szerKomnaty = getWidth() / szer;
        int wysKomnaty = getHeight() / wys;

        int x = podroznik.getX();
        int y = podroznik.getY();

        int xPos = x * szerKomnaty;
        int yPos = y * wysKomnaty;

        Image img = podroznikIcon.getImage();
        g.drawImage(img, xPos + szerKomnaty / 4, yPos + wysKomnaty / 4, szerKomnaty / 2, wysKomnaty / 2, this);
    }
}
