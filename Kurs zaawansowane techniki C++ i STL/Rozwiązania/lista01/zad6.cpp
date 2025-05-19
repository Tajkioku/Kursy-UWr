#include <iostream>
#include <cmath>

using namespace std;

int main() {
    double a, b, c;
    cout << "Podaj wspolczynniki a, b i c: ";
    cin >> a >> b >> c;

    if (double delta = b * b - 4 * a * c; a == 0) {
        cout << "To nie jest rownanie kwadratowe." << endl;
    } else if (delta > 0) {
        double x1 = (-b + std::sqrt(delta)) / (2 * a);
        double x2 = (-b - std::sqrt(delta)) / (2 * a);
        cout << "Rownanie ma 2 pierwiastki rzeczywiste: " << x1 << " " << x2 << endl;
    } else if (delta == 0) {
        double x = -b / (2 * a);
        cout << "Rownanie ma 1 pierwiastek rzeczywisty: " << x << endl;
    } else {
        cout << "Rownanie nie ma pierwiastkow rzeczywistych." << endl;
    }

    return 0;
}
