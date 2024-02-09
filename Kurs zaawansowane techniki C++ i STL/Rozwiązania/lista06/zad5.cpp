#include <iostream>
#include <string>
#include <algorithm>

void printAllPermutations(std::string str) {
    std::sort(str.begin(), str.end());
    do {
        std::cout << str << std::endl;
    } while (std::next_permutation(str.begin(), str.end()));
}

int main() {
    std::string s = "abc";
    printAllPermutations(s);
    return 0;
}
