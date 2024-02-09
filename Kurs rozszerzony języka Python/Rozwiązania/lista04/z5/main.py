from itertools import permutations

def solve_cryptarithm(word1, word2, word3, operator):  
    unique_letters = set(word1 + word2 + word3)
    
    # Jezeli liter jest wiecej niz cyfr 0-9 -> koniec
    if len(unique_letters) > 10:
        return None

    # Sprawdzam wszystkie mozliwe mapowania liter na cyfry
    for perm in permutations('0123456789', len(unique_letters)):
        # Mapowanie litera -> zpermutowana cyfra
        mapping = dict(zip(sorted(unique_letters), perm))
        num1 = int(''.join(mapping[l] for l in word1))
        num2 = int(''.join(mapping[l] for l in word2))
        num3 = int(''.join(mapping[l] for l in word3))
        
        # Mapowanie operatorow
        f = {  '+': lambda x, y: x + y,
                '-': lambda x, y: x - y, 
                '*': lambda x, y: x * y,
                '/': lambda x, y: x / y  }

        # Sprawdzam wynik
        if f[operator](num1, num2) == num3:
            yield mapping
    return None


# Testy
result = solve_cryptarithm('KIOTO', 'OSAKA', 'TOKIO', '+')
print(result)
result = solve_cryptarithm('KIOTO', 'OSAKA', 'TOKIA', '-')
print(result)
result = solve_cryptarithm('KIOTO', 'OSAKA', 'TOKIO', '*')
print(result)
