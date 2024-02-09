#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
#include <stdexcept>

class Trojkat {
public:
    Trojkat(double a, double b, double c, std::string nazwa = "") : a(a), b(b), c(c), nazwa(nazwa) {
        if (!czyTrojkat()) {
            throw std::invalid_argument("Podane boki nie spełniają warunku trójkąta.");
        }
    }

    Trojkat() : Trojkat(1, 1, 1) {}

    double getA() const { return a; }
    double getB() const { return b; }
    double getC() const { return c; }

    double obwod() const {
        return a + b + c;
    }

    double pole() const {
        double p = obwod() / 2;
        return std::sqrt(p * (p - a) * (p - b) * (p - c));
    }

    friend std::ostream& operator<<(std::ostream& os, const Trojkat& trojkat) {
        os << "Trójkąt " << trojkat.nazwa << ": [a=" << trojkat.a << ", b=" << trojkat.b << ", c=" << trojkat.c << "]";
        return os;
    }

private:
    double a, b, c;
    std::string nazwa;

    bool czyTrojkat() const {
        return (a + b > c) && (a + c > b) && (b + c > a);
    }
};

int main() {
    std::vector<Trojkat> trojkaty {
        {3, 4, 5, "Trojkat 1"},
        {4, 4, 5, "Trojkat 2"},
        {5, 12, 13, "Trojkat 3"},
        {8, 15, 17, "Trojkat 4"},
        {7, 7, 10, "Trojkat 5"},
//        {1, 1, 3, "Trojkat 6"},
//        {9, 9, 9, "Trojkat 7"}
    };

    // Sortowanie
    std::sort(trojkaty.begin(), trojkaty.end(), [](const Trojkat& t1, const Trojkat& t2) {
        return t1.obwod() < t2.obwod();
    });

    std::cout << "Trójkąty posortowane według długości obwodu:" << std::endl;
    for (const auto& trojkat : trojkaty) {
        std::cout << trojkat << " - obwód: " << trojkat.obwod() << std::endl;
    }
    std::cout << std::endl;

    // Minimalne i maksymalne pole
    auto minTrojkat = std::min_element(trojkaty.begin(), trojkaty.end(), [](const Trojkat& t1, const Trojkat& t2) {
        return t1.pole() < t2.pole();
    });

    auto maxTrojkat = std::max_element(trojkaty.begin(), trojkaty.end(), [](const Trojkat& t1, const Trojkat& t2) {
        return t1.pole() < t2.pole();
    });

    std::cout << "Trójkąt o minimalnym polu: " << *minTrojkat << " -> pole: " << minTrojkat->pole() << std::endl;
    std::cout << "Trójkąt o maksymalnym polu: " << *maxTrojkat << " -> pole: " << maxTrojkat->pole() << std::endl;
    std::cout << std::endl;

    // Trojkąty ostrokątne
    std::cout << "Trójkąty ostrokątne:" << std::endl;
    std::for_each(trojkaty.begin(), trojkaty.end(), [](const Trojkat& trojkat) {
        if (trojkat.getA() * trojkat.getA() + trojkat.getB() * trojkat.getB() > trojkat.getC() * trojkat.getC()) {
            std::cout << trojkat << std::endl;
        }
    });

    return 0;
}
