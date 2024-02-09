#include <iostream>
#include <string>

using namespace std;

int main() {
    cout << "Podaj date w formacie DD-MM-RRRR: ";
    string data;
    cin >> data;

    // Dziele date wzgledem "-"
    size_t pos1 = data.find("-");
    size_t pos2 = data.rfind("-");
    
    if (pos1 == string::npos || pos2 == string::npos || pos1 == pos2) {
       cout << "Bledny format daty." << endl;
        return 1;
    }
    
    // Zamiana string -> int
    int dzien = stoi(data.substr(0, pos1));
    int miesiac = stoi(data.substr(pos1 + 1, pos2 - pos1 - 1));
    int rok = stoi(data.substr(pos2 + 1));

    string nazwaMiesiaca;
    switch (miesiac) {
        case 1:
            nazwaMiesiaca = "stycznia";
            break;
        case 2:
            nazwaMiesiaca = "lutego";
            break;
        case 3:
            nazwaMiesiaca = "marca";
            break;
        case 4:
            nazwaMiesiaca = "kwietnia";
            break;
        case 5:
            nazwaMiesiaca = "maja";
            break;
        case 6:
            nazwaMiesiaca = "czerwca";
            break;
        case 7:
            nazwaMiesiaca = "lipca";
            break;
        case 8:
            nazwaMiesiaca = "sierpnia";
            break;
        case 9:
            nazwaMiesiaca = "wrzesnia";
            break;
        case 10:
            nazwaMiesiaca = "pazdziernika";
            break;
        case 11:
            nazwaMiesiaca = "listopada";
            break;
        case 12:
            nazwaMiesiaca = "grudnia";
            break;
        default:
            cout << "Bledny numer miesiaca." << endl;
            return 0;
    }

    cout << dzien << " " << nazwaMiesiaca << " " << rok << endl;

    return 0;
}
