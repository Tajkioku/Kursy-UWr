#include <iostream>
#include <vector>
#include "expression.hpp"

int main()
{
    Variable::addVariable("x", 10);
    Expression *num1 =
    new Div(
        new Div(
            new Mul(
                new Sub(
                        new Variable("x"),
                        new Number(1)),
                new Number(0.5)),
            new Variable("x")),
        new Number(2));
    cout << num1->eval() << " = " << num1->description() << endl;
    Variable::removeVariable("x");
    Variable::addVariable("x", 5);
    cout << num1->eval() << " = " << num1->description() << endl;
    Variable::removeVariable("x");
    Variable::addVariable("x", 1);
    cout << num1->eval() << " = " << num1->description() << endl;

    Expression *num2 =
    new Div(
        new Add(
            new Number(1),
            new Number(2)),
        new Sub(
                new Number(3),
                new Mul(
                        new Variable("x"),
                        new Number(4))));
    
    Variable::removeVariable("x");
    Variable::addVariable("x", 1);
    cout << num2->eval() << " = " << num2->description() << endl;
    Variable::removeVariable("x");
    Variable::addVariable("x", 2);
    cout << num2->eval() << " = " << num2->description() << endl;
    Variable::removeVariable("x");
    Variable::addVariable("x", 3);
    cout << num2->eval() << " = " << num2->description() << endl;
    
    Expression *num3 =
    new Div(
        new Cos(
                new Add(
                    new Sub(
                            new Variable("x"),
                            new Number(1)),
            new Variable("x"))),
        new Pow(
            new Pow(
                    new e(),
                    new Variable("x")),
            new Number(2)));

    Variable::removeVariable("x");
    Variable::addVariable("x", 0.25);
    cout << num3->eval() << " = " << num3->description() << endl;
    Variable::removeVariable("x");
    Variable::addVariable("x", 0.5);
    cout << num3->eval() << " = " << num3->description() << endl;
    Variable::removeVariable("x");
    Variable::addVariable("x", 1);
    cout << num3->eval() << " = " << num3->description() << endl;

    return 0;
}
