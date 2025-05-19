#include <iostream>
#include <limits>

int main() {
    std::cout << "Dla long long int: " << std::endl;
    std::cout << "Min: " << std::numeric_limits<long long int>::min() << std::endl;
    std::cout << "Max: " << std::numeric_limits<long long int>::max() << std::endl;
    std::cout << "Liczna bitow: " << sizeof(long long int) * 8 << " bity" << std::endl;
   
    long long int maxx = std::numeric_limits<long long int>::max();
    std::cout << "Liczb dziesietnych: " << std::to_string(maxx).size() << std::endl;
    return 0;
}
