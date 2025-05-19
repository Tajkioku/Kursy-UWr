// Kamil Wozniak 330601
// Lista 4
// .NET 9.0.201 

using System;
using System.Collections.Generic;
using LogicFormulas;

namespace LogicFormulasApp
{
    class Program
    {
        static void Main(string[] args)
        {
            if (args.Length > 0 && args[0] == "test")
            {
                RunTests();
            }
            else
            {
                Demo();
            }
        }

        static void Demo()
        {
            // Przykład: -x ∨ (y && true)
            Formula formula = new Or(new Not(new Zmienna("x")), new And(new Zmienna("y"), Stala.True));

            Console.WriteLine("Formula: " + formula.ToString());

            Dictionary<string, bool> s = new Dictionary<string, bool>
            {
                {"x", false},
                {"y", true}
            };

            Console.WriteLine("Wartość formuły: " + formula.oblicz(s));

            // Uproszczenie formuły
            Formula simplified = formula.simplify();
            Console.WriteLine("Uproszczona formuła: " + simplified.ToString());

            Formula expr = !new Zmienna("a") | (new Zmienna("b") & Stala.False);
            Console.WriteLine("Operator overloaded expression: " + expr.ToString());
            
            // Przeglad pre-order
            Console.WriteLine("Przegląd drzewa formuły:");
            foreach (var node in formula)
            {
                Console.WriteLine(" - " + node.ToString());
            }
        }

        // Testy unixowe
        static void RunTests()
        {
            int testsPassed = 0;
            int testsFailed = 0;
            
            // Test 1: oblicz dla stałych
            try
            {
                if (Stala.True.oblicz(null) != true) throw new Exception();
                if (Stala.False.oblicz(null) != false) throw new Exception();
                testsPassed++;
            }
            catch
            {
                Console.WriteLine("Test 1 nie powiódł się: oblicz dla stałych");
                testsFailed++;
            }

            // Test 2: oblicz dla zmiennej
            try
            {
                var dict = new Dictionary<string, bool> { { "x", true } };
                if (new Zmienna("x").oblicz(dict) != true) throw new Exception();
                testsPassed++;
            }
            catch
            {
                Console.WriteLine("Test 2 nie powiódł się: oblicz dla zmiennej");
                testsFailed++;
            }

            // Test 3: oblicz dla Not
            try
            {
                var dict = new Dictionary<string, bool> { { "x", true } };
                if (new Not(new Zmienna("x")).oblicz(dict) != false) throw new Exception();
                testsPassed++;
            }
            catch
            {
                Console.WriteLine("Test 3 nie powiódł się: oblicz dla Not");
                testsFailed++;
            }

            Console.WriteLine($"Testy zakończone: {testsPassed} zaliczonych, {testsFailed} niezaliczonych.");
        }
    }
}
