import random
import math

def pi_symulation(num_samples, tolerance):
    inside_circle = 0
    pi_approximation = 0

    for n in range(1, num_samples+1):
        x = random.uniform(0, 1)
        y = random.uniform(0, 1)
        distance = x**2 + y**2

        if distance <= 1:
            inside_circle += 1

        pi_approximation = 4 * inside_circle / n

        print(f"N: {n}, pi ~= {pi_approximation}")

        if abs(pi_approximation - math.pi) < tolerance:
            return (n, pi_approximation)

    return (num_samples, pi_approximation)

# Wywołanie symulacji
num_samples = 100000 
tolerance = 0.0001   
result = pi_symulation(num_samples, tolerance)
print(f"Przyblizenie po {result[0]} rzutach: {result[1]}")
