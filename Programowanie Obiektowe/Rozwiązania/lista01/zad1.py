# Kamil Wozniak 330601
# Lista 1 zad 1
# Python 3.13.1 

# Imperatywna wersja
def factorial_imp(n):
    result = 1
    for i in range(1, n + 1):
        result *= i
    return result

# Funkcyjna wersja 
def factorial_func(n):
    if n == 0:
        return 1
    else:
        return n * factorial_func(n - 1)

def binom(n, k):
    return factorial_func(n) // (factorial_func(k) * factorial_func(n - k))

def pascal_row(n):
    return [binom(n, k) for k in range(n + 1)]


def main():
    print(pascal_row(0))
    print(pascal_row(1))
    print(pascal_row(10))

if __name__ == '__main__':
    main()



