import java.util.HashMap;

public class Liczby {

    // Tablice liczebników składowych
    static String[] jednosci = {
        null, "jeden", "dwa", "trzy", "cztery", "pięć", "sześć", "siedem", "osiem", "dziewięć"
    };

    static String[] nastki = {
        "dziesięć", "jedenaście", "dwanaście", "trzynaście", "czternaście", "piętnaście", 
        "szesnaście", "siedemnaście", "osiemnaście", "dziewiętnaście"
    };

    static String[] dziesiatki = {
        null, "dziesięć", "dwadzieścia", "trzydzieści", "czterdzieści", "pięćdziesiąt", 
        "sześćdziesiąt", "siedemdziesiąt", "osiemdziesiąt", "dziewięćdziesiąt"
    };

    static String[] setki = {
        null, "sto", "dwieście", "trzysta", "czterysta", "pięćset", 
        "sześćset", "siedemset", "osiemset", "dziewięćset"
    };

    // Funkcja zamieniająca liczbę na jej słowną reprezentację
    public static String liczbaNaSlowa(int liczba) {
        if (liczba == Integer.MIN_VALUE) {
            return "minus dwa miliardy sto czterdzieści siedem milionów czterysta osiemdziesiąt trzy tysiące sześćset czterdzieści osiem";
        }
        
        if (liczba == 0) {
            return "zero";
        }

        StringBuilder result = new StringBuilder();
        if (liczba < 0) {
            result.append("minus ");
            liczba = -liczba;
        }

        if (liczba / 1000000 > 0) {
            result.append(liczbaNaSlowaTys(liczba / 1000000)).append(" milionów ");
            liczba %= 1000000;
        }

        if (liczba / 1000 > 0) {
            result.append(liczbaNaSlowaTys(liczba / 1000)).append(" tysięcy ");
            liczba %= 1000;
        }

        if (liczba > 0) {
            result.append(liczbaNaSlowaTys(liczba));
        }

        return result.toString().trim();
    }

    // Funkcja zamieniająca liczby mniejsze niż 1000 na słowną reprezentację
    public static String liczbaNaSlowaTys(int liczba) {
        StringBuilder result = new StringBuilder();

        if (liczba / 100 > 0) {
            result.append(setki[liczba / 100]).append(" ");
            liczba %= 100;
        }

        if (liczba >= 10 && liczba <= 19) {
            result.append(nastki[liczba - 10]).append(" ");
        } else {
            if (liczba / 10 > 0) {
                result.append(dziesiatki[liczba / 10]).append(" ");
            }
            if (liczba % 10 > 0) {
                result.append(jednosci[liczba % 10]).append(" ");
            }
        }

        return result.toString().trim();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Brak argumentów. Podaj liczby całkowite jako argumenty.");
            return;
        }

        for (String arg : args) {
            try {
                int liczba = Integer.parseInt(arg);
                System.out.println(liczbaNaSlowa(liczba));
            } catch (NumberFormatException e) {
                System.out.println("Błędny format liczby: " + arg);
            }
        }
    }
}
