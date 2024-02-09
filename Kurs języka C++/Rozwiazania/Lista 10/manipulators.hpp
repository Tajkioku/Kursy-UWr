#ifndef MANIPULATORS_HPP
#define MANIPULATORS_HPP

#include <iostream>

namespace streams {

std::ostream& comma(std::ostream& os);
std::ostream& colon(std::ostream& os);
std::istream& clearline(std::istream& is);

class index
{
    int x;
    int w;

    public:
        index(int x, int w);
    
    friend std::ostream& operator<<(std::ostream& os, const index& idx);
};

class ignore
{
    int x;

    public:
        ignore(int x);
    
    friend std::istream& operator>>(std::istream& is, const ignore& ign);
};

std::ostream& operator<<(std::ostream& os, const index& idx);
std::istream& operator>>(std::istream& is, const ignore& ign);

}

#endif /* manipulators_hpp */
