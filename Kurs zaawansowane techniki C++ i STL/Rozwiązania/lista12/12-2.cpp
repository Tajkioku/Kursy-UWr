#include <iostream>
#include <thread>
#include <chrono>

void timer() {
    for (int i = 10; i > 0; --i) {
        if (i == 5 || i == 3 || i == 1) {
            std::cout << "Program zakończy się za " << i << " sekund(y)" << std::endl;
        }
        std::this_thread::sleep_for(std::chrono::seconds(1));
    }
}

int main() {
    std::thread timerThread(timer);

    // Czekanie na zakończenie wątku
    timerThread.join();

    std::cout << "Koniec programu" << std::endl;
    return 0;
}
