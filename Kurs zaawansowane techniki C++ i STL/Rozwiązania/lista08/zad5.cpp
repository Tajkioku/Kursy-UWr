#include <iostream>
#include <fstream>
#include <string>
#include <regex>

int main() {
    std::string filePath = "url.html";

    std::ifstream file(filePath);
    if (!file) {
        std::cerr << "Nie można otworzyć pliku: " << filePath << std::endl;
        return 1;
    }

    std::regex href_regex(R"<a\s+(?:[^>]*?\s+)?href="([^"]*)"")
    std::smatch matches;
    std::string line;

    while (std::getline(file, line)) {
        std::sregex_iterator currentMatch(line.begin(), line.end(), href_regex);
        std::sregex_iterator lastMatch;

        while (currentMatch != lastMatch) {
            std::smatch match = *currentMatch;
            std::cout << match[1] << std::endl; // match[1] zawiera wartość href
            currentMatch++;
        }
    }

    file.close();
    return 0;
}