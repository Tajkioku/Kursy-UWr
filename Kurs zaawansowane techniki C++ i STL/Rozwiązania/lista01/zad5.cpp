#include <iostream>
#include <cstdint>

using namespace std;

// N-ta liczbę Lucasa z dedukcja typu wyniku
// template <typename T>
auto Lucas(uint64_t n) {
    if (n == 0) {
        return 2ull; // L(0) = 2
    } else if (n == 1) {
        return 1ull; // L(1) = 1
    } else {
        return Lucas(n - 1) + Lucas(n - 2);
    }
}

int main() {
    uint64_t n = 10;
    cout << "L(" << n << ") = " << Lucas(n) << endl;
    return 0;
}
