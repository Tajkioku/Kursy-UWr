#ifndef INPUT_HPP
#define INPUT_HPP

#include <string>
#include <fstream>

namespace streams {

class input
{
    std::ifstream is;

    friend auto operator>>(input& in, int& target);

public:
    input(std::string file_name);

    input(const input& in);
    auto operator=(const input& in);
    input(input&& in);
    auto operator=(input&& in);

    ~input();
};

class output
{
    std::ofstream os;

    friend auto operator<<(output& out, int source);

public:
    output(std::string file_name);
    
    output(const output& out);
    auto operator=(const output& out);
    output(output&& out);
    auto operator=(output&& out);

    ~output();
};

}

#endif /* input_hpp */
