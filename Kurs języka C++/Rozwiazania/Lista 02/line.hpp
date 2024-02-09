#ifndef LINE_HPP
#define LINE_HPP

#include <iostream>
#include <cmath>
#include <exception>
#include "point.hpp"

class Line
{
    private:
        Point a, b;

    public:
        Line();
        Line(double x1, double y1, double x2, double y2);
        Line(Point p1, Point p2);
        Line(const Line& l);

        Point getA();
        Point getB();

        void translate(double dx, double dy);
        void rotate(double bx, double by, double theta);
        void rotate(Point p, double theta);

        double length();
        bool isInside(Point p);

        friend std::ostream& operator<<(std::ostream& out, const Line& A);
};

#endif