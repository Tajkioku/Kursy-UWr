#include <iostream>
#include <vector>
#include <functional>

int main() {
    std::function<int(int, int)> lambda = [&](int n, int k) -> int {
        if (k == 0 || k == n) {
            return 1;
        } else if (k < 0 || n < k) {
            return 0;
        } else {
            return lambda(n - 1, k - 1) + lambda(n - 1, k);
        }
    };

    std::vector<std::pair<int, int>> test = {
        {5, 2},
        {7, 3},
        {10, 5},
        {10, -1},
        {-10, 1}
    };

    for (auto p : test) {
        std::cout << "Lambda(" << p.first << ", " << p.second << ") = " << lambda(p.first, p.second) << std::endl;
    }

    return 0;
}
