#include <iostream>
#include <climits>
#include <cstdio>
#include "rational.hpp"

using namespace calculations;
using namespace std;

int main()
{
    Rational r1(1, 2);
    Rational r2(1, 3);
    cout << "1/2 = " << r1 << endl;
    cout << "1/3 = " << r2 << endl;

    cout << "1/2 + 0/3 = " << r1 + r2 << endl;
    cout << "1/2 - 0/3 = " << r1 - r2 << endl;
    cout << "1/2 * 0/3 = " << r1 * r2 << endl;
    cout << "1/2 / 0/3 = " << r1 / r2 << endl;

    cout << "1/2 += 0/3 -> " << (r1 += r2) << endl;
    cout << "1/2 -= 0/3 -> " << (r1 -= r2) << endl;
    cout << "1/2 *= 0/3 -> " << (r1 *= r2) << endl;
    cout << "1/2 /= 0/3 -> " << (r1 /= r2) << endl;

    cout << "-(1/2) = " << -r1 << endl;
    cout << "!(1/2) = " << !r1 << endl;
    cout << "-(1/3) = " << -r2 << endl;
    cout << "!(1/3) = " << !r2 << endl;
    
    cout << "Zamiana 1/3 na int: " << (int)r2 << endl;
    cout << "Zamiana na double: " << (double)r2 << endl;
    
    Rational r3(7, 13);
    cout << "7/13 = " << r3 << endl;
    cout << "Zmiana 7/13 na double: " << (double)r3 << endl;
    
//    Rational r3(1, 0);
    
//    Rational r4(0, 1);
//    r2 /= r4;

    return 0;
}
