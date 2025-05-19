#ifndef TEST_HPP
#define TEST_HPP

#include <string>
#include <vector>

using line_struct = std::vector <std::pair <int, std::string> >;

line_struct load_file(std::string file_name);
void test_manipulators(std::string file_name);

#endif /* test_hpp */
