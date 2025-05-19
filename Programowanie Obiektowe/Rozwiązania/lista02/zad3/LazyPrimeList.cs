// Kamil Wozniak 330601
// Lista 2 zad 3
// .NET 9.0.201 
using System;

public class LazyPrimeList : LazyIntList
{
    // Nadpisujemy metodę element, aby generowała liczby pierwsze
    public override int element(int i)
    {
        if (i <= 0)
            throw new ArgumentOutOfRangeException(nameof(i), "Indeks musi być dodatni (1-based).");

        while (dane.Count < i)
        {
            // Jeśli lista jest pusta, zaczynamy od liczby 2
            int candidate = dane.Count == 0 ? 2 : dane[dane.Count - 1] + 1;

            // Szukamy kolejnej liczby pierwszej
            while (!IsPrime(candidate))
            {
                candidate++;
            }
            dane.Add(candidate);
        }

        return dane[i - 1];
    }

    // Czy liczba jest pierwsza
    private bool IsPrime(int n)
    {
        if (n < 2) return false;
        for (int j = 2; j * j <= n; j++)
        {
            if (n % j == 0) return false;
        }
        return true;
    }
}
