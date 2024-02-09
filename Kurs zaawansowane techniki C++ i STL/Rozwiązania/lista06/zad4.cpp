#include <iostream>
#include <fstream>
#include <map>
#include <cctype>
#include <iterator>
#include <iomanip>

int main(int argc, char* argv[]) {
    
    std::ifstream file(argv[1]);
    if (!file.is_open()) {
        std::cerr << "Nie mozna otworzyc pliku." << argv[1] << std::endl;
        return 1;
    }

    // Histogram
    std::map<char, int> frequency;
    int totalLetters = 0;
    // Iterator do czytania znakow z pliku
    std::istream_iterator<char> it(file), end;

    for (; it != end; it++) {
        char c = std::tolower(*it);
        if (std::isalpha(c)) {
            frequency[c]++;
            totalLetters++;
        }
    }

    file.close();

    // Dane histogramu
    std::cout << "Histogram liter:" << std::endl;
    for (const auto& pair : frequency) {
        double percentage = 100.0 * pair.second / totalLetters;
        std::cout << pair.first << ": " << std::fixed << std::setprecision(2) << percentage << "%" << std::endl;
    }

    return 0;
}
