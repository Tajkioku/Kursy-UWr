package model;
// Kamil Wozniak 330601
// Lista 5 i 6
// java version "23.0.1" 2024-10-15

public class Krytyk implements PriorytetowyObserwator {
    private static final long serialVersionUID = 1L;
    
    public String nazwa;

    public Krytyk(String nazwa) {
        this.nazwa = nazwa;
    }
    
    @Override
    public void powiadomienie(Ksiazka k) {
        System.out.println("Krytyk " + nazwa + " komentuje nową ksiazke: " + k);
    }
    
    @Override
    public int getPriority() {
        return 1;
    }
    
    @Override
    public String toString() {
        return "Krytyk [nazwa=" + nazwa + "]";
    }
}
