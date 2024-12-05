package struktury;

public class ZbiorTablicowy implements Zbior, Cloneable {
    private Para[] zbior;
    private int zapelnienie;

    public ZbiorTablicowy(int rozmiar) {
        if (rozmiar <= 0) {
            throw new IllegalArgumentException("Rozmiar musi wiekszy od 0.");
        }
        this.zbior = new Para[rozmiar];
        this.zapelnienie = 0;
    }

    @Override
    public Para szukaj(String klucz) {
        for (int i = 0; i < zapelnienie; i++) {
            if (zbior[i].klucz.equals(klucz)) {
                return zbior[i];
            }
        }
        return null;
    }

    @Override
    public void wstaw(Para para) {
        Para istniejąca = szukaj(para.klucz);
        if (istniejąca != null) {
            istniejąca.setWartosc(para.getWartosc());
        } else {
            if (zapelnienie >= zbior.length) {
                throw new IllegalStateException("Tablica jest pełna.");
            }
            zbior[zapelnienie++] = para;
        }
    }

    @Override
    public void usun(String klucz) {
        for (int i = 0; i < zapelnienie; i++) {
            if (zbior[i].klucz.equals(klucz)) {
                zbior[i] = zbior[--zapelnienie];
                zbior[zapelnienie] = null; // Usuń ostatni element
                return;
            }
        }
    }

    @Override
    public void czysc() {
        for (int i = 0; i < zapelnienie; i++) {
            zbior[i] = null;
        }
        zapelnienie = 0;
    }

    @Override
    public boolean pusty() {
        return zapelnienie == 0;
    }

    @Override
    public int ile() {
        return zapelnienie;
    }

    @Override
    public ZbiorTablicowy clone() {
        try {
            ZbiorTablicowy sklonowany = (ZbiorTablicowy) super.clone();
            sklonowany.zbior = zbior.clone();
            return sklonowany;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
