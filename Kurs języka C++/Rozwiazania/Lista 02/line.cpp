#include "line.hpp"

Line::Line()
{
    a.setX(0);
    a.setY(0);
    b.setX(1);
    b.setY(1);
}

Line::Line(double x1, double y1, double x2, double y2)
{
    if (x1 == x2 && y1 == y2)
        throw std::invalid_argument("Nie można utworzyć odcinka o długości 0.\n");
    else
    {
        a.setX(x1);
        a.setY(y1);
        b.setX(x2);
        b.setY(y2);
    }
}

Line::Line(Point A, Point B)
{
    if (A.getX() == B.getX() && A.getY() == B.getY())
        throw std::invalid_argument("Nie można utworzyć odcinka o długości 0.\n");
    else
    {
        a = A;
        b = B;
    }
}

Line::Line(const Line& l)
{
    a = l.a;
    b = l.b;
}

Point Line::getA()
{
    return a;
}

Point Line::getB()
{
    return b;
}

void Line::translate(double dx, double dy)
{
    a.setX(a.getX() + dx);
    a.setY(a.getY() + dy);
    b.setX(b.getX() + dx);
    b.setY(b.getY() + dy);
}

void Line::rotate(double bx, double by, double theta)
{
    a.setX((a.getX() - bx) * cos(theta) - (a.getY() - by) * sin(theta) + bx);
    a.setY((a.getX() - bx) * sin(theta) + (a.getY() - by) * cos(theta) + by);

    b.setX((b.getX() - bx) * cos(theta) - (b.getY() - by) * sin(theta) + bx);
    b.setY((b.getX() - bx) * sin(theta) + (b.getY() - by) * cos(theta) + by);
}

void Line::rotate(Point p, double theta)
{
    a.setX((a.getX() - p.getX()) * cos(theta) - (a.getY() - p.getY()) * sin(theta) + p.getX());
    a.setY((a.getX() - p.getX()) * sin(theta) + (a.getY() - p.getY()) * cos(theta) + p.getY());

    b.setX((b.getX() - p.getX()) * cos(theta) - (b.getY() - p.getY()) * sin(theta) + p.getX());
    b.setY((b.getX() - p.getX()) * sin(theta) + (b.getY() - p.getY()) * cos(theta) + p.getY());
}

double Line::length()
{
    double dx = b.getX() - a.getX();
    double dy = b.getY() - a.getY();

    return sqrt(pow(dx, 2) + pow(dy, 2));
}

bool Line::isInside(Point p)
{

    if ((p.getX() >= a.getX() && p.getY() >= a.getY() && p.getX() <= b.getX() && p.getY() <= b.getY()) ||
        (p.getX() >= b.getX() && p.getY() >= b.getY() && p.getX() <= a.getX() && p.getY() <= a.getY()))
        return true;
    else
        return false;
}

std::ostream& operator<<(std::ostream& out, const Line& A)
{
    return out << "A" << A.a << ", B" << A.b;
}