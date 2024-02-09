#include "triangle.hpp"

Triangle::Triangle()
{
    a.setX(0);
    a.setY(1);
    b.setX(0);
    b.setY(0);
    c.setX(1);
    c.setY(0);
}

Triangle::Triangle(double x1, double y1, double x2, double y2, double x3, double y3)
{
    if(x1 == x2 && x2 == x3 && y1 == y2 && y2 == y3)
        throw std::invalid_argument("Nie można utworzyć trojkata z bokami o długości 0.\n");

    if((y3 - y2) * (x2 - x1) == (y2 - y1) * (x3 - x2))
        throw std::invalid_argument("Punkty sa wspoliniowe.\n");
    else
    {
        a.setX(x1);
        a.setY(y1);
        b.setX(x2);
        b.setY(y2);
        c.setX(x3);
        c.setY(y3);
    }
}

Triangle::Triangle(Point p1, Point p2, Point p3)
{
    if(p1.getX() == p2.getX() && p2.getX() == p3.getX() && p1.getY() == p2.getY() && p2.getY() == p3.getY())
        throw std::invalid_argument("Nie można utworzyć trojkata z bokami o długości 0.\n");

    if((p3.getY() - p2.getY()) * (p2.getX() - p1.getX()) == (p2.getY() - p1.getY()) * (p3.getX() - p2.getX()))
        throw std::invalid_argument("Punkty sa wspoliniowe.\n");
    else
    {
        a = p1;
        b = p2;
        c = p3;
    }
}

Triangle::Triangle(const Triangle& t)
{
    a = t.a;
    b = t.b;
    c = t.c;
}

Point Triangle::getA()
{
    return a;
}

Point Triangle::getB()
{
    return b;
}

Point Triangle::getC()
{
    return c;
}

void Triangle::translate(double dx, double dy)
{
    a.setX(a.getX() + dx);
    a.setY(a.getY() + dy);
    b.setX(b.getX() + dx);
    b.setY(b.getY() + dy);
    c.setX(c.getX() + dx);
    c.setY(c.getY() + dy);
}

void Triangle::rotate(double bx, double by, double theta)
{
    a.setX((a.getX() - bx) * cos(theta) - (a.getY() - by) * sin(theta) + bx);
    a.setY((a.getX() - bx) * sin(theta) + (a.getY() - by) * cos(theta) + by);

    b.setX((b.getX() - bx) * cos(theta) - (b.getY() - by) * sin(theta) + bx);
    b.setY((b.getX() - bx) * sin(theta) + (b.getY() - by) * cos(theta) + by);

    c.setX((c.getX() - bx) * cos(theta) - (c.getY() - by) * sin(theta) + bx);
    c.setY((c.getX() - bx) * sin(theta) + (c.getY() - by) * cos(theta) + by);
}

void Triangle::rotate(Point p, double theta)
{
    a.setX((a.getX() - p.getX()) * cos(theta) - (a.getY() - p.getY()) * sin(theta) + p.getX());
    a.setY((a.getX() - p.getX()) * sin(theta) + (a.getY() - p.getY()) * cos(theta) + p.getY());

    b.setX((b.getX() - p.getX()) * cos(theta) - (b.getY() - p.getY()) * sin(theta) + p.getX());
    b.setY((b.getX() - p.getX()) * sin(theta) + (b.getY() - p.getY()) * cos(theta) + p.getY());

    c.setX((c.getX() - p.getX()) * cos(theta) - (c.getY() - p.getY()) * sin(theta) + p.getX());
    c.setY((c.getX() - p.getX()) * sin(theta) + (c.getY() - p.getY()) * cos(theta) + p.getY());
}

double distance(Point a, Point b)
{
    double dx = b.getX() - a.getX();
    double dy = b.getY() - a.getY();

    return sqrt(pow(dx, 2) + pow(dy, 2));
}

double Triangle::perimeter()
{
    return distance(a, b) + distance(b, c) + distance(a, c);
    
}

double Triangle::area()
{
    double A = distance(a, b);
    double B = distance(b, c);
    double C = distance(a, c);
    double S = (A + B + C) / 2;

    return sqrt(S * (S - A) * (S - B) * (S - C));
}

double areaByPoints(Point a, Point b, Point c)
{
    double A = distance(a, b);
    double B = distance(b, c);
    double C = distance(a, c);
    double S = (A + B + C) / 2;

    return sqrt(S * (S - A) * (S - B) * (S - C));
}

bool Triangle::isInside(Point p)
{
    double T = area();
    double T1 = areaByPoints(a, b, p);
    double T2 = areaByPoints(a, c, p);
    double T3 = areaByPoints(b, c, p);

    if(std::abs(T - (T1 + T2 + T3)) < 1e-12)
        return true;
    else
        return false;
}

std::ostream& operator<<(std::ostream& out, const Triangle& t)
{
    return out << "A" << t.a << ", B " << t.b << ", C" << t.c;
}