#include <ratio>
#include <iostream>

int main() {
    using namespace std;

    // H1 = 1
    using H1 = ratio<1, 1>;

    // H2 = 1 + 1/2 = 3/2
    using H2 = ratio_add<H1, ratio<1, 2>>;

    // H3 = 1 + 1/2 + 1/3 = 11/6
    using H3 = ratio_add<H2, ratio<1, 3>>;

    cout << "H1 = " << H1::num << "/" << H1::den << endl;
    cout << "H2 = " << H2::num << "/" << H2::den << endl;
    cout << "H3 = " << H3::num << "/" << H3::den << endl;

    return 0;
}
