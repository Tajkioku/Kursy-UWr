#include <vector>
#include <unordered_map>
#include <algorithm>
#include <iostream>

std::vector<std::pair<int, int>> findMostFrequentElements(const std::vector<int>& data) {
    std::unordered_map<int, int> frequencyMap;
    int maxFrequency = 0;

    // Zliczanie czestotliwosci kazdego elementu
    for (int num : data) {
        maxFrequency = std::max(maxFrequency, ++frequencyMap[num]);
    }

    // Znalezienie wszystkich elementow o maksymalnej czestotliwosci
    std::vector<std::pair<int, int>> result;
    for (auto x : frequencyMap) {
        if (x.second == maxFrequency) {
            result.emplace_back(x.first, x.second);
        }
    }

    return result;
}

int main() {
    std::vector<int> tab = {1, 1, 3, 5, 8, 9, 5, 8, 8, 5};
    auto result = findMostFrequentElements(tab);

    for (auto x : result) {
        std::cout << "{" << x.first << ", " << x.second << "} ";
    }

    return 0;
}
