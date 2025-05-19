# Kamil Wozniak 330601
# Lista 1 zad 1 
# Python 3.13.1

# Imperatywna wersja
def gcd_imp(a, b):
    while b != 0:
        a, b = b, a % b
    return a

# Funkcyjna wersja
def gcd_func(a, b):
    return a if b == 0 else gcd_func(b, a % b)

# Imperatywna wersja
def prime_imp(n):
    result = []
    for i in range(1, n + 1):
        if gcd_imp(n, i) == 1:
            result.append(i)
    return result

# Funkcyjna wersja
def prime_func(n):
    return list(filter(lambda x: gcd_func(n, x) == 1, range(1, n + 1)))

def main():
    n = 10
    print(f"Imperatywne z {n}: {prime_imp(n)}")
    print(f"Funkcyjne z {n}: {prime_func(n)}")

if __name__ == '__main__':
    main()