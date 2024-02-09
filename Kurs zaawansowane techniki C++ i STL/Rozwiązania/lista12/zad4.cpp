#include <iostream>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <vector>
#include <queue>
#include <chrono>
#include <random>
#include <atomic>

class Buffer {
public:
    Buffer(int maxSize) : maxSize(maxSize) {}

    void add(int product) {
        std::unique_lock<std::mutex> lock(mtx);
        cond.wait(lock, [this]() { return buffer.size() < maxSize; });
        buffer.push(product);
        cond.notify_all();
    }

    int remove() {
        std::unique_lock<std::mutex> lock(mtx);
        cond.wait(lock, [this]() { return !buffer.empty(); });
        int product = buffer.front();
        buffer.pop();
        cond.notify_all();
        return product;
    }

private:
    std::queue<int> buffer;
    std::mutex mtx;
    std::condition_variable cond;
    int maxSize;
};

void producer(Buffer& buffer, int id) {
    std::random_device rd;
    std::mt19937 rng(rd());
    std::uniform_int_distribution<int> uni(1000, 5000);

    for (int i = 0; i < 5; ++i) {
        buffer.add(i);
        std::cout << "Producent " << id << " wyprodukowal produkt " << i << std::endl;
        auto sleepDuration = uni(rng);
        std::this_thread::sleep_for(std::chrono::milliseconds(sleepDuration));
        // std::this_thread::sleep_for(std::chrono::milliseconds(100));
    }
}

void consumer(Buffer& buffer, int id) {
    std::random_device rd;
    std::mt19937 rng(rd());
    std::uniform_int_distribution<int> uni(1000, 5000);

    for (int i = 0; i < 5; ++i) {
        int product = buffer.remove();
        std::cout << "Konsument " << id << " skonsumowal produkt " << product << std::endl;
        auto sleepDuration = uni(rng);
        std::this_thread::sleep_for(std::chrono::milliseconds(sleepDuration));
        // std::this_thread::sleep_for(std::chrono::milliseconds(100));
    }
}

int main() {
    Buffer buffer(3);
    std::vector<std::thread> producers;
    std::vector<std::thread> consumers;

    for (int i = 0; i < 3; ++i) {
        producers.push_back(std::thread(producer, std::ref(buffer), i));
        consumers.push_back(std::thread(consumer, std::ref(buffer), i));
    }

    for (auto& producer : producers) {
        producer.join();
    }

    for (auto& consumer : consumers) {
        consumer.join();
    }

    return 0;
}
