#include "test.hpp"
#include "manipulators.hpp"
#include "input.hpp"
#include <fstream>
#include <iostream>
#include <algorithm>

line_struct load_file(std::string file_name)
{
    std::ifstream file;
    file.open(file_name);

    line_struct lines;

    if (file.is_open())
    {
        int count = 1;
        std::string line;

        while (file.good())
        {
            file >> line;
            lines.push_back(std::make_pair(count, line));
            count++;
        }
    }
    else
    {
        std::cerr << "Nie mozna otworzyć pliku" << file_name << std::endl;
        exit(1);
    }

    return lines;
}

bool comp(std::pair<int, std::string> a, std::pair<int, std::string> b)
{
    return a.second < b.second;
}

void test_manipulators(std::string file_name)
{
    auto lines = load_file(file_name);

    const int line_length = (int)std::to_string(lines.size()).size();

    std::sort(lines.begin(), lines.end(), comp);

    for (auto line : lines)
        std::cout << streams::index(line.first, line_length) << " " << line.second << std::endl;
}
