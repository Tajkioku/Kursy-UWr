#include <iostream>
#include <filesystem>
#include <string>

namespace fs = std::filesystem;

int main() {
    try {
        std::string path;
        std::cout << "Podaj ścieżkę do katalogu: ";
        std::cin >> path;

        if (!fs::exists(path)) {
            std::cerr << "Podany katalog nie istnieje." << std::endl;
            return 1;
        }

        int total_size = 0;
        for (const auto& entry : fs::recursive_directory_iterator(path)) {
            if (fs::is_regular_file(entry)) {
                total_size += fs::file_size(entry);
            }
        }

        std::cout << "Suma rozmiarów plików: " << total_size << " bajtów" << std::endl;
    } catch (const fs::filesystem_error& e) {
        std::cerr << "Błąd plików: " << e.what() << std::endl;
    } catch (const std::exception& e) {
        std::cerr << "Błąd: " << e.what() << std::endl;
    }

    return 0;
}
