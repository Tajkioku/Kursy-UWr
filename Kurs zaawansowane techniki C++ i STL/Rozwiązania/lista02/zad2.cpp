#include <iostream>
#include <fstream>
#include <memory>

class line_writer {
public:
    line_writer(const std::string& filename) {
        file_stream = std::make_shared<std::ofstream>(filename);
        if (!file_stream->is_open()) {
            std::cerr << "Nie można otworzyć pliku." << std::endl;
        }
    }

    void writeLine(const std::string& line) {
        if (file_stream->is_open()) {
            *file_stream << line << std::endl;
        }
    }

    ~line_writer() {
        if (file_stream->is_open()) {
            file_stream->close();
        }
    }

private:
    std::shared_ptr<std::ofstream> file_stream;
};

int main() {
    std::string filename = "output.txt";
    std::shared_ptr<line_writer> writer = std::make_shared<line_writer>(filename);

    writer->writeLine("Pierwsza linia.");
    writer->writeLine("Druga linia.");

    // Zapisywanie danych za pomocą innych wskaźników
    std::shared_ptr<line_writer> writer2 = writer;
    std::shared_ptr<line_writer> writer3 = writer;
    writer2->writeLine("Trzecia linia.");
    writer3->writeLine("Czwarta linia.");

    return 0;
}
