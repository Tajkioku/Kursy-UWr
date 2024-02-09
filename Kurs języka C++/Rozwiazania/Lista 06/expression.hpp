#ifndef EXPRESSIONS_HPP
#define EXPRESSIONS_HPP

#include <iostream>
#include <vector>
#include <cmath>

using namespace std;

class Expression
{
    public:
        virtual string description() = 0;
        virtual double eval() = 0;

        virtual int Priority()
        {
            return 999;
        }
    
        virtual ~Expression() {};
};

class Number : public Expression
{
    public:
        double value;
        Number (double value);

        string description();
        double eval();
};

class Variable : public Expression
{
    private:
        static vector<pair <string, double> > variables;

    public:
        static void addVariable(string var, double val);
        static void removeVariable(string var);
        static void printAll();
        
        string var_name;
        
        Variable(string x);

        double eval();
        string description();
        
};

class Const : public Expression
{
    protected:
        double value;
};

class pi : public Const
{
    public:
        string description();
        double eval();
        
        pi();
};

class e : public Const
{
    public:
        string description();
        double eval();
        
        e();
};

class fi : public Const
{
    public:
        string description();
        double eval();
        
        fi();
};

class Unary : public Expression
{
    public:
        Expression *x1;
        Unary(Expression *x1);
};

class Sin : public Unary
{
    public:
        string description();
        double eval();
        Sin(Expression *x1);
};

class Cos : public Unary
{
    public:
        string description();
        double eval();
        Cos(Expression *x1);
};

class Exp : public Unary
{
    public:
        string description();
        double eval();
        Exp(Expression *x1);
};

class Ln : public Unary
{
    public:
        string description();
        double eval();
        Ln(Expression *x1);
};

class Abs : public Unary
{
    public:
        string description();
        double eval();
        Abs(Expression *x1);
};

class Opposite : public Unary
{
    public:
        string description();
        double eval();
        Opposite(Expression *x1);
};

class Reciprocal : public Unary
{
    public:
        string description();
        double eval();
        Reciprocal(Expression *x1);
};

class Binary : public Unary
{
    public:
        int Priority() override
        {
            return 1;
        }
    
        Expression *x2;
        Binary(Expression *x1, Expression *x2);
};

class Add : public Binary
{
    public:
        string description();
        double eval();

        Add(Expression *x1, Expression *x2) : Binary(x1, x2) {}
};

class Sub : public Binary
{
    public:
        string description();
        double eval();

        Sub(Expression *x1, Expression *x2) : Binary(x1, x2) {}
};

class Mul : public Binary
{
    public:
        string description();
        double eval();

        int Priority()
        {
            return 10;
        }

        Mul(Expression *x1, Expression *x2) : Binary(x1, x2) {}
};

class Div : public Binary
{
    public:
        string description();
        double eval();

        int Priority()
        {
            return 10;
        }

//        bool LeftSided()
//        {
//            return true;
//        }

        Div(Expression *x1, Expression *x2) : Binary(x1, x2) {}
};

class Log : public Binary
{
    public:
        string description();
        double eval();

        int Priority()
        {
            return 20;
        }

        Log(Expression *x1, Expression *x2) : Binary(x1, x2) {}
};

class Mod : public Binary
{
    public:
        string description();
        double eval();

        int Priority()
        {
            return 20;
        }

        Mod(Expression *x1, Expression *x2) : Binary(x1, x2) {}
};

class Pow : public Binary
{
    public:
        string description();
        double eval();

        int Priority()
        {
            return 20;
        }

//        bool LeftSided()
//        {
//            return true;
//        }

        Pow(Expression *x1, Expression *x2) : Binary(x1, x2) {}
};

#endif
