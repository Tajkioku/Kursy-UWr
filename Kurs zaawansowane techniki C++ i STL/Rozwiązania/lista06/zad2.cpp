#include <iostream>
#include <list>
#include <algorithm>
#include <string>

class Point {
public:
    int x, y;
    int r, g, b;
    std::string name;

    Point(int x, int y, int r, int g, int b, std::string name) 
        : x(x), y(y), r(r), g(b), b(b), name(name) {}

    double luminance() const {
        return 0.3 * r + 0.59 * g + 0.11 * b;
    }
};

int main() {
    std::list<Point> points = {
        Point(1, 2, 255, 0, 0, "Red1"),
        Point(-1, 3, 0, 255, 0, "Green1"),
        Point(4, -4, 0, 0, 255, "Blue1"),
        Point(3, 3, 125, 125, 0, "Yellow1"),
        Point(-3, -3, 255, 255, 255, "White1"),
        Point(2, -2, 0, 0, 0, "Black"),
        Point(-2, 2, 128, 0, 128, "Purple1"),
        Point(5, 5, 255, 165, 0, "Orange1"),
        Point(-5, -5, 192, 192, 192, "Grey1"),
        Point(6, -6, 128, 128, 128, "Grey2"),
        Point(-6, 6, 0, 255, 255, "Cyan1"),
        Point(7, 7, 255, 20, 147, "Pink1"),
        Point(-7, -7, 255, 105, 180, "Pink2"),
        Point(8, -8, 0, 100, 0, "Green2"),
        Point(-8, 8, 135, 206, 235, "SkyBlue1"),
        Point(9, 9, 255, 215, 0, "Gold1"),
        Point(-9, -9, 218, 165, 32, "Gold2"),
        Point(10, -10, 64, 224, 208, "Turq1"),
        Point(-10, 10, 245, 222, 179, "Wheat1"),
        Point(11, 11, 255, 0, 255, "Magenta1"),
        Point(-11, -11, 255, 228, 196, "Bisque1"),
        Point(12, -12, 124, 252, 0, "LawnGreen1"),
        Point(-12, 12, 173, 255, 47, "GreenYellow1")
    };

    // Usun punkty o nazwie dluzszej niz 5 znakow
    points.remove_if([](const Point& p) { return p.name.length() > 5; });

    std::cout << "Punkty po usuniciu:" << std::endl;
    for (const auto& p : points) {
        std::cout << p.name << " ";
    }
    std::cout << std::endl;

    // Policz punkty w kazdej cwiartce
    int counts[4] = {0};
    for (const auto& p : points) {
        if (p.x > 0 && p.y > 0) counts[0]++;
        else if (p.x < 0 && p.y > 0) counts[1]++;
        else if (p.x < 0 && p.y < 0) counts[2]++;
        else if (p.x > 0 && p.y < 0) counts[3]++;
    }

    std::cout << "Punkty w cwiartkach: ";
    for (int count : counts) {
        std::cout << count << " ";
    }
    std::cout << std::endl;

    // Sortowanie wedlug jasnosci
    points.sort([](const Point& a, const Point& b) {
        return a.luminance() < b.luminance();
    });

    std::cout << "Posortowane punkty:" << std::endl;
    for (const auto& p : points) {
        std::cout << p.name << " (" << p.luminance() << ") ";
    }
    std::cout << std::endl;

    // Wyodrebnienie ciemnych punktow
    std::list<Point> darkPoints;
    std::copy_if(points.begin(), points.end(), std::back_inserter(darkPoints),
                           [](const Point& p) { return p.luminance() < 64; });

    std::cout << "Ciemne punkty:" << std::endl;
    for (const auto& p : darkPoints) {
        std::cout << p.name << " ";
    }
    std::cout << std::endl;

    return 0;
}
