package obliczenia;

public class Main {
    public static void main(String[] args) {
        // Ustawienie wartości zmiennej x na 1.618 (liczba złota)
        Zmienna.ustawZmienna("x", 1.618);

        // Wyrażenie: 7 + x * 5
        Wyrazenie wyrazenie1 = new Dodawanie(
            new Liczba(7.2),
            new Mnozenie(
                new Zmienna("x"),
                new Liczba(2.4)
            )
        );

        System.out.println("Wyrażenie 1: " + wyrazenie1);
        System.out.println("Wynik: " + wyrazenie1.oblicz());

        // Wyrażenie: 7 + 5 * 3 - 1
        Wyrazenie wyrazenie2 = new Odejmowanie(
            new Dodawanie(
                new Liczba(7),
                new Mnozenie(new Liczba(5), new Liczba(3))
            ),
            new Liczba(1)
        );

        System.out.println("Wyrażenie 2: " + wyrazenie2);
        System.out.println("Wynik: " + wyrazenie2.oblicz());

        // Wyrażenie: ~(-x * e)
        Wyrazenie wyrazenie3 = new Przeciwny(
            new Mnozenie(
                new Przeciwny(new Zmienna("x")),
                Stala.E
            )
        );

        System.out.println("Wyrażenie 3: " + wyrazenie3);
        System.out.println("Wynik: " + wyrazenie3.oblicz());

        // Wyrażenie: (3 * ~x - 1) / (!(x + 5))
        Wyrazenie wyrazenie4 = new Dzielenie(
            new Odejmowanie(
                new Mnozenie(new Liczba(3), new Przeciwny(new Zmienna("x"))),
                new Liczba(1)
            ),
            new Odwrotny(
                new Dodawanie(new Zmienna("x"), new Liczba(5))
            )
        );

        System.out.println("Wyrażenie 4: " + wyrazenie4);
        System.out.println("Wynik: " + wyrazenie4.oblicz());

        // Wyrażenie: sin((x + 13) * PI / (1 - x))
        Wyrazenie wyrazenie5 = new Sinus(
            new Dzielenie(
                new Mnozenie(
                    new Dodawanie(new Zmienna("x"), new Liczba(13)),
                    Stala.PI
                ),
                new Odejmowanie(new Liczba(1), new Zmienna("x"))
            )
        );

        System.out.println("Wyrażenie 5: " + wyrazenie5);
        System.out.println("Wynik: " + wyrazenie5.oblicz());

        // Wyrażenie: exp(5) + x * log(e, x)
        Wyrazenie wyrazenie6 = new Dodawanie(
            new Exp(new Liczba(5)),
            new Mnozenie(
                new Zmienna("x"),
                new Logarytm(Stala.E, new Zmienna("x"))
            )
        );

        System.out.println("Wyrażenie 6: " + wyrazenie6);
        System.out.println("Wynik: " + wyrazenie6.oblicz());
    }
}
