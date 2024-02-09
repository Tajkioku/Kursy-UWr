#include <iostream>
#include <vector>
#include <thread>
#include <chrono>
#include <random>
#include <atomic>
#include <sstream>

void threadFunction(std::atomic<bool>& running, const std::string& threadName) {
    std::random_device rd;
    std::mt19937 rng(rd());
    std::uniform_int_distribution<int> uni(500, 1000);

    while (running) {
        auto sleepDuration = uni(rng);

        std::cout << threadName << std::endl;

        // Czekanie
        std::this_thread::sleep_for(std::chrono::milliseconds(sleepDuration));
    }
}

int main() {
    std::atomic<bool> running(true);
    std::vector<std::thread> threads;

    // Tworzenie i uruchamianie wątków
    for (int i = 0; i < 5; ++i) {
        std::stringstream ss;
        ss << "Thread " << i;
        threads.emplace_back(threadFunction, std::ref(running), ss.str());
    }

    // Czekanie 1 minute
    std::this_thread::sleep_for(std::chrono::minutes(1));
    running = false;

    // Czekanie na zakończenie wątkow
    for (auto& thread : threads) {
        thread.join();
    }
    std::cout << "koniec";

    return 0;
}
