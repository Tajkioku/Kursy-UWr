// Kamil Wozniak 330601
// Lista 2 zad 3
// .NET 9.0.201 
using System;
using System.Collections.Generic;

public class LazyIntList
{
    // Lista przechowujaca wygenerowane elementy
    protected List<int> dane;

    public LazyIntList()
    {
        dane = new List<int>();
    }

    // Zwraca i-ty element
    public virtual int element(int i)
    {
        if (i <= 0)
            throw new ArgumentOutOfRangeException(nameof(i), "Indeks musi być dodatni (1-based).");

        // Generujemy kolejne liczby
        while (dane.Count < i)
        {
            dane.Add(dane.Count + 1);
        }

        return dane[i - 1];
    }

    // Zwraca liczbe wygenerowanych elementow
    public int size()
    {
        return dane.Count;
    }
}
