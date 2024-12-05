package obliczenia;

public class Przeciwny extends Wyrazenie {
    private final Wyrazenie argument;

    public Przeciwny(Wyrazenie argument) {
        this.argument = argument;
    }

    @Override
    public double oblicz() {
        return -argument.oblicz();
    }

    @Override
    public String toString() {
        return "(-" + argument + ")";
    }
}
