#include <iostream>
#include <vector>
#include <list>
#include <set>
#include <algorithm>

class SummationFunctor {
    public:
        SummationFunctor() : sum(0), count(0), average(0) {}

        template<typename T>
        void operator()(const T& element) {
            sum += element;
            count++;
            average = static_cast<double>(sum) / count;
            std::cout << "Element: " << element << ", Sum: " << sum << ", Count: " << count << ", Average: " << average << std::endl;
        }

    private:
        double sum;
        int count;
        double average;
};

int main() {
    std::set<int> intSet = { 1, 2, 3, 4, 5 };
    std::list<float> floatList = { 1.5, 2.5, 3.5, 4.5, 5.5 };
    std::vector<double> doubleVector = { 1.2, 2.2, 3.2, 4.2, 5.2 };

    SummationFunctor functor;

    std::cout << "=== Set ===" << std::endl;
    std::for_each(intSet.begin(), intSet.end(), functor);

    std::cout << "=== List ===" << std::endl;
    std::for_each(floatList.begin(), floatList.end(), functor);

    std::cout << "=== Vector ===" << std::endl;
    std::for_each(doubleVector.begin(), doubleVector.end(), functor);

    return 0;
}
