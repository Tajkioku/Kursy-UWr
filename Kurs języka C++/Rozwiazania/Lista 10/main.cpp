#include "manipulators.hpp"
#include "test.hpp"

int main(int argc, char** argv)
{
//     std::cout << "Test clearline:" << std::endl;
//     std::string str;
//     std::cin >> streams::clearline >> str;
//     std::cout << str << std::endl;

    std::cout << "Test interpunkcji:" << std::endl;
     std::cout << streams::index(10, 2)
              << streams::comma
              << streams::colon
              << "test_1: "
              << std::endl;
    
    std::cout << "Test ignore:" << std::endl;
    std::string str1;
    std::cin >> streams::ignore(5) >> str1;

    std::cout << str1 << std::endl;
    test_manipulators("./test.txt");
    
    return 0;
}
