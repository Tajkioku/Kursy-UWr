// Kamil Wozniak 330601
// Lista 2 zad 4
// .NET 9.0.201 
using System;
using System.Collections.Generic;

public class JakiesSlowoFibonacciego
{
    // Lista pamietajaca wygenerowane słowa
    private List<string> slowa;

    // Konstruktor - domyślne "a" i "b"
    public JakiesSlowoFibonacciego() : this("a", "b")
    {
    }

    public JakiesSlowoFibonacciego(string slowo1, string slowo2)
    {
        slowa = new List<string>();
        slowa.Add(slowo1); // indeks 1
        slowa.Add(slowo2); // indeks 2
    }

    // Zwraca i-te słowo Fibonacciego
    public string slowo(int i)
    {
        if (i <= 0)
            throw new ArgumentOutOfRangeException(nameof(i), "Indeks musi być dodatni (1-based).");

        // Generujemy brakujące słowa
        while (slowa.Count < i)
        {
            string next = slowa[slowa.Count - 1] + slowa[slowa.Count - 2];
            slowa.Add(next);
        }

        return slowa[i - 1];
    }
}
