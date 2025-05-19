#include "number.hpp"

Number::Number() : Number(0) {}

Number::Number(double x)
{
    value = x; 
    history = new double[max_history_size];
}

Number::Number(Number &&N)
{
    value = N.value;
    history = N.history;
    N.value = 0;
    N.history = nullptr;
}

Number::Number(const Number &N)
{
    value = N.value;
    history = new double[max_history_size];
}

Number::~Number() 
{
    delete[] history;
}

Number& Number::operator=(const Number& N)
{
    value = N.value;
    history = N.history;
    return *this;
}

Number& Number::operator=(Number&& N)
{
    value = N.value;
    history = N.history;
    N.value = 0;
    N.history = nullptr;
    return *this;
}

double Number::get_value() 
{
    return value;
}

void Number::set_value(double x) 
{
    if(history_size == max_history_size) 
    {
        for(int i = 0; i < max_history_size - 1; i++) 
        {
            history[i] = history[i+1];
        }
        history_size--;
    }
    history[history_size++] = value;
    value = x;
}

double Number::get_previous_value() 
{
    if(history_size == 0) 
    {
        std::cerr << "Historia jest pusta!" << std::endl;
        return value;
    }
    double previous_value = history[--history_size];
    history[history_size] = 0;
    return previous_value;
}