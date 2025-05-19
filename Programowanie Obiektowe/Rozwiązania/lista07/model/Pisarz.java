package model;
// Kamil Wozniak 330601
// Lista 5 i 6
// java version "23.0.1" 2024-10-15

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Pisarz implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String pseudonim;
    public int rokUrodzenia;
    public List<Ksiazka> ksiazki;
    // Lista obserwatorow – nie chcemy jej zapisywac
    transient List<PriorytetowyObserwator> obserwatorzy;

    public Pisarz(String pseudonim, int rokUrodzenia) {
        this.pseudonim = pseudonim;
        this.rokUrodzenia = rokUrodzenia;
        this.ksiazki = new ArrayList<>();
        this.obserwatorzy = new ArrayList<>();
    }
    
    public void dodajObserwatora(PriorytetowyObserwator o) {
        if (obserwatorzy == null) {
            obserwatorzy = new ArrayList<>();
        }
        if (!obserwatorzy.contains(o)) {
            obserwatorzy.add(o);
        }
    }
    
    public void usunObserwatora(PriorytetowyObserwator o) {
        if (obserwatorzy != null) {
            obserwatorzy.remove(o);
        }
    }
    
    // Sortowanie wg priorytetu
    public void notifyObservers(Ksiazka k) {
        if (obserwatorzy == null) return;
        List<PriorytetowyObserwator> sorted = new ArrayList<>(obserwatorzy);
        sorted.sort(Comparator.comparingInt(PriorytetowyObserwator::getPriority));
        for (PriorytetowyObserwator o : sorted) {
            o.powiadomienie(k);
        }
    }
    
    // Napisz - jeden autor
    public void napisz(String tytul, int rokWydania) {
        Ksiazka k = new Ksiazka(tytul, this, rokWydania);
        ksiazki.add(k);
        notifyObservers(k);
    }
    
    @Override
    public String toString() {
        return "Pisarz [pseudonim=" + pseudonim + ", rokUrodzenia=" + rokUrodzenia + "]";
    }
}
