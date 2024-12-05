public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Error: podaj liczby jako argumenty wywołania programu.");
            return;
        }

        for (String arg : args) {
            try {
                long liczba = Long.parseLong(arg);
                System.out.print(liczba + " = ");

                long[] czynniki = LiczbyPierwsze.naCzynnikiPierwsze(liczba);
                for (int i = 0; i < czynniki.length; i++) {
                    if (i > 0) System.out.print(" * ");
                    System.out.print(czynniki[i]);
                }
                System.out.println();
            } catch (NumberFormatException e) {
                System.err.println("Błąd: '" + arg + "' nie jest liczbą całkowitą.");
            }
        }
    }
}
