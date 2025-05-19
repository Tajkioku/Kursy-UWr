package model;

import java.io.Serializable;
import java.util.List;

public class DataStore implements Serializable {
    private static final long serialVersionUID = 1L;

    public List<Ksiazka> ksiazki;
    public List<Pisarz> pisarze;
    public List<Wydawnictwo> wydawnictwa;

    public DataStore(List<Ksiazka> ksiazki, List<Pisarz> pisarze, List<Wydawnictwo> wydawnictwa) {
        this.ksiazki = ksiazki;
        this.pisarze = pisarze;
        this.wydawnictwa = wydawnictwa;
    }
}
