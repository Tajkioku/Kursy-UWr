#include <iostream>
#include <fstream>
#include <iterator>
#include <algorithm>

using namespace std;

int eulerTotient(int n) {
    int result = n;
    for (int p = 2; p * p <= n; p++) {
        while (n % p == 0)
            n /= p;
        result -= result / p;
    }
    if (n > 1)
        result -= result / n;
    return result;
}

int main(int argc, char* argv[]) {
    if (argc != 2) {
        cerr << "Error: " << argv[0] << endl;
        return 1;
    }

    int k = atoi(argv[1]);

    ofstream outFile("phi.txt");
    ostream_iterator<int> outIt(outFile, "; ");

    for (int i = 1; i <= k; ++i) {
        *outIt++ = eulerTotient(i);
    }

    outFile.close();
    return 0;
}
