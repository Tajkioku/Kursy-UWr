#include <iostream>

// Lucas
template <int N>
struct LucasNumber {
    static const int value = LucasNumber<N - 1>::value + LucasNumber<N - 2>::value;
};

// Bazowe wartosci
template <>
struct LucasNumber<0> {
    static const int value = 2;
};

template <>
struct LucasNumber<1> {
    static const int value = 1;
};

int main() {
    std::cout << "10th Lucas Number: " << LucasNumber<10>::value << std::endl;

    return 0;
}
