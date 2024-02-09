#include <iostream>
#include <vector>
#include <thread>
#include <algorithm>

void merge(std::vector<int>& array, int left, int middle, int right) {
    std::vector<int> leftHalf(array.begin() + left, array.begin() + middle + 1);
    std::vector<int> rightHalf(array.begin() + middle + 1, array.begin() + right + 1);

    int leftIndex = 0, rightIndex = 0, mergedIndex = left;

    while (leftIndex < leftHalf.size() && rightIndex < rightHalf.size()) {
        if (leftHalf[leftIndex] < rightHalf[rightIndex]) {
            array[mergedIndex++] = leftHalf[leftIndex++];
        } else {
            array[mergedIndex++] = rightHalf[rightIndex++];
        }
    }

    while (leftIndex < leftHalf.size()) {
        array[mergedIndex++] = leftHalf[leftIndex++];
    }

    while (rightIndex < rightHalf.size()) {
        array[mergedIndex++] = rightHalf[rightIndex++];
    }
}

void mergeSort(std::vector<int>& array, int left, int right) {
    if (left < right) {
        int middle = left + (right - left) / 2;

        std::thread leftThread(mergeSort, std::ref(array), left, middle);
        std::thread rightThread(mergeSort, std::ref(array), middle + 1, right);

        leftThread.join();
        rightThread.join();

        merge(array, left, middle, right);
    }
}

int main() {
    std::vector<int> array = {12, 11, 13, 5, 6, 7, 4, 3, 2, 1};
    int n = array.size();

    mergeSort(array, 0, n - 1);

    std::cout << "Posortowana tablica: \n";
    for (auto i : array)
        std::cout << i << " ";

    return 0;
}
