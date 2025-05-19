class Formula:
    def __init__(self):
        pass
    
    def calculate(self, variables):
        pass
    
    def __str__(self):
        pass
    
    def __add__(self, other):
        return Or(self, other)
    
    def __mul__(self, other):
        return And(self, other)
    
    # def tautology(self):

class Variable(Formula):
    def __init__(self, name):
        super().__init__()
        self.name = name
    
    def calculate(self, variables):
        return variables[self.name]
    
    def __str__(self):
        return self.name

class Constant(Formula):
    def __init__(self, value):
        super().__init__()
        self.value = value
    
    def calculate(self, variables):
        return self.value
    
    def __str__(self):
        return str(self.value)

class Not(Formula):
    def __init__(self, formula):
        super().__init__()
        self.formula = formula
    
    def calculate(self, variables):
        return not self.formula.calculate(variables)
    
    def __str__(self):
        return f"~({self.formula})"

class And(Formula):
    def __init__(self, left, right):
        super().__init__()
        self.left = left
        self.right = right
    
    def calculate(self, variables):
        return self.left.calculate(variables) and self.right.calculate(variables)
    
    def __str__(self):
        return f"({self.left}) ∧ ({self.right})"

class Or(Formula):
    def __init__(self, left, right):
        super().__init__()
        self.left = left
        self.right = right
    
    def calculate(self, variables):
        return self.left.calculate(variables) or self.right.calculate(variables)
    
    def __str__(self):
        return f"({self.left}) v ({self.right})"


# Testy 
simple_formula = And(Variable("p"), Constant(True))
variables_1 = {"p": True}
print("Test 1: ", simple_formula, "=", simple_formula.calculate(variables_1))

negation_formula = Not(Variable("q"))
variables_2 = {"q": False}
print("Test 2: ", negation_formula, "=", negation_formula.calculate(variables_2))

complex_formula = Or(And(Variable("a"), Not(Variable("b"))), Variable("c"))
variables_3 = {"a": True, "b": True, "c": False}
print("Test 3: ", complex_formula, "=", complex_formula.calculate(variables_3))

complex_formula2 = And(Or(Variable("x"), Variable("y")), Not(Variable("z")))
variables_4 = {"x": False, "y": True, "z": True}
print("Test 4: ", complex_formula2, "=", complex_formula2.calculate(variables_4))
