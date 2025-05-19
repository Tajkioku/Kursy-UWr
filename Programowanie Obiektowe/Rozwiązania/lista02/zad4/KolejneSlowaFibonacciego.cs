// Kamil Wozniak 330601
// Lista 2 zad 4
// .NET 9.0.201 
using System;

public class KolejneSlowaFibonacciego
{
    private string slowo1;
    private string slowo2;
    private int index;

    // Konstruktor - domyślne słowa "a" i "b"
    public KolejneSlowaFibonacciego() : this("a", "b")
    {
    }

    public KolejneSlowaFibonacciego(string slowo1, string slowo2)
    {
        this.slowo1 = slowo1;
        this.slowo2 = slowo2;
        this.index = 1;
    }

    // Zwraca kolejne słowo Fibonacciego
    public string next()
    {
        if (index == 1)
        {
            index++;
            return slowo1;
        }
        else if (index == 2)
        {
            index++;
            return slowo2;
        }
        else
        {
            string nowe = slowo2 + slowo1;
            slowo1 = slowo2;
            slowo2 = nowe;
            index++;
            return nowe;
        }
    }
}
