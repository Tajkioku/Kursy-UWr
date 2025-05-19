#include <iostream>
#include <functional>

template <typename T>
class po_kolei {
public:
    std::function<T(T)> f1;
    std::function<T(T)> f2;

    po_kolei(std::function<T(T)> f1, std::function<T(T)> f2) : f1(f1), f2(f2) {}

    auto operator()(T x) const {
        return f2(f1(x));
    }

    // Dokladanie f3
    po_kolei operator()(std::function<T(T)> f3) const {
        return po_kolei<T>(*this, f3);
    }
};

int main() {
    auto f1 = [](int x) -> int { return x + 1; };
    auto f2 = [](int x) -> int { return x * 2; };
    auto f3 = [](int x) -> int { return x - 3; };
    
    po_kolei<int> skladanie(f1, f2);

    int wynik = skladanie(5); // Najpierw (5+1), potem (6*2)
    std::cout << "(f1, f2): " << wynik << std::endl;

    auto skladanie2 = skladanie(f3);
    int wynik2 = skladanie2(5); // Najpierw (5+1), potem (6*2), potem (12-3)
    std::cout << "((f1, f2), f3): " << wynik2 << std::endl;

    auto skladanie3 = skladanie2(skladanie(f3)); // skaldanie(f3) = 9
    int wynik3 = skladanie3(5); // (9+1), (10*2), (20-3)
    std::cout << "Wynik3: " << wynik3 << std::endl;

    return 0;
}
