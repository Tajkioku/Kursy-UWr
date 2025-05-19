// Kamil Wozniak 330601
// Lista 4
// .NET 9.0.201 

using System;
using System.Collections.Generic;

namespace LogicFormulas
{
    public class Or : Formula
    {
        public Formula Left { get; private set; }
        public Formula Right { get; private set; }

        public Or(Formula left, Formula right)
        {
            Left = left;
            Right = right;
        }

        public override bool oblicz(Dictionary<string, bool> s)
        {
            return Left.oblicz(s) || Right.oblicz(s);
        }

        public override Formula simplify()
        {
            Formula simplifiedLeft = Left.simplify();
            Formula simplifiedRight = Right.simplify();

            // false || p = p
            if (simplifiedLeft is Stala st1 && st1.Value == false)
                return simplifiedRight;
            if (simplifiedRight is Stala st2 && st2.Value == false)
                return simplifiedLeft;

            // true || p = true
            if (simplifiedLeft is Stala st3 && st3.Value == true)
                return Stala.True;
            if (simplifiedRight is Stala st4 && st4.Value == true)
                return Stala.True;

            return new Or(simplifiedLeft, simplifiedRight);
        }

        public override string ToString()
        {
            return $"({Left.ToString()} v {Right.ToString()})";
        }

        public override bool Equals(object obj)
        {
            if (obj is Or other)
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
