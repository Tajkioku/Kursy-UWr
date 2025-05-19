#ifndef NUMBER_HPP
#define NUMBER_HPP

#include <iostream>

class Number
{
    private:
        double value;
        double* history;
        int history_size = 0;
        static const int max_history_size = 3;

    public:
        Number();
        Number(double x);
        Number(Number&& N);
        Number(const Number& N);
        ~Number();

        Number& operator=(const Number& N);
        Number& operator=(Number&& N);

        double get_value();
        void set_value(double x);
        double get_previous_value();
};

#endif