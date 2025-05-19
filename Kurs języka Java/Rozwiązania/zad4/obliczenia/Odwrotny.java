package obliczenia;

public class Odwrotny extends Wyrazenie {
    private final Wyrazenie argument;

    public Odwrotny(Wyrazenie argument) {
        if (argument == null) {
            throw new IllegalArgumentException("Argument nie może być null.");
        }
        this.argument = argument;
    }

    @Override
    public double oblicz() {
        double wartosc = argument.oblicz();
        if (wartosc == 0) {
            throw new ArithmeticException("Dzielenie przez zero.");
        }
        return 1.0 / wartosc;
    }

    @Override
    public String toString() {
        return "(1/" + argument + ")";
    }
}
