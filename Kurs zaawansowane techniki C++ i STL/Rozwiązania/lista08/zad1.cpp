#include <iostream>
#include <regex>
#include <string>
#include <vector>

int main() {
    std::regex pattern("^([01]?[0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?$");
    std::vector<std::string> tests = {
        "14:17",      // Prawidłowe
        "09:15",      // Prawidłowe
        "23:37:08",   // Prawidłowe
        "14.16",      // Nieprawidłowe
        "19:5",       // Nieprawidłowe
        "21:32:07,10",// Nieprawidłowe
    };

    for (const auto& input : tests) {
        if (std::regex_match(input, pattern)) {
            std::cout << "Godzina " << input << " jest prawidłowa." << std::endl;
        } else {
            std::cout << "Goidzina " << input << " jest nieprawidłowa." << std::endl;
        }
    }

    return 0;
}
