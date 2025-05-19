#include <iostream>
#include <vector>
#include <iterator>
#include <numeric>
#include <cmath>
#include <iomanip>

int main() {
    std::istream_iterator<double> start(std::cin), end;
    std::vector<double> liczby(start, end);

    // Srednia
    double suma = std::accumulate(liczby.begin(), liczby.end(), 0.0);
    double srednia = suma / liczby.size();

    // Odchylenie standardowe
    double suma_kwadratow = std::accumulate(liczby.begin(), liczby.end(), 0.0, 
        [srednia](const double a, const double b) {
            return a + pow(b - srednia, 2);
        });
    double odchylenie_std = sqrt(suma_kwadratow / liczby.size());

    // Ctrl + D
    std::cout << std::fixed << std::setprecision(3);
    std::cout << "Średnia: " << srednia << std::endl;
    std::cout << "Odchylenie standardowe: " << odchylenie_std << std::endl;

    return 0;
}
