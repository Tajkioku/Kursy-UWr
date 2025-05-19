// Kamil Wozniak 330601
// Lista 4
// .NET 9.0.201 

using System;
using System.Collections.Generic;

namespace LogicFormulas
{
    public class Stala : Formula
    {
        // public getter (można odczytać wartość) i private setter (wartość ustawia się tylko wewnątrz klasy)
        public bool Value { get; private set; }
        
        // Singleton
        public static readonly Stala True = new Stala(true);
        public static readonly Stala False = new Stala(false);

        private Stala(bool value)
        {
            Value = value;
        }

        // Zwraca wartość
        public static Stala GetInstance(bool value)
        {
            return value ? True : False;
        }

        public override bool oblicz(Dictionary<string, bool> s)
        {
            return Value;
        }

        // Stała jest już uproszczona
        public override Formula simplify()
        {
            return this;
        }

        public override string ToString()
        {
            return Value.ToString().ToLower();
        }

        public override bool Equals(object obj)
        {
            if (obj is Stala other)
            {
                return this.Value == other.Value;
            }
            return false;
        }

        // Zwraca hash code oparty na wartości logicznej
        public override int GetHashCode()
        {
            return Value.GetHashCode();
        }

        // Stała nie ma żadnych dzieci (to jest liść drzewa), stąd yield break kończy iterację
        protected override IEnumerable<Formula> GetChildren()
        {
            yield break;
        }
    }
}
