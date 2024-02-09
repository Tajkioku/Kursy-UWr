#ifndef POINT_HPP
#define POINT_HPP

#include <iostream>
#include <cmath>

class Point
{
    private:
        double x, y;

    public:
        Point();
        Point(double X, double Y);
        Point(const Point& p);

        double getX();
        double getY();
        void setX(double value);
        void setY(double value);

        void translate(double dx, double dy);
        void rotate(double bx, double by, double theta);
        void rotate(Point p, double theta);

        friend std::ostream& operator<<(std::ostream& out, const Point& p);
        Point& operator=(const Point &p);
};

#endif