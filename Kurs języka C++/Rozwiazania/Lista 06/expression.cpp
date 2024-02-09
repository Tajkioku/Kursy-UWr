#include "expression.hpp"

vector<pair <string, double> > Variable::variables;

Number::Number(double value)
{
    this->value = value;
}

string Number::description()
{
    string str = to_string(value);
    return str;
}

double Number::eval()
{
    return value;
}


Variable::Variable(string x)
{
    this->var_name = x;
}

string Variable::description()
{
    return var_name;
}

double Variable::eval()
{
    try
    {
        int Size = (int)Variable::variables.size();
        for (int i = 0; i < Size; i++)
        {
            if (Variable::variables[i].first == var_name)
            {
                return Variable::variables[i].second;
            }
        }

        string error = "Brak wyrazen";
        throw error;
    }
    catch(string error)
    {
        cerr << "Error podczas wykonywania eval: " << error << endl;
        return -1;
    }
}

void Variable::addVariable(string x, double value)
{
    int Size = (int)Variable::variables.size();
    for (int i = 0; i < Size; i++)
    {
        if (Variable::variables[i].first == x)
        {
            cout << "Wyrazenie " << x << "juz istnieje!" << endl;
            return;
        }
    }
    auto new_var = make_pair(x, value);
    Variable::variables.push_back(new_var);
}

void Variable::removeVariable(string x)
{
    try
    {
        int Size = (int)Variable::variables.size();
        for (int i = 0; i < Size; i++)
        {
            if (Variable::variables[i].first == x)
            {
                Variable::variables.erase(Variable::variables.begin() + i);
                return;
            }
        }
        
        string error = "Nie znaleziono wyrazenia";
        throw error;
    }
    catch (string error)
    {
        cout << "Error podczas usuwania wyrazenia: " << error << endl;
    }
}

void Variable::printAll()
{
    int Size = (int)Variable::variables.size();
    
    cout << "Wszystkie wyrazenia: " << endl;
    for (int i = 0; i < Size; i++)
    {
        cout << Variable::variables[i].first << " = " << Variable::variables[i].second << endl;
        
    }
}

pi::pi()
{
    value = 3.14159265;
}

string pi::description()
{
    return "pi";
}

double pi::eval()
{
    return value;
}

e::e()
{
    value = 2.71828182;
}

string e::description()
{
    return "e";
}

double e::eval()
{
    return value;
}

fi::fi()
{
    value = 1.61803399;
}

std::string fi::description()
{
    return "fi";
}

double fi::eval()
{
    return value;
}

Unary::Unary(Expression *x1)
{
    this->x1 = x1;
}

Sin::Sin(Expression *x1) : Unary(x1) {}

string Sin::description()
{
    return "sin(" + x1->description() + ")";
}

double Sin::eval()
{
    return sin(x1->eval());
}

Cos::Cos(Expression *x1) : Unary(x1) {}

string Cos::description()
{
    return "cos(" + x1->description() + ")";
}

double Cos::eval()
{
    return cos(x1->eval());
}

Exp::Exp(Expression *x1) : Unary(x1) {}

string Exp::description()
{
    return "exp(" + x1->description() + ")";
}

double Exp::eval()
{
    return exp(x1->eval());
}

Ln::Ln(Expression *x1) : Unary(x1) {}

string Ln::description()
{
    return "ln(" + x1->description() + ")";
}

double Ln::eval()
{
    return log(x1->eval());
}

Abs::Abs(Expression *x1) : Unary(x1) {}

string Abs::description()
{
    return "|" + x1->description() + "|";
}

double Abs::eval()
{
    return abs(x1->eval());
}

Opposite::Opposite(Expression *x1) : Unary(x1) {}

string Opposite::description()
{
    return "-" + x1->description();
}

double Opposite::eval()
{
    return -1 * x1->eval();
}

Reciprocal::Reciprocal(Expression *x1) : Unary(x1) {}

string Reciprocal::description()
{
    return "1 / " + x1->description();
}

double Reciprocal::eval()
{
    return 1.0 / x1->eval();
}

Binary::Binary(Expression *x1, Expression *x2) : Unary(x1)
{
    this->x2 = x2;
}

string Add::description()
{
    string left, right;

    if (x1->Priority() > Priority() )
        left = x1->description();
    else
        left = "(" + x1->description() + ")";

    if (x2->Priority() > Priority() )
        right = x2->description();
    else
        right = "(" + x2->description() + ")";

    return left + " + " + right;
}

double Add::eval()
{
    return x1->eval() + x2->eval();
}

string Sub::description()
{
    string left, right;

    if (x1->Priority() > Priority() )
        left = x1->description();
    else
        left = "(" + x1->description() + ")";

    if (x2->Priority() > Priority() )
        right = x2->description();
    else
        right = "(" + x2->description() + ")";

    return left + " - " + right;
}

double Sub::eval()
{
    return x1->eval() - x2->eval();
}

string Mul::description()
{
    string left, right;

    if (x1->Priority() > Priority())
        left = x1->description();
    else
        left = "(" + x1->description() + ")";

    if (x2->Priority() > Priority())
        right = x2->description();
    else
        right = "(" + x2->description() + ")";

    return left + " * " + right;
}

double Mul::eval()
{
    return x1->eval() * x2->eval();
}

string Div::description()
{
    string left, right;

    if (x1->Priority() > Priority())
        left = x1->description();
    else
        left = "(" + x1->description() + ")";

    if (x2->Priority() > Priority())
        right = x2->description();
    else
        right = "(" + x2->description() + ")";

    return left + " / " + right;
}

double Div::eval()
{
    return x1->eval() / x2->eval();
}

string Log::description()
{
    return "log(" + x1->description() + ", " + x2->description() + ")";
}

double Log::eval()
{
    double e1, e2;

    e1 = log (x1->eval());
    e2 = log (x2->eval());

    return e2 / e1;
}

string Mod::description()
{
    string left, right;

    if (x1->Priority() > Priority())
        left = x1->description();
    else
        left = "(" + x1->description() + ")";

    if (x2->Priority() > Priority())
        right = x2->description();
    else
        right = "(" + x2->description() + ")";

    return left + " % " + right;
}

double Mod::eval()
{
    return fmod(x1->eval(), x2->eval());
}

string Pow::description()
{
    string left, right;

    if (x1->Priority() > Priority())
        left = x1->description();
    else
        left = "(" + x1->description() + ")";

    if (x2->Priority() > Priority())
        right = x2->description();
    else
        right = "(" + x2->description() + ")";

    return left + " ^ " + right;
}

double Pow::eval()
{
    return pow(x1->eval(), x2->eval());
}


