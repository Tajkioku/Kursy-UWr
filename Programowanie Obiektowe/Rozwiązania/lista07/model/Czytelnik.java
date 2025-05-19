package model;
// Kamil Wozniak 330601
// Lista 5 i 6
// java version "23.0.1" 2024-10-15

public class Czytelnik implements PriorytetowyObserwator {
    private static final long serialVersionUID = 1L;
    
    public String imie;

    public Czytelnik(String imie) {
        this.imie = imie;
    }
    
    @Override
    public void powiadomienie(Ksiazka k) {
        System.out.println("Czytelnik " + imie + " otrzymal powiadomienie o ksiazce: " + k);
    }
    
    @Override
    public int getPriority() {
        return 3;
    }
    
    @Override
    public String toString() {
        return "Czytelnik [imie=" + imie + "]";
    }
}
