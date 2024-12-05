package obliczenia;

import struktury.Para;
import struktury.ZbiorTablicowy;

public class Zmienna extends Wyrazenie {
    private static final ZbiorTablicowy zbiorZmiennych = new ZbiorTablicowy(100);
    private final String nazwa;

    public Zmienna(String nazwa) {
        if (nazwa == null || nazwa.isEmpty()) {
            throw new IllegalArgumentException("Nazwa zmiennej nie może być pusta.");
        }
        this.nazwa = nazwa;
    }

    public static void ustawZmienna(String nazwa, double wartosc) {
        zbiorZmiennych.wstaw(new Para(nazwa, wartosc));
    }

    @Override
    public double oblicz() {
        Para para = zbiorZmiennych.szukaj(nazwa);
        if (para == null) {
            throw new IllegalStateException("Zmienna o nazwie '" + nazwa + "' nie została zdefiniowana.");
        }
        return para.getWartosc();
    }

    @Override
    public String toString() {
        return nazwa;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Zmienna zmienna = (Zmienna) obj;
        return nazwa.equals(zmienna.nazwa);
    }
}
