import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Duch {
    private Labirynt labirynt;
    private Timer timer;
    private Random rand;
    private LabiryntGra gra;

    public Duch(Labirynt labirynt, LabiryntGra gra) {
        this.labirynt = labirynt;
        this.gra = gra;
        rand = new Random();
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                zamurowaniePrzejscia();
            }
        });
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    private void zamurowaniePrzejscia() {
        int szer = labirynt.getSzerokosc();
        int wys = labirynt.getWysokosc();
        int x = rand.nextInt(szer);
        int y = rand.nextInt(wys);
        Komnata komnata = labirynt.getKomnata(x, y);
        Kierunek[] kierunki = Kierunek.values();
        Kierunek kierunek = kierunki[rand.nextInt(kierunki.length)];

        if (komnata.czyPrzejscie(kierunek)) {
            komnata.zamurowaniePrzejscia(kierunek);
            // Zamurowanie przejścia w sąsiedniej komnacie
            Komnata sasiednia = labirynt.getSasiedniaKomnata(komnata, kierunek);
            if (sasiednia != null) {
                sasiednia.zamurowaniePrzejscia(odwrotnyKierunek(kierunek));
            }
            gra.repaint();
        }
    }

    private Kierunek odwrotnyKierunek(Kierunek kierunek) {
        switch (kierunek) {
            case GORA:
                return Kierunek.DOL;
            case DOL:
                return Kierunek.GORA;
            case LEWO:
                return Kierunek.PRAWO;
            case PRAWO:
                return Kierunek.LEWO;
            default:
                return null;
        }
    }
}
