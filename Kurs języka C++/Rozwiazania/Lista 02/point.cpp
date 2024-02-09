#include "point.hpp"

Point::Point()
{
    x = 0;
    y = 0;
}

Point::Point(double X, double Y)
{
    x = X;
    y = Y;
}

Point::Point(const Point& p)
{
    x = p.x;
    y = p.y;
}

double Point::getX()
{
    return x;
}

double Point::getY()
{
    return y;
}

void Point::setX(double value)
{
    x = value;
}

void Point::setY(double value)
{
    y = value;
}

void Point::translate(double dx, double dy)
{
    x = x + dx;
    y = y + dy;
}

void Point::rotate(double X, double Y, double theta)
{
    x = (x - X) * cos(theta) - (y - Y) * sin(theta) + X;
    y = (x - X) * sin(theta) + (y - Y) * cos(theta) + Y;
}

void Point::rotate(Point p, double theta)
{
    x = (x - p.x) * cos(theta) - (y - p.y) * sin(theta) + p.x;
    y = (x - p.x) * sin(theta) + (y - p.y) * cos(theta) + p.y;
}

std::ostream& operator<< (std::ostream& out, const Point& p)
{
    return out << "(" << p.x << ", " << p.y << ")";
}

Point& Point::operator= (const Point& p)
{
    x = p.x;
    y = p.y;
    return *this;
}