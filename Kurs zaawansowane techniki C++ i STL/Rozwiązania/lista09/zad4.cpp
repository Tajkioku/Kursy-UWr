#include <iostream>
#include <filesystem>
#include <vector>
#include <algorithm>
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

        fs::path canonicalPath = fs::canonical(path);
        std::cout << "Nazwa kanoniczna katalogu: " << canonicalPath << std::endl;

        std::vector<std::string> directories;
        std::vector<std::string> files;

        for (const auto& entry : fs::directory_iterator(canonicalPath)) {
            if (fs::is_directory(entry)) {
                directories.push_back(entry.path().filename().string());
            } else if (fs::is_regular_file(entry)) {
                files.push_back(entry.path().filename().string());
            }
        }

        std::sort(directories.begin(), directories.end());
        std::sort(files.begin(), files.end());

        std::cout << "Katalogi:" << std::endl;
        for (const auto& dir : directories) {
            std::cout << dir << std::endl;
        }

        std::cout << "Pliki:" << std::endl;
        for (const auto& file : files) {
            std::cout << file << std::endl;
        }

    } catch (const fs::filesystem_error& e) {
        std::cerr << "Błąd plików: " << e.what() << std::endl;
    } catch (const std::exception& e) {
        std::cerr << "Błąd: " << e.what() << std::endl;
    }

    return 0;
}
