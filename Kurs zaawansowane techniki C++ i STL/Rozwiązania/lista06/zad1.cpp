#include <iostream>
#include <deque>
#include <algorithm>
#include <string>
#include <random>

class Person {
public:
    std::string firstName;
    std::string lastName;
    int age;
    double weight;
    double height;

    Person(std::string fn, std::string ln, int a, double w, double h) 
        : firstName(fn), lastName(ln), age(a), weight(w), height(h) {}

    double bmi() const {
        return weight / (height * height);
    }
};

int main() {
    std::deque<Person> people = {
        {"Tomasz", "Wozniak", 50, 150, 1.65},
        {"Michal", "Nowak", 30, 130, 1.83},
        {"Barbara", "Kaminski", 38, 58, 1.89},
        {"Katarzyna", "Nowak", 19, 61, 1.77},
        {"Pawel", "Kaminski", 29, 82, 1.78},
        {"Anna", "Wozniak", 60, 65, 1.85},
        {"Katarzyna", "Wisniewski", 39, 125, 1.61},
        {"Agnieszka", "Nowak", 58, 95, 1.97},
        {"Barbara", "Szymanski", 60, 91, 1.78},
        {"Malgorzata", "Nowak", 46, 55, 2.0},
        {"Katarzyna", "Lewandowski", 54, 103, 1.7},
        {"Tomasz", "Zielinski", 29, 80, 1.52}
    };

    // Sortowanie wedlug BMI
    std::sort(people.begin(), people.end(), [](const Person& a, const Person& b) {
        return a.bmi() < b.bmi();
    });

    std::cout << "Posortowane osoby:" << std::endl;
    for (const auto& person : people) {
        std::cout << person.firstName << " " << person.lastName << " - BMI: " << person.bmi() << std::endl;
    }

    // Modyfikacja wagi
    std::for_each(people.begin(), people.end(), [](Person& p) {
        p.weight *= 0.9;
    });

    std::cout << "\nPo zmianie wagi:" << std::endl;
    for (const auto& person : people) {
        std::cout << person.firstName << " " << person.lastName << " - Waga: " << person.weight << std::endl;
    }

    // Podzial na grupy wedlug wagi
    std::deque<Person> heavy, light;
    std::partition_copy(people.begin(), people.end(), std::back_inserter(heavy), std::back_inserter(light), [](const Person& p) {
        return p.weight > 100;
    });

    std::cout << "\nOsoby ciezkie:" << std::endl;
    for (const auto& person : heavy) {
        std::cout << person.firstName << " " << person.lastName << std::endl;
    }

    std::cout << "Osoby lekkie:" << std::endl;
    for (const auto& person : light) {
        std::cout << person.firstName << " " << person.lastName << std::endl;
    }

    // Ustawienie osoby o srodkowym wzroscie na pozycji 5
    std::nth_element(people.begin(), people.begin() + 4, people.end(), [](const Person& a, const Person& b) {
        return a.height < b.height;
    });

    std::cout << "\nPo ustawieniu osoby o srodkowym wzroscie na 5. pozycji:" << std::endl;
    for (const auto& person : people) {
        std::cout << person.firstName << " " << person.lastName << std::endl;
    }

    // Losowe przemieszanie osob
    std::random_device rd;
    std::mt19937 g(rd());

    std::shuffle(people.begin(), people.begin() + 5, g);
    std::shuffle(people.end() - 5, people.end(), g);

    std::cout << "\nPo losowym przemieszaniu:" << std::endl;
    for (const auto& person : people) {
        std::cout << person.firstName << " " << person.lastName << std::endl;
    }

    // Najstarsza i najmlodsza osoba
    auto oldest = std::max_element(people.begin(), people.end(), [](const Person& a, const Person& b) {
        return a.age < b.age;
    });

    auto youngest = std::min_element(people.begin(), people.end(), [](const Person& a, const Person& b) {
        return a.age < b.age;
    });

    std::cout << "\nNajstarsza osoba: " << oldest->firstName << " " << oldest->lastName << std::endl;
    std::cout << "Najmlodsza osoba: " << youngest->firstName << " " << youngest->lastName << std::endl;

    return 0;
}

