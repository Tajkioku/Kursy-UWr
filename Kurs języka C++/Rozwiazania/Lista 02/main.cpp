#include <iostream>
#include <cmath>
#include "point.hpp"
#include "line.hpp"
#include "triangle.hpp"

using namespace std;

int main()
{
    Point x(1.23, 4.56);
    cout << "Punkt: " << x << endl;
    x.translate(5.55, -11.43);
    cout << "Po translacji: " << x << endl;
    x.rotate(0, 0, M_PI);
    cout << "Roctacja w (0, 0) o pi: " << x << endl;
    x.rotate(0, 0, M_PI);
    cout << "Roctacja w (0, 0) o kolejne pi: " << x << endl;

    cout << endl;

    cout << "Odcinek:" << endl;
    Line a(0, 0, 1, 1);
    Line b = a;
    cout << a << "\n" << b << endl;
    b.translate(1.2, 3.4);
    cout << "Po translacji: " << b << endl;
    a.rotate(0, 0, M_PI);
    cout << "Roctacja w (0, 0) o pi: " << a << endl;
    a.rotate(0, 0, M_PI);
    cout << "Roctacja w (0, 0) o kolejne pi: " << a << endl;
    cout << "Dlugosc odcinka: " << a.length() << endl;
  
    cout << "Trojkat: " << endl;
    Triangle t1(0, 0, 1, 3, 2, 2);
    Triangle t2 = t1;
    cout << t1 << "\n" << t2 << endl;
    cout << "Pole: " << t1.area() << endl;
    cout << "Obowd: " << t1.perimeter() << endl;
    t2.translate(2, 3);
    cout << "Po translacji: " << t2 << endl;
    t1.rotate(0, 0, M_PI);
    cout << "Roctacja w (0, 0) o pi: " << t1 << endl;
    t1.rotate(0, 0, M_PI);
    cout << "Roctacja w (0, 0) o kolejne pi: " << t1 << endl;

    return 0;
}