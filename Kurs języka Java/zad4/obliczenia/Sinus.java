package obliczenia;

public class Sinus extends Wyrazenie {
    private final Wyrazenie argument;

    public Sinus(Wyrazenie argument) {
        this.argument = argument;
    }

    @Override
    public double oblicz() {
        return Math.sin(argument.oblicz());
    }

    @Override
    public String toString() {
        return "sin(" + argument + ")";
    }
}
