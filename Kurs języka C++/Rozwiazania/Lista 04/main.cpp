#include <iostream>
#include <cmath>
#include "tabbit.hpp"

using namespace std;

int main()
{
    tab_bit x(150);
    tab_bit y(150);
    tab_bit z(150);

    x.pisz(0, true);
    x.pisz(2, true);
    cout << x[0] << " " << x[1] << " " << x[2] << " " << x[3] << endl;
    y.pisz(1, true);
    y.pisz(3, true);
    cout << y[0] << " " << y[1] << " " << y[2] << " " << y[3] << endl;

    x.pisz(10, true);
    x.pisz(20, true);
    x.pisz(30, true);
    x.pisz(40, true);
    x.pisz(50, true);
    cout << x << endl;
    
    cout << "Operacje bitowe: " << endl;
    z = x;
    cout << z << endl;
    
    z &= y;
    cout << z << endl; 

    z = x;
    z |= y;
    cout << z << endl;

    z = x;
    z ^= y;
    cout << z << endl;
    return 0;
}