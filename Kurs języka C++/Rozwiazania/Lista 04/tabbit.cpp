#include "tabbit.hpp"

tab_bit::tab_bit(int rozm) 
{
    dl = (rozm + rozmiarSlowa - 1) / rozmiarSlowa;
    tab = new slowo[dl];
}

tab_bit::tab_bit(const tab_bit &tb) {
    dl = (tb.dl + rozmiarSlowa - 1) / rozmiarSlowa;
    tab = new slowo[dl];

    // skopiowanie zawartość z oryginalnej tablicy bitów
    std::memcpy(tab, tb.tab, dl * sizeof(slowo));
    dl = tb.dl;
}

tab_bit & tab_bit::operator = (const tab_bit &tb)
{
    if (this != &tb) // sprawdzenie czy nie przypisujemu do samego siebie
    {
        delete[] tab; 
        dl = tb.dl; 
        tab = new slowo[dl];
        for (size_t i = 0; i < dl; i++)
            tab[i] = tb.tab[i];
    }
    return *this;
}

tab_bit& tab_bit::operator=(tab_bit&& tb) 
{
    if (this != &tb) // sprawdzenie czy nie przypisujemu do samego siebie
    { 
        delete[] tab;
        tab = tb.tab;
        dl = tb.dl;
        tb.tab = nullptr;
        tb.dl = 0;
    }
    return *this; 
}

tab_bit::~tab_bit() 
{
    delete[] tab;
}

bool tab_bit::czytaj (int i) const
{
    return (tab[i / rozmiarSlowa] & (1 << (i % rozmiarSlowa))) != 0;
} 

void tab_bit::pisz (int i, bool b) 
{
    if (b) 
    {
        tab[i / rozmiarSlowa] |= (1 << (i % rozmiarSlowa));
    } 
    else 
    {
        tab[rozmiarSlowa / rozmiarSlowa] &= ~(1 << (i % rozmiarSlowa));
    }
}

 bool tab_bit::operator[](int i) const 
 {
    if (i < 0 || i >= rozmiarSlowa) 
        throw std::out_of_range("Indeks poza zakresem!");

    return (tab[i / rozmiarSlowa] & (1 << (i % rozmiarSlowa))) != 0;
}

inline int tab_bit::rozmiar () const
{
    return dl * rozmiarSlowa;
}

// operator OR bitowego
// tab_bit& tab_bit::operator | (const tab_bit &tb) const 
// {
//     if(dl != tb.dl)
//         throw std::out_of_range("Tablice maja rozne rozmiary!");

//     tab_bit result(dl);

//     for (size_t i = 0; i < dl; i++)
//         result.tab[i] = tab[i] | tb.tab[i];

//     return result;

// }

tab_bit& tab_bit::operator |= (const tab_bit &tb) 
{
    for (int i = 0; i < std::min(dl, tb.dl); i++)
        tab[i] |= tb.tab[i];

    return *this;
}

// operator AND bitowego
// tab_bit& tab_bit::operator & (const tab_bit &tb) const 
// {
//     if(dl != tb.dl)
//         throw std::out_of_range("Tablice maja rozne rozmiary!");

//     tab_bit result(dl);

//     for (size_t i = 0; i < dl; i++)
//         result.tab[i] = tab[i] & tb.tab[i];

//     return result;

// }

tab_bit& tab_bit::operator &= (const tab_bit &tb) 
{
    for (int i = 0; i < std::min(dl, tb.dl); i++)
        tab[i] &= tb.tab[i];
    
    return *this;
}

// operator XOR bitowego
// tab_bit& tab_bit::operator ^ (const tab_bit &tb) const 
// {
//     if(dl != tb.dl)
//         throw std::out_of_range("Tablice maja rozne rozmiary!");

//     tab_bit result(dl);

//     for (size_t i = 0; i < dl; i++)
//         result.tab[i] = tab[i] ^ tb.tab[i];

//     return result;

// }

tab_bit& tab_bit::operator ^= (const tab_bit &tb) 
{
    for (int i = 0; i < std::min(dl, tb.dl); i++)
        tab[i] ^= tb.tab[i];
        
    return *this;
}

// operator NOT bitowego
// tab_bit& tab_bit::operator ~ () const 
// {
//     tab_bit result(dl);

//     for (int i = 0; i < dl; i++) 
//         result.tab[i] = !result.tab[i];
    
//     return result;
// }

std::istream& operator >> (std::istream& we, tab_bit& tb)
{
    char c;
    for (int i = 0; i < tb.rozmiar(); i++) 
    {
        we >> c;
        tb.tab[i] = (c == '1');
    }
    return we;
}

std::ostream& operator << (std::ostream& wy, const tab_bit& tb)
{
    for (int i = 0; i < tb.rozmiar(); i++) 
    {
        wy << tb.czytaj(i);
    }
    return wy;
}