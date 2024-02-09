#include <iostream>
#include <deque>
#include <string>
#include <tuple>
#include <algorithm>

struct Osoba {
    std::string nazwisko;
    std::string imie;
    int rok_urodzenia;

    Osoba (const std::string& nazwisko, const std::string& imie, int rok_urodzenia)
        : nazwisko(nazwisko), imie(imie), rok_urodzenia(rok_urodzenia) {}

    friend bool operator< (const Osoba& osoba1, const Osoba& osoba2) {
        return std::tie(osoba1.nazwisko, osoba1.imie, osoba1.rok_urodzenia) <
               std::tie(osoba2.nazwisko, osoba2.imie, osoba2.rok_urodzenia);
    }
};

int main() {
    std::deque<Osoba> ludzie;
    ludzie.emplace_back("Kowalski", "Jan", 1980);
    ludzie.emplace_back("Nowak", "Bartosz", 1990);
    ludzie.emplace_back("Nowak", "Bartosz", 1989);
    ludzie.emplace_back("Kowalski", "Marcin", 1975);
    ludzie.emplace_back("Nowak", "Jan", 1995);

    std::sort(ludzie.begin(), ludzie.end());

    std::cout << "Posortowane osoby:" << std::endl;
    for (auto osoba : ludzie)
        std::cout << osoba.nazwisko << " " << osoba.imie << " " << osoba.rok_urodzenia << "" << std::endl;

    return 0;
}
