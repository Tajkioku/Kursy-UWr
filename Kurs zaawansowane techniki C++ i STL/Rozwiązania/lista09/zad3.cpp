#include <iostream>
#include <filesystem>
#include <chrono>
#include <ctime>

namespace fs = std::filesystem;

void printFileInfo(const fs::path& path) {
    try {
        auto ftime = fs::last_write_time(path);
        // std::time_t cftime = decltype(ftime)::clock::to_time_t(ftime);
        auto sctp = std::chrono::time_point_cast<std::chrono::system_clock::duration>(
            ftime - fs::file_time_type::clock::now() + std::chrono::system_clock::now());
        std::time_t cftime = std::chrono::system_clock::to_time_t(sctp);

        std::cout << "Path: " << path << "\n";
        std::cout << "Canonical Path: " << fs::canonical(path) << "\n";
        std::cout << "Ostatnio modyfikowany: " << std::asctime(std::localtime(&cftime));

        if (fs::is_regular_file(path)) {
            std::cout << "Rozmiar: " << fs::file_size(path) << " bajtów\n";
        }
        std::cout << std::endl;

    } catch (const fs::filesystem_error& e) {
        std::cerr << e.what() << std::endl;
    }
}

int main(int argc, char* argv[]) {
    if (argc < 2) {
        std::cerr << "Error: " << argv[0] << std::endl;
        return 1;
    }

    for (int i = 1; i < argc; i++) {
        fs::path p(argv[i]);

        if (fs::exists(p)) {
            printFileInfo(p);
        } else {
            std::cout << "Plik nie istnieje: " << p << std::endl;
        }
    }

    return 0;
}
