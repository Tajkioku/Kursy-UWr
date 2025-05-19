package model;
// Kamil Wozniak 330601
// Lista 5 i 6
// java version "23.0.1" 2024-10-15

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ksiazka implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String tytul;
    public List<Pisarz> autorzy;
    public int rokWydania;

    // Konstruktor dla jednego autora
    public Ksiazka(String tytul, Pisarz autor, int rokWydania) {
        this.tytul = tytul;
        this.autorzy = new ArrayList<>();
        this.autorzy.add(autor);
        this.rokWydania = rokWydania;
    }
    
    // Konstruktor dla wielu autorow
    public Ksiazka(String tytul, int rokWydania, Pisarz... autorzy) {
        this.tytul = tytul;
        this.rokWydania = rokWydania;
        this.autorzy = new ArrayList<>();
        for (Pisarz p : autorzy) {
            this.autorzy.add(p);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Ksiazka [tytul=" + tytul + ", Rok wydania=" + rokWydania + ", autorzy=");
        for (Pisarz p : autorzy) {
            sb.append(p.pseudonim).append(" ");
        }
        sb.append("]");
        return sb.toString();
    }
}
