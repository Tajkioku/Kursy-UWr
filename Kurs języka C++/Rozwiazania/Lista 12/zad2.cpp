#include <iostream>
#include <functional>
#include <complex>

template<typename T>
struct LinearFunction {
    T a, b;

    LinearFunction(T a, T b) : a(a), b(b) {}

    T operator()(T x) {
        std::multiplies<T> multiply;
        std::plus<T> add;
        auto product = std::bind(multiply, a, std::placeholders::_1);
        return add(product(x), b);
    }
};

int main() {
    std::cout << "=== Int ===" << std::endl;
    LinearFunction<int> intFunc(2, 3);
    std::cout << "Func(5): " << intFunc(5) << std::endl;
    
    std::cout << "=== Float ===" << std::endl;
    LinearFunction<float> floatFunc((float)1.5, (float)2.5);
    std::cout << "Func(2.0): " << floatFunc(2.0f) << std::endl;

    std::cout << "=== Double ===" << std::endl;
    LinearFunction<double> doubleFunc((double)0.5, (double)1.0);
    std::cout << "Func(3.0): " << doubleFunc(3.0) << std::endl;

    std::cout << "=== Complex ===" << std::endl;
    LinearFunction<std::complex<double>> complexFunc(std::complex<double>(2.0, 1.0), std::complex<double>(1.0, 2.0));
    std::cout << "sFunc(1.0 + 1.0i): " << complexFunc(std::complex<double>(1.0, 1.0)) << std::endl;

    return 0;
}
