def pierwsze_imperatywna(n):
    if n < 2:
        return []
    
    liczby = list(range(2, n+1))
    pierwsze = []
    
    while liczby:
        liczba = liczby.pop(0)
        pierwsze.append(liczba)
        
        for i in liczby:
            if i % liczba == 0:
                liczby.remove(i)
                
    return pierwsze


def pierwsze_skladana(n):
    return [x for x in range(2, n+1) \
            if all(x % y != 0 for y in range(2, int(x**0.5) + 1))]


def pierwsze_funkcyjna(n):
    return list(filter(lambda x: all(x % y != 0 \
            for y in range(2, int(x**0.5) + 1)), range(2, n+1)))


import timeit

def testuj_funkcje(n_values, funkcje):  
    print(f"{'n':<10}{'imperatywna':<15}{'skladana':<15}{'funkcyjna':<15}")
    print('-' * 50)
    for n in n_values:
        wyniki = []
        for funkcja in funkcje:
            czas = timeit.timeit(lambda: funkcja(n), number=1)
            wyniki.append(czas)
        print(f"{n:<10}{wyniki[0]:<15.5f}{wyniki[1]:<15.5f}{wyniki[2]:<15.5f}")


# Testy
n_values = [1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000]
funkcje = [pierwsze_imperatywna, pierwsze_skladana, pierwsze_funkcyjna]
testuj_funkcje(n_values, funkcje)

