#ifndef TABBIT_HPP
#define TABBIT_HPP

#include <iostream>
#include <algorithm>

class tab_bit
{
        typedef uint32_t slowo;
        static const int rozmiarSlowa = 32;
        // class ref; // klasa pomocnicza do adresowania bitów

    protected:
        int dl;
        slowo *tab;
    public:
        explicit tab_bit (int rozm); // wyzerowana tablica bitow [0...rozm]
        // explicit tab_bit (slowo tb); // tablica bitów [0...rozmiarSlowa]
                                     // zainicjalizowana wzorcem
        tab_bit (const tab_bit &tb); // konstruktor kopiujący
        tab_bit (tab_bit &&tb); // konstruktor przenoszący
        tab_bit & operator = (const tab_bit &tb); // przypisanie kopiujące
        tab_bit & operator = (tab_bit &&tb); // przypisanie przenoszące
        ~tab_bit (); // destruktor

        bool czytaj (int i) const;
        void pisz (int i, bool b); 
    
        bool operator[] (int i) const;
        // ref operator[] (int i); // indeksowanie dla zwykłych tablic bitowych
        inline int rozmiar () const;
     
        // tab_bit& operator | (const tab_bit &tb) const;
        tab_bit& operator |= (const tab_bit &tb);
        // tab_bit& operator & (const tab_bit &tb) const;
        tab_bit& operator &= (const tab_bit &tb);
        // tab_bit& operator ^ (const tab_bit &tb) const;
        tab_bit& operator ^= (const tab_bit &tb);
        // tab_bit operator ~ () const {
        
        friend std::istream & operator >> (std::istream &we, tab_bit &tb);
        friend std::ostream & operator << (std::ostream &wy, const tab_bit &tb);
};

#endif