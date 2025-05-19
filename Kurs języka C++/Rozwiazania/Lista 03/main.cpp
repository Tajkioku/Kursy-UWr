#include <iostream>
#include <cmath>
#include "number.hpp"

using namespace std;

int main()
{
    Number x(0.0);
    cout << "Wartosc x: " << x.get_value() << endl;
    x.set_value(1.0);

    Number y = x;
    cout << "Wartosc y: " << y.get_value() << endl;
    Number &&z = Number(5);
    cout << "Wartosc z: " << z.get_value() << endl;

    cout << "Wartosc x: " << x.get_value() << endl;
    cout << "Poprzednia wartosc x: " << x.get_previous_value() << endl;
    cout << "Wartosc x: " << x.get_value() << endl;

    x.set_value(2.0);
    x.set_value(3.0);
    x.set_value(4.0);
    cout << "Wartosc x: " << x.get_value() << endl;
    cout << "Poprzednia wartosc x: " << x.get_previous_value() << endl;
    cout << "Poprzednia wartosc x: " << x.get_previous_value() << endl;
    cout << "Poprzednia wartosc x: " << x.get_previous_value() << endl;
   
    return 0;
}