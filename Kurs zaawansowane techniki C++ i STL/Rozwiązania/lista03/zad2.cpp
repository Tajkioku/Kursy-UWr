#include <iostream>
#include <limits>

int main() {
    std::cout << "Najblizsze 0 dla float: " << std::numeric_limits<float>::min() << std::endl;
    std::cout << "Najblizsze 0 dla double: " << std::numeric_limits<double>::min() << std::endl;
    std::cout << "Max dla float: " << std::numeric_limits<float>::max() << std::endl;
    std::cout << "Max double: " << std::numeric_limits<double>::max() << std::endl;
    // Roznica pomiedzy 1 a >1 to maszynowy epsilon
    std::cout << "Epsilon dla float: " << std::numeric_limits<float>::epsilon() << std::endl;
    std::cout << "Epsilon dla double: " << std::numeric_limits<double>::epsilon() << std::endl;

    return 0;
}
