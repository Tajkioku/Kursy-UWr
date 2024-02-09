#include <iostream>

// Silnia
template <int N>
struct Factorial {
    static const int value = N * Factorial<N - 1>::value;
};

// Bazowe wartosci
template <>
struct Factorial<0> {
    static const int value = 1;
};

// Dwumian
template <int N, int K>
struct BinomialCoefficient {
    static const int value = Factorial<N>::value / (Factorial<K>::value * Factorial<N - K>::value);
};

int main() {
    std::cout << "(5, 3): " << BinomialCoefficient<5, 3>::value << std::endl;

    return 0;
}
