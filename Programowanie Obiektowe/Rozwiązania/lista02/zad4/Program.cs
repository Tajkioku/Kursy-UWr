// Kamil Wozniak 330601
// Lista 2 zad 4
// .NET 9.0.201 
using System;

public class Program
{
    public static void Main()
    {
        Console.WriteLine("== Test KolejneSlowaFibonacciego ==");
        KolejneSlowaFibonacciego fibSlowa = new KolejneSlowaFibonacciego();
        Console.WriteLine(fibSlowa.next()); // "a"
        Console.WriteLine(fibSlowa.next()); // "b"
        Console.WriteLine(fibSlowa.next()); // "ba"
        Console.WriteLine(fibSlowa.next()); // "bab"
        Console.WriteLine(fibSlowa.next()); // "babba"
        Console.WriteLine();

        Console.WriteLine("== Test JakiesSlowoFibonacciego ==");
        JakiesSlowoFibonacciego fibSlowo = new JakiesSlowoFibonacciego();
        Console.WriteLine(fibSlowo.slowo(1)); // "a"
        Console.WriteLine(fibSlowo.slowo(2)); // "b"
        Console.WriteLine(fibSlowo.slowo(3)); // "ba"
        Console.WriteLine(fibSlowo.slowo(5)); // "babba"
        Console.WriteLine(fibSlowo.slowo(4)); // "bab"
    }
}
