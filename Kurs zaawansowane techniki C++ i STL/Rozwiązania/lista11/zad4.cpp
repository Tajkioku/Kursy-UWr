#include <iostream>
#include <cstddef>

template <size_t N>
double inner(double *x, double *y) {
    return x[N-1] * y[N-1] + inner<N-1>(x, y);
}

template <>
double inner<0>(double *x, double *y) {
    return 0;
}

int main() {
    // Wektory dlugosci 3 
    double x[] = {1.0, 2.0, 3.0};
    double y[] = {4.0, 5.0, 6.0};

    std::cout << "Iloczyn skalarny x i y: " << inner<3>(x, y) << std::endl;

    // Wektory dlugosci 4
    double a[] = {1.0, 2.0, 3.0, 4.0};
    double b[] = {5.0, 6.0, 7.0, 8.0};

    std::cout << "Iloczyn skalarny a i b: " << inner<4>(a, b) << std::endl;

    return 0;
}
