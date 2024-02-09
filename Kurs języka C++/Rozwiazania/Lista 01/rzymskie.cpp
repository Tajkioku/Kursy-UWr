#include <iostream>
#include <string>
#include <vector>

using namespace std;

const vector < pair <int, string> > rzymskie = {
    {1000, "M"}, {900, "CM"}, {500, "D"}, {400, "CD"},
    {100, "C"}, {90, "XC"}, {50, "L"}, {40, "XL"},
    {10, "X"}, {9, "IX"}, {5, "V"}, {4, "IV"}, {1, "I"}
};

string bin2rzym(int x) {
    string ans;
    for (const auto &p : rzymskie) {
        while (x >= p.first) {
            ans += p.second;
            x -= p.first;
        }
    }
    return ans;
}

int main(int argc, char* argv[]) {
    for (int i = 1; i < argc; i++) {
        try {
            int x = stoi(argv[i]);
            if (x >= 1 && x <= 3999) {
                cout << bin2rzym(x) << endl;
            } else {
                clog << "Blad: " << x << " jest poza zakresem od 1 do 3999" << endl;
            }
        } catch (invalid_argument&) {
            clog << "Blad: " << argv[i] << " nie jest liczba calkowita" << endl;
        } catch (out_of_range&) {
            clog << "Blad: " << argv[i] << " jest poza zakresem int" << endl;
        }
    }

    return 0;
}
