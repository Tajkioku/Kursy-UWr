#include <type_traits>
#include <iostream>

template <typename Source, typename Target>
void transfer_value(Source&& source, Target& target) {
    using DecayedSource = std::decay_t<Source>;

    // Czy zrodlo to wskaznik
    if constexpr (std::is_pointer_v<DecayedSource>) {
        using SourceValueType = std::remove_pointer_t<DecayedSource>;
        // Sprawdzam mozliwosc konwersji na docelowy typ
        if constexpr (std::is_convertible_v<SourceValueType, Target>) {
            target = *source;
        } else {
            // Gdy konwersja nie jest mozliwa
            std::cerr << "Konwersja z typu " << typeid(SourceValueType).name() 
                      << " do " << typeid(Target).name() << " jest niemozliwa." << std::endl;
        }
    } else {
        // Jezeli nie jest wskaznikiem sprawdzam mozliwosc konwersji
        if constexpr (std::is_convertible_v<DecayedSource, Target>) {
            target = source;
        } else {
            // Gdy konwersja nie jest mozliwa
            std::cerr << "Konwersja z typu " << typeid(DecayedSource).name() 
                      << " do " << typeid(Target).name() << " jest niemozliwa." << std::endl;
        }
    }
}

int main() {
    int y = 0;

    // Ze wskaznika do y
    int x = 5;
    int* px = &x;
    transfer_value(px, y);
    std::cout << "y = " << y << std::endl;

    // Wartosc do y
    int z = 10;
    transfer_value(z, y);  
    std::cout << "y = " << y << std::endl;

    return 0;
}
