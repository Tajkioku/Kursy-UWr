// Kamil Wozniak 330601
// Lista 4
// .NET 9.0.201 

using System;
using System.Collections.Generic;

namespace LogicFormulas
{
    public class Not : Formula
    {
        public Formula Operand { get; private set; }

        public Not(Formula operand)
        {
            Operand = operand;
        }

        public override bool oblicz(Dictionary<string, bool> s)
        {
            return !Operand.oblicz(s);
        }

        public override Formula simplify()
        {
            Formula simplifiedOperand = Operand.simplify();
            // -(-p) = p
            if (simplifiedOperand is Not innerNot)
            {
                return innerNot.Operand;
            }
            return new Not(simplifiedOperand);
        }

        public override string ToString()
        {
            return $"-({Operand.ToString()})";
        }

        public override bool Equals(object obj)
        {
            if (obj is Not other)
                return Operand.Equals(other.Operand);
            return false;
        }

        public override int GetHashCode()
        {
            return ~Operand.GetHashCode();
        }

        protected override IEnumerable<Formula> GetChildren()
        {
            yield return Operand;
        }
    }
}
