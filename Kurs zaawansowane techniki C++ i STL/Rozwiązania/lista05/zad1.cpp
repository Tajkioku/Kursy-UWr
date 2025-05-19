#include <iostream>
#include <forward_list>
#include <stack>

enum class SymbolType {
    OPERATOR,
    OPERAND,
    LEFT_PARENTHESIS,
    RIGHT_PARENTHESIS
};

class Symbol {
public:
    SymbolType type;
    double value;
    std::string name;
    int priority;

    Symbol(SymbolType t, double v = 0.0, const std::string& n = "", int p = 0)
        : type(t), value(v), name(n), priority(p) {}
};

std::forward_list<Symbol> infixToPostfix(const std::forward_list<Symbol>& infixExpression) {
    std::forward_list<Symbol> postfixExpression;
    std::stack<Symbol> operatorsStack;

    for (const auto& symbol : infixExpression) {
        switch (symbol.type) {
            case SymbolType::OPERAND:
                postfixExpression.push_front(symbol);
                break;
            case SymbolType::OPERATOR:
                while (!operatorsStack.empty() && operatorsStack.top().priority >= symbol.priority) {
                    postfixExpression.push_front(operatorsStack.top());
                    operatorsStack.pop();
                }
                operatorsStack.push(symbol);
                break;
            case SymbolType::LEFT_PARENTHESIS:
                operatorsStack.push(symbol);
                break;
            case SymbolType::RIGHT_PARENTHESIS:
                while (!operatorsStack.empty() && operatorsStack.top().type != SymbolType::LEFT_PARENTHESIS) {
                    postfixExpression.push_front(operatorsStack.top());
                    operatorsStack.pop();
                }
                operatorsStack.pop();  // '('
                break;
        }
    }

    while (!operatorsStack.empty()) {
        postfixExpression.push_front(operatorsStack.top());
        operatorsStack.pop();
    }

    return postfixExpression;
}

int main() {
    std::forward_list<Symbol> infixExpression = {
        {SymbolType::OPERAND, 3.0, "x"},
        {SymbolType::OPERATOR, 0.0, "+", 1},
        {SymbolType::OPERAND, 5.0, "y"},
        {SymbolType::OPERATOR, 0.0, "*", 2},
        {SymbolType::LEFT_PARENTHESIS},
        {SymbolType::OPERAND, 2.0, "z"},
        {SymbolType::OPERATOR, 0.0, "+", 1},
        {SymbolType::OPERAND, 4.0, "w"},
        {SymbolType::RIGHT_PARENTHESIS},
        {SymbolType::OPERATOR, 0.0, "/", 2},
        {SymbolType::OPERAND, 6.0, "v"}
    };

    std::forward_list<Symbol> postfixExpression = infixToPostfix(infixExpression);

    std::cout << "Infix ";
    for (const auto& symbol : infixExpression) {
        if (symbol.type == SymbolType::LEFT_PARENTHESIS)
            std::cout << "(";
        else if (symbol.type == SymbolType::RIGHT_PARENTHESIS)
            std::cout << ")";
        else
            std::cout << symbol.name;
        std::cout << " ";
    }
    std::cout << std::endl;

    postfixExpression.reverse();
    std::cout << "Postfix";
    for (const auto& symbol : postfixExpression) {
        if (symbol.type == SymbolType::OPERAND)
            std::cout << " " << symbol.name;
        else
            std::cout << " " << symbol.name;
    }
    std::cout << std::endl;

    return 0;
}
