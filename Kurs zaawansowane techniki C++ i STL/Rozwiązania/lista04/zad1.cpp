#include <iostream>
#include <functional>

template <typename F, typename G, typename H>
class compose_f_gx_hx {
    public:
        compose_f_gx_hx(F f, G g, H h) : f_(f), g_(g), h_(h) {}

        template <typename T>
        auto operator()(T x) const {
            return f_(g_(x), h_(x));
        }

    private:
        F f_;
        G g_;
        H h_;
};

double g(double x) {
    return x * x;
}

double h(double x) {
    return x + 1;
}

double f(double x, double y) {
    return x - y;
}

int main() {
    auto composed = compose_f_gx_hx(f, g, h);
    
    // f(g(5), h(5)) = f(25, 6) = 25 - 6
    double result = composed(5);
    std::cout << "Result: " << result << std::endl;

    return 0;
}
