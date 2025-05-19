#include <iostream>
#include <regex>
#include <vector>

int main() {
    std::regex pattern("^((0[1-9]|[12][0-9]|3[01])-(0[13578]|1[02])-(\\d{4})|(0[1-9]|[12][0-9]|30)-(0[469]|11)-(\\d{4})|(0[1-9]|1[0-9]|2[0-8])-02-(\\d{4}))$");
    std::vector<std::string> tests = {
        // Prawidłowe
        "19-02-2002", "15-09-1999", "05-12-2018", "01-01-2000", "31-07-1995", "28-02-2000",
        // Nieprawidłowe
        "03/07/1917", "6-3-1945", "29-02-2022", "31-04-2020", "00-12-2000", "32-01-2001", "25-13-2021", 
        "31-02-2019", "31-09-2018", "31-06-2017", "31-11-2016", "31-04-2015", "31-00-2014", "00-01-2013", 
        "31-13-2012", "32-12-2011", "29-02-2019", "31-31-2010",

        "01-01-0002"
    };

    for (const auto& input : tests) {
        if (std::regex_match(input, pattern)) {
            std::cout << "Data " << input << " jest prawidłowa." << std::endl;
        } else {
            std::cout << "Data " << input << " jest nieprawidłowa." << std::endl;
        }
    }

    return 0;
}
