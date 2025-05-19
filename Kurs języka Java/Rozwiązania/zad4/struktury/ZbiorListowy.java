package struktury;

import java.util.ArrayList;
import java.util.List;

public class ZbiorListowy implements Zbior {
    private final List<Para> pary = new ArrayList<>();

    @Override
    public Para szukaj(String klucz) {
        for (Para para : pary) {
            if (para.klucz.equals(klucz)) {
                return para;
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
            pary.add(para);
        }
    }

    @Override
    public void usun(String klucz) {
        pary.removeIf(para -> para.klucz.equals(klucz));
    }

    @Override
    public void czysc() {
        pary.clear();
    }

    @Override
    public boolean pusty() {
        return pary.isEmpty();
    }

    @Override
    public int ile() {
        return pary.size();
    }
}
