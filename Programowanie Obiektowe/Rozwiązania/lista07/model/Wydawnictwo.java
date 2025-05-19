package model;
// Kamil Wozniak 330601
// Lista 5 i 6
// java version "23.0.1" 2024-10-15

public class Wydawnictwo implements PriorytetowyObserwator {
    private static final long serialVersionUID = 1L;
    
    public char symbol;
    public int rokZalozenia;

    public Wydawnictwo(char symbol, int rokZalozenia) {
        this.symbol = symbol;
        this.rokZalozenia = rokZalozenia;
    }
    
    public void wydajKsiazke(Ksiazka k) {
        if (k.tytul.charAt(0) == symbol) {
            System.out.println("Wydawnictwo " + symbol + " wydaje ksiazke: " + k);
        }
    }
    
    @Override
    public void powiadomienie(Ksiazka k) {
        wydajKsiazke(k);
    }
    
    @Override
    public int getPriority() {
        return 2;
    }
    
    @Override
    public String toString() {
        return "Wydawnictwo [symbol=" + symbol + ", Rok zalozenia=" + rokZalozenia + "]";
    }
}
