package obliczenia;

public class Dzielenie extends Wyrazenie {
    private final Wyrazenie lewy;
    private final Wyrazenie prawy;

    public Dzielenie(Wyrazenie lewy, Wyrazenie prawy) {
        if (prawy == null) {
            throw new IllegalArgumentException("Prawy strona nie może być null.");
        }
        this.lewy = lewy;
        this.prawy = prawy;
    }

    @Override
    public double oblicz() {
        double wartoscPrawy = prawy.oblicz();
        if (wartoscPrawy == 0) {
            throw new ArithmeticException("Dzielenie przez zero.");
        }
        return lewy.oblicz() / wartoscPrawy;
    }

    @Override
    public String toString() {
        return "(" + lewy + " / " + prawy + ")";
    }
}
