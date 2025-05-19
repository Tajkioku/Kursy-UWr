package obliczenia;

public class Logarytm extends Wyrazenie {
    private final Wyrazenie podstawa;
    private final Wyrazenie argument;

    public Logarytm(Wyrazenie podstawa, Wyrazenie argument) {
        this.podstawa = podstawa;
        this.argument = argument;
    }

    @Override
    public double oblicz() {
        double p = podstawa.oblicz();
        double a = argument.oblicz();
        if (p <= 0 || p == 1 || a <= 0) {
            throw new IllegalArgumentException("Nieprawidłowe wartości do obliczenia logarytmu.");
        }
        return Math.log(a) / Math.log(p);
    }

    @Override
    public String toString() {
        return "log(" + podstawa + ", " + argument + ")";
    }
}
