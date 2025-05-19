import java.util.EnumMap;

public class Komnata {
    private int x, y;
    private EnumMap<Kierunek, Boolean> sciany; // true - przejście istnieje, false - brak przejścia
    private boolean odwiedzona;
    private EnumMap<Kierunek, Boolean> zamurowane; // przejścia zamurowane przez ducha

    public Komnata(int x, int y) {
        this.x = x;
        this.y = y;
        odwiedzona = false;
        sciany = new EnumMap<>(Kierunek.class);
        zamurowane = new EnumMap<>(Kierunek.class);
        for (Kierunek kierunek : Kierunek.values()) {
            sciany.put(kierunek, false);
            zamurowane.put(kierunek, false);
        }
    }

    public void ustawPrzejscie(Kierunek kierunek, boolean istnieje) {
        sciany.put(kierunek, istnieje);
    }

    public boolean czyPrzejscie(Kierunek kierunek) {
        return sciany.get(kierunek) && !zamurowane.get(kierunek);
    }

    public void zamurowaniePrzejscia(Kierunek kierunek) {
        if (sciany.get(kierunek)) {
            zamurowane.put(kierunek, true);
        }
    }

    public boolean czyZamurowane(Kierunek kierunek) {
        return zamurowane.get(kierunek);
    }

    public void setOdwiedzona(boolean odwiedzona) {
        this.odwiedzona = odwiedzona;
    }

    public boolean czyOdwiedzona() {
        return odwiedzona;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
