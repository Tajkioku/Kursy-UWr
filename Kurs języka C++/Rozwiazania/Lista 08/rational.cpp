#include "rational.hpp"

namespace calculations
{
    int GCD (int a, int b)
    {
        int temp = a % b;
        
        if (temp == 0)
            return b;
        
        return GCD(b, temp);
    }

    void Rational::normalize()
    {
        int x = this->numerator;
        int y = this->denominator;
        int gcd = GCD(x, y);

        x /= gcd;
        y /= gcd;

        if (y < 0)
        {
            x *= -1;
            y *= -1;
        }

        this->numerator   = x;
        this->denominator = y;
    }

    Rational::Rational (int x, int y) // x/y
    {
        if (y == 0) // Mianownik = 0
            throw DenomIsZero();

        this->numerator   = x;
        this->denominator = y;
        normalize();
    }

    Rational::Rational (int x) // x/1
    {
        this->numerator   = x;
        this->denominator = 1;
    }

    Rational operator - (Rational a) // a -> -a
    {
        a.numerator *= -1;
        return a;
    }

    Rational operator ! (Rational a) // x/y -> y/x
    {
        if (a.numerator == 0) // Nowy mianownik = 0
            throw DenomIsZero();
        
        int number = a.numerator;
        a.numerator = a.denominator;
        a.denominator = number;
        a.normalize();

        return a;
    }

    void check_range(long long x, long long y)
    {
        if (x > INT_MAX || x < INT_MIN || y > INT_MAX || y < INT_MIN)
            throw OutOfRange();
    }

    Rational operator + (const Rational &a, const Rational &b) // a + b
    {
        long long x = a.numerator * b.denominator + b.numerator * a.denominator;
        long long y = a.denominator * b.denominator;
        check_range(x, y);

        Rational ans((int)x, (int)y);
        ans.normalize();

        return ans;
    }

    Rational operator - (const Rational &a, const Rational &b) // a - b
    {
        long long x = a.numerator * b.denominator - b.numerator * a.denominator;
        long long y = a.denominator * b.denominator;
        check_range(x, y);

        Rational ans((int)x, (int)y);
        ans.normalize();

        return ans;
    }

    Rational operator * (const Rational &a, const Rational &b) // a * b
    {
        long long x = a.numerator * b.numerator;
        long long y = a.denominator * b.denominator;
        check_range(x, y);

        Rational ans((int)x, (int)y);
        ans.normalize();

        return ans;
    }

    Rational operator / (const Rational &a, const Rational &b) // a / b
    {
        if (b.numerator == 0)
            throw DivByZero();

        long long x = a.numerator * b.denominator;
        long long y = a.denominator * b.numerator;
        check_range(x, y);

        Rational ans((int)x, (int)y);
        ans.normalize();

        return ans;
    }

    
    Rational& Rational::operator += (const Rational &a) // += a
    {
        long long x = this->numerator * a.denominator + a.numerator * this->denominator;
        long long y = this->denominator * a.denominator;
        check_range(x, y);

        this->numerator   = (int)x;
        this->denominator = (int)y;
        normalize();

        return *this;
    }

    Rational& Rational::operator -= (const Rational &a) // -= a
    {
        long long x = this->numerator * a.denominator - a.numerator * this->denominator;
        long long y = this->denominator * a.denominator;
        check_range(x, y);

        this->numerator   = (int)x;
        this->denominator = (int)y;
        normalize();

        return *this;
    }

    Rational& Rational::operator *= (const Rational &a) // *= a
    {
        long long x = this->numerator   * a.numerator;
        long long y = this->denominator * a.denominator;
        check_range(x, y);

        this->numerator   = (int)x;
        this->denominator = (int)y;
        normalize();

        return *this;
    }

    Rational& Rational::operator /= (const Rational &a) // /= a
    {
        if (a.numerator == 0)
            throw DivByZero();

        long long x = this->numerator   * a.denominator;
        long long y = this->denominator * a.numerator;
        check_range(x, y);

        this->numerator   = this->numerator   * a.denominator;
        this->denominator = this->denominator * a.numerator;
        normalize();

        return *this;
    }
    
    Rational::operator double()
    {
        return (double)this->numerator / this->denominator;
    }
    Rational::operator int()
    {
        return this->numerator / this->denominator;
    }

    std::ostream& operator << (std::ostream &output, const calculations::Rational &x)
    {
        std::vector <int> rests;
        std::vector <int> ans;

        int rest;
        rest = x.numerator % x.denominator;
        
        if (rest == 0) // Brak części ułamkowej
        {
            output << x.numerator / x.denominator;
            return output;
        }

        rests.push_back (rest);
        output << x.numerator / x.denominator; // Wypisanie liczby przed .

        while(true)
        {
            ans.push_back (rest * 10 / x.denominator);
            rest = rest * 10 % x.denominator;
            
            if (rest == 0) // Brak nieskończonego rozwinięcia dziesietnego
            {
                output << ".";
                for (auto i : ans)
                    output << i;
                return output;
            }

            for (int j = 0; j < rests.size(); j++) // Potęcjalny cykl
            {
                if (rest == rests[j])
                {
                    output << ".";
                    for (int i = 0; i < j; i++)
                        output << ans[i];

                    output << "(";
                    for (int i = j; i < ans.size(); i++)
                        output << ans[i];
                    output << ")";

                    return output;
                }
            }
            rests.push_back(rest);
        }
    }
}
