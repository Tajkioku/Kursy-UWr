// Kamil Wozniak 330601
// Lista 4
// .NET 9.0.201  

using System;
using System.Collections.Generic;

namespace LogicFormulas
{
    public abstract class Formula : IEnumerable<Formula>
    {
        public abstract bool oblicz(Dictionary<string, bool> s);
        public abstract Formula simplify();
        public abstract override string ToString();
        public abstract override bool Equals(object obj);
        public abstract override int GetHashCode();

        // Implementacja IEnumerable dla przeglądania drzewa (pre-order)
        public IEnumerator<Formula> GetEnumerator()
        {
            // Najpierw zwracamy bieżący węzeł (this)
            yield return this;
            // Następnie rekurencyjnie przechodzimy przez wszystkie dzieci tego węzła
            foreach (var child in GetChildren())
            {
                foreach (var node in child)
                {
                    yield return node;
                }
            }
        }
        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }

        // Zwraca bezpośrednie dzieci danego węzła
        protected abstract IEnumerable<Formula> GetChildren();

        // Przeciążenia operatorów umożliwiające budowanie wyrażeń w naturalny sposób
        public static Formula operator &(Formula a, Formula b)
        {
            return new And(a, b);
        }
        
        public static Formula operator |(Formula a, Formula b)
        {
            return new Or(a, b);
        }

        public static Formula operator !(Formula a)
        {
            return new Not(a);
        }
    }
}
