#include "manipulators.hpp"

std::ostream& streams::comma(std::ostream& os)
{
    os << ", ";
    return os;
}

std::ostream& streams::colon(std::ostream& os)
{
    os << ": ";
    return os;
}

std::ostream& streams::operator<<(std::ostream& os, const streams::index& index)
{
    os << "[";

    std::string number = std::to_string(index.x);
    int padding = index.w - (int)number.size();

    for (int i = 0; i < padding; i++)
        os << " ";
    
    os << number << "]";

    return os;
}

streams::index::index(int x, int w)
{
    if (w < 0)
    {
        throw std::invalid_argument("index: w < 0");
    }

    this->x = x;
    this->w = w;
}

std::istream& streams::operator>>(std::istream& is, const streams::ignore& ignore)
{
    char c;
    int count = 0;

    while (count < ignore.x && is.get(c) && c != '\n')
        count++;
    
    return is;
}

streams::ignore::ignore(int x)
{
    if (x < 0)
        throw std::invalid_argument("ignore: liczba znaków < 0");
    
    this->x = x;
}

std::istream& streams::clearline(std::istream& is)
{
    char c;
    
    while (is.get(c) && c != '\n')
    {
        // Do nothing
    }
    
    return is;
}
