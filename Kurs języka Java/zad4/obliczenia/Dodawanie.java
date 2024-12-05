package obliczenia;

public class Dodawanie extends Wyrazenie {
    private final Wyrazenie lewy;
    private final Wyrazenie prawy;

    public Dodawanie(Wyrazenie lewy, Wyrazenie prawy) {
        this.lewy = lewy;
        this.prawy = prawy;
    }

    @Override
    public double oblicz() {
        return lewy.oblicz() + prawy.oblicz();
    }

    @Override
    public String toString() {
        return "(" + lewy + " + " + prawy + ")";
    }
}
