import java.util.*;

public class Labirynt {
    private int szerokosc;
    private int wysokosc;
    private Komnata[][] komnaty;

    public Labirynt(int szerokosc, int wysokosc) {
        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
        komnaty = new Komnata[szerokosc][wysokosc];
        inicjalizujKomnaty();
    }

    private void inicjalizujKomnaty() {
        for (int x = 0; x < szerokosc; x++) {
            for (int y = 0; y < wysokosc; y++) {
                komnaty[x][y] = new Komnata(x, y);
            }
        }
    }

    public void generujLabirynt() {
        Stack<Komnata> stos = new Stack<>();
        Komnata startowa = komnaty[0][0];
        startowa.setOdwiedzona(true);
        stos.push(startowa);

        Random rand = new Random();

        while (!stos.isEmpty()) {
            Komnata obecna = stos.peek();
            List<Kierunek> nieodwiedzoneSasiednie = new ArrayList<>();

            for (Kierunek kierunek : Kierunek.values()) {
                Komnata sasiednia = getSasiedniaKomnata(obecna, kierunek);
                if (sasiednia != null && !sasiednia.czyOdwiedzona()) {
                    nieodwiedzoneSasiednie.add(kierunek);
                }
            }

            if (!nieodwiedzoneSasiednie.isEmpty()) {
                Kierunek wybranyKierunek = nieodwiedzoneSasiednie.get(rand.nextInt(nieodwiedzoneSasiednie.size()));
                Komnata sasiednia = getSasiedniaKomnata(obecna, wybranyKierunek);

                obecna.ustawPrzejscie(wybranyKierunek, true);
                sasiednia.ustawPrzejscie(odwrotnyKierunek(wybranyKierunek), true);

                sasiednia.setOdwiedzona(true);
                stos.push(sasiednia);
            } else {
                stos.pop();
            }
        }
    }

    public Komnata getSasiedniaKomnata(Komnata komnata, Kierunek kierunek) {
        int x = komnata.getX();
        int y = komnata.getY();

        switch (kierunek) {
            case GORA:
                y -= 1;
                break;
            case DOL:
                y += 1;
                break;
            case LEWO:
                x -= 1;
                break;
            case PRAWO:
                x += 1;
                break;
        }

        if (x >= 0 && x < szerokosc && y >= 0 && y < wysokosc) {
            return komnaty[x][y];
        } else {
            return null;
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

    public Komnata[][] getKomnaty() {
        return komnaty;
    }

    public Komnata getKomnata(int x, int y) {
        if (x >= 0 && x < szerokosc && y >= 0 && y < wysokosc) {
            return komnaty[x][y];
        }
        return null;
    }

    public int getSzerokosc() {
        return szerokosc;
    }

    public int getWysokosc() {
        return wysokosc;
    }
}
