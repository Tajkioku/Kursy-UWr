#ifndef TRIANGLE_HPP
#define TRIANGLE_HPP

#include <iostream>
#include <cmath>
#include <exception>
#include "point.hpp"

class Triangle
{
    private:
        Point a, b, c;

    public:
        Triangle();
        Triangle(double x1, double y1, double x2, double y2, double x3, double y3);
        Triangle(Point p1, Point p2, Point p3);
        Triangle(const Triangle& t);

        Point getA();
        Point getB();
        Point getC();

        void translate(double dx, double dy);
        void rotate(double bx, double by, double theta);
        void rotate(Point p, double theta);

        double area();
        double perimeter();
        bool isInside(Point p);

        friend std::ostream& operator<<(std::ostream& out, const Triangle& t);
};

#endif 
