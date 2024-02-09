#include <iostream>
#include <regex>
#include <vector>

int main() {
    std::regex pattern(R"(\(\-?\d+(\.\d+)?[+-]\d+(\.\d+)?[iI]\))");
    std::vector<std::string> tests = {
        "(12+3I)",       // Prawidłowe
        "(7.4-0.5i)",    // Prawidłowe
        "(2+0.01i)",     // Prawidłowe
        "3+4I",          // Nieprawidłowe 
        "(3,14-2,72i)",  // Nieprawidłowe (przecinek)
        "(5.7i)",        // Nieprawidłowe
    };

    for (const auto& input : tests) {
        if (std::regex_match(input, pattern)) {
            std::cout << "Liczba " << input << " jest prawidłowa." << std::endl;
        } else {
            std::cout << "Liczba " << input << " jest nieprawidłowa." << std::endl;
        }
    }

    return 0;
}
