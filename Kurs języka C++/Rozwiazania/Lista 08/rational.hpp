#ifndef RATIONAL_HPP
#define RATIONAL_HPP

#include <iostream>
#include <limits.h>
#include <vector>
#include <algorithm>

namespace calculations
{

    class DivByZero : std::exception {};
    class DenomIsZero : std::exception {};
    class OutOfRange : std::exception {};

    int GCD (int a, int b);

    class Rational
    {
        public:
            int numerator;
            int denominator;
            void normalize();

            Rational (int x, int y); // x/y
            Rational (int x);        // x/1

            friend Rational operator - (Rational a); // a -> -a
            friend Rational operator ! (Rational a); // a/b -> b/a
            friend Rational operator + (const Rational &a, const Rational &b);
            friend Rational operator - (const Rational &a, const Rational &b);
            friend Rational operator * (const Rational &a, const Rational &b);
            friend Rational operator / (const Rational &a, const Rational &b);

            Rational& operator += (const Rational &x);
            Rational& operator -= (const Rational &x);
            Rational& operator *= (const Rational &x);
            Rational& operator /= (const Rational &x);

            operator double();
            operator int();

            friend std::ostream& operator << (std::ostream &output, const Rational &x);
    };
}


#endif // Rational_HPP
