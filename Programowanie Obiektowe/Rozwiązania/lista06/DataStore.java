// Kamil Wozniak 330601
// Lista 5 i 6
// java version "23.0.1" 2024-10-15

import java.io.Serializable;
import java.util.List;

public class DataStore implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public List<Pisarz> pisarze;
    public List<Ksiazka> ksiazki;
    public List<Czytelnik> czytelnicy;
    
    public DataStore(List<Pisarz> pisarze, List<Ksiazka> ksiazki, List<Czytelnik> czytelnicy) {
        this.pisarze = pisarze;
        this.ksiazki = ksiazki;
        this.czytelnicy = czytelnicy;
    }
}
