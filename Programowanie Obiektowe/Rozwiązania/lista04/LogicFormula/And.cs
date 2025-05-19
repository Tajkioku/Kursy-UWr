// Kamil Wozniak 330601
// Lista 4
// .NET 9.0.201 

using System;
using System.Collections.Generic;

namespace LogicFormulas
{
    public class And : Formula
    {
        public Formula Left { get; private set; }
        public Formula Right { get; private set; }

        public And(Formula left, Formula right)
        {
            Left = left;
            Right = right;
        }

        public override bool oblicz(Dictionary<string, bool> s)
        {
            return Left.oblicz(s) && Right.oblicz(s);
        }

        public override Formula simplify()
        {
            Formula simplifiedLeft = Left.simplify();
            Formula simplifiedRight = Right.simplify();

            // p && false = false, false && p = false
            if (simplifiedLeft is Stala st1 && st1.Value == false)
                return Stala.False;
            if (simplifiedRight is Stala st2 && st2.Value == false)
                return Stala.False;

            // true && p = p
            if (simplifiedLeft is Stala st3 && st3.Value == true)
                return simplifiedRight;
            if (simplifiedRight is Stala st4 && st4.Value == true)
                return simplifiedLeft;

            return new And(simplifiedLeft, simplifiedRight);
        }

        public override string ToString()
        {
            return $"({Left.ToString()} ∧ {Right.ToString()})";
        }

        public override bool Equals(object obj)
        {
            if (obj is And other)
            {
                return Left.Equals(other.Left) && Right.Equals(other.Right);
            }
            return false;
        }

        public override int GetHashCode()
        {
            return Left.GetHashCode() ^ Right.GetHashCode();
        }

        protected override IEnumerable<Formula> GetChildren()
        {
            yield return Left;
            yield return Right;
        }
    }
}
