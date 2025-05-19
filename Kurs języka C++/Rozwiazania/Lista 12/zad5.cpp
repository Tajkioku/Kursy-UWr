#include <iostream>
#include <functional>

int main() {
    std::function<void(int)> collatz = [&collatz](int n) {
        std::cout << n << " ";
        
        if (n == 1)
            return;
        
        if (n % 2 == 0)
            collatz(n / 2);
        else
            collatz(3 * n + 1);
    };
    
    int n;
    std::cout << "Podaj wartość początkową (n > 0): ";
    std::cin >> n;
    
    if (n > 0) {
        std::cout << "Ciąg Collatza: ";
        collatz(n);
        std::cout << std::endl;
    } else {
        std::cout << "Podano nieprawidłową wartość początkową!" << std::endl;
    }
    
    return 0;
}
