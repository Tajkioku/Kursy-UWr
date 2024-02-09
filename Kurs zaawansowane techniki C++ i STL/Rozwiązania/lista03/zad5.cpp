#include <iostream>
#include <vector>
#include <chrono>
#include <random>

using Matrix = std::vector<std::vector<double>>;

Matrix createRandomMatrix(int size) {
    // Losowanie
    std::mt19937 gen(std::random_device{}());
    std::uniform_real_distribution<> dist(0.5, 2.0);

    // Generowanie
    Matrix matrix(size, std::vector<double>(size));
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            matrix[i][j] = dist(gen);
        }
    }
    return matrix;
}

Matrix multiplyMatrix(const Matrix& A, const Matrix& B) {
    int size = A.size();
    Matrix result(size, std::vector<double>(size));

    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                result[i][j] += A[i][k] * B[k][j];
            }
        }
    }

    return result;
}

int main() {
    Matrix matrix10 = createRandomMatrix(10);
    Matrix matrix100 = createRandomMatrix(100);
    Matrix matrix1000 = createRandomMatrix(1000);

    // 10 x 10
    int repeats = 100000;
    auto start = std::chrono::high_resolution_clock::now();
    for (int i = 0; i < repeats; i++) {
        matrix10 = multiplyMatrix(matrix10, matrix10);
    }
    auto end = std::chrono::high_resolution_clock::now();
    auto duration10 = std::chrono::duration_cast<std::chrono::microseconds>(end - start).count() / double(repeats);
    
    // 100 x 100
    start = std::chrono::high_resolution_clock::now();
    matrix100 = multiplyMatrix(matrix100, matrix100);
    end = std::chrono::high_resolution_clock::now();
    auto duration100 = std::chrono::duration_cast<std::chrono::microseconds>(end - start).count();

    // 1000 x 1000
    start = std::chrono::high_resolution_clock::now();
    matrix1000 = multiplyMatrix(matrix1000, matrix1000);
    end = std::chrono::high_resolution_clock::now();
    auto duration1000 = std::chrono::duration_cast<std::chrono::microseconds>(end - start).count();

    // Wypisywanie
    std::cout << "10x10: " << duration10 << " microseconds" << std::endl;
    std::cout << "100x100: " << duration100 << " microseconds" << std::endl;
    std::cout << "1000x1000: " << duration1000 << " microseconds" << std::endl;

    return 0;
}
