#include <iostream>

// NWD
template <int A, int B>
struct GCD {
    static const int value = GCD<B, A % B>::value;
};

// Bazowa wartosc
template <int A>
struct GCD<A, 0> {
    static const int value = A;
};

int main() {
    std::cout << "NWD 48 i 18: " << GCD<48, 18>::value << std::endl;

    return 0;
}
