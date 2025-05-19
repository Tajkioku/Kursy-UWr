// Kamil Wozniak 330601
// Lista 2 zad 3
// .NET 9.0.201 
using System;

public class Program
{
    public static void Main()
    {
        Console.WriteLine("== Test LazyIntList ==");
        LazyIntList lista = new LazyIntList();
        Console.WriteLine("Rozmiar listy (oczekiwane 0): " + lista.size());
        Console.WriteLine("element(40) = " + lista.element(40));
        Console.WriteLine("Rozmiar listy (oczekiwane 40): " + lista.size());
        Console.WriteLine("element(38) = " + lista.element(38));
        Console.WriteLine("Rozmiar listy (oczekiwane 40): " + lista.size());
        Console.WriteLine();

        Console.WriteLine("== Test LazyPrimeList ==");
        LazyPrimeList listaP = new LazyPrimeList();
        Console.WriteLine("Rozmiar listy liczb pierwszych (oczekiwane 0): " + listaP.size());
        Console.WriteLine("5-ta liczba pierwsza = " + listaP.element(5));  // 2, 3, 5, 7, 11 → 11
        Console.WriteLine("Rozmiar listy (oczekiwane 5): " + listaP.size());
        Console.WriteLine("2-ga liczba pierwsza = " + listaP.element(2));  // powinna być 3
        Console.WriteLine("Rozmiar listy (oczekiwane 5): " + listaP.size());
    }
}
