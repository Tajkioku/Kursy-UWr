#include <iostream>
#include <string>
#include <regex>

int main() {
    std::regex pattern("^[A-Z][a-z]*(\\s+[A-Z][a-z]*|\\-[A-Z][a-z]*)*$");
    std::vector<std::string> tests  = {
        // Prawidłowe
        "Wroclaw", "Bielsko-Biala", "Tarnowskie Gory", "Gdansk-Przymorze", "Maly Raj", "Nowa-Wies Wielka",       // Prawidłowe
        // Nieprawidłowe
        "WARSZAWA", "Zle mieso", "Zimna-Wodka-07"," Krakow"
    };

    for (const std::string &input : tests) {
        if (std::regex_match(input, pattern)) {
            std::cout << "Data " << input << " jest prawidłowa." << std::endl;
        } else {
            std::cout << "Data " << input << " jest nieprawidłowa." << std::endl;
        }
    }

    return 0;
}
