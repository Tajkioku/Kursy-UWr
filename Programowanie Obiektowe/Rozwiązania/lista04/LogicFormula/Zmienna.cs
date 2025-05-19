// Kamil Wozniak 330601
// Lista 4
// .NET 9.0.201 

using System;
using System.Collections.Generic;

namespace LogicFormulas
{
    public class Zmienna : Formula
    {
        public string Nazwa { get; private set; }

        public Zmienna(string nazwa)
        {
            Nazwa = nazwa;
        }

        public override bool oblicz(Dictionary<string, bool> s)
        {
            if (s != null && s.ContainsKey(Nazwa))
                return s[Nazwa];
            throw new ArgumentException($"Brak wartości dla zmiennej: {Nazwa}");
        }

        public override Formula simplify()
        {
            return this;
        }

        public override string ToString()
        {
            return Nazwa;
        }

        public override bool Equals(object obj)
        {
            if (obj is Zmienna other)
                return Nazwa == other.Nazwa;
            return false;
        }

        public override int GetHashCode()
        {
            return Nazwa.GetHashCode();
        }

        protected override IEnumerable<Formula> GetChildren()
        {
            yield break;
        }
    }
}
