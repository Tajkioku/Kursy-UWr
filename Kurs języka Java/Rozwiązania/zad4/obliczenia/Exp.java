package obliczenia;

public class Exp extends Wyrazenie {
    private final Wyrazenie argument;

    public Exp(Wyrazenie argument) {
        this.argument = argument;
    }

    @Override
    public double oblicz() {
        return Math.exp(argument.oblicz());
    }

    @Override
    public String toString() {
        return "exp(" + argument + ")";
    }
}
