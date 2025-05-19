package model;
// Kamil Wozniak 330601
// Lista 5 i 6
// java version "23.0.1" 2024-10-15

import java.io.*;
import java.util.*;

public class Literatura {
    
    public static void saveData(DataStore data, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(data);
            System.out.println("Dane zapisane do pliku: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static DataStore loadData(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            DataStore data = (DataStore) in.readObject();
            System.out.println("Dane odczytane z pliku: " + filename);
            return data;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Blad podczas odczytu danych. Tworzenie nowych danych.");
            return null;
        }
    }
    
    // Metoda tworzaca ksiazke współautorska
    public static void napiszKsiazke(int rokWydania, String tytul, Pisarz... autorzy) {
        Ksiazka k = new Ksiazka(tytul, rokWydania, autorzy);
        for (Pisarz p : autorzy) {
            p.ksiazki.add(k);
            p.notifyObservers(k);
        }
    }
    
    public static void main(String[] args) {
        String filename = "dane.ser";
        DataStore data = loadData(filename);
        
        if (data == null) {
            List<Pisarz> pisarze = new ArrayList<>();
            List<Ksiazka> ksiazki = new ArrayList<>();
            List<Czytelnik> czytelnicy = new ArrayList<>();
            
            // Tworzymy obserwatorow
            Wydawnictwo wyd = new Wydawnictwo('T', 1990);
            Krytyk krytyk = new Krytyk("Jan Kowalski");
            Czytelnik czytelnik1 = new Czytelnik("Adam");
            czytelnicy.add(czytelnik1);
            
            // Tworzymy autorow
            Pisarz p1 = new Pisarz("Litwos", 1970);
            Pisarz p2 = new Pisarz("Aho", 1965);
            Pisarz p3 = new Pisarz("Hopcroft", 1950);
            Pisarz p4 = new Pisarz("Ullmann", 1945);

            // Tworzymy wydawnictwa
            List<Wydawnictwo> wydawnictwa = new ArrayList<>();
            wydawnictwa.add(new Wydawnictwo('T', 1990));
            
            // Rejestracja obserwatorów wedlug priorytetu
            p1.dodajObserwatora(krytyk);
            p1.dodajObserwatora(wyd);
            p1.dodajObserwatora(czytelnik1);
            
            p2.dodajObserwatora(krytyk);
            p2.dodajObserwatora(czytelnik1);
            
            p3.dodajObserwatora(wyd);
            p3.dodajObserwatora(krytyk);
            
            p4.dodajObserwatora(wyd);
            p4.dodajObserwatora(czytelnik1);
            p4.dodajObserwatora(krytyk);
            
            pisarze.add(p1);
            pisarze.add(p2);
            pisarze.add(p3);
            pisarze.add(p4);
            
            // Przykładowe operacje
            p1.napisz("Trylogia", 2000);
            napiszKsiazke(2005, "AiSD", p2, p3, p4);
            
            // Zbieramy unikalne ksiazki
            Set<Ksiazka> zbiorKsiazek = new HashSet<>();
            for (Pisarz p : pisarze) {
                zbiorKsiazek.addAll(p.ksiazki);
            }
            ksiazki.addAll(zbiorKsiazek);
            
            data = new DataStore(ksiazki, pisarze, wydawnictwa);
            saveData(data, filename);
        } else {
            System.out.println("Pisarze:");
            for (Pisarz p : data.pisarze) {
                System.out.println(p);
                for (Ksiazka k : p.ksiazki) {
                    System.out.println("   " + k);
                }
            }
        }
    }
}
