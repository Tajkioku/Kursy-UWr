import numpy as np

def romberg_integration(f, a, b, m=20):
    # Create Romberg matrix
    R = np.zeros((m, m), dtype=float)
    
    # Fill in the first column of the Romberg matrix (metoda trapezowa)
    h = (b - a)
    R[0, 0] = h * (f(a) + f(b)) / 2
    for i in range(1, m):
        h /= 2
        sum_trap = sum(f(a + k * h) for k in range(1, 2**i, 2))
        R[i, 0] = R[i - 1, 0] / 2 + sum_trap * h

    # Fill in the rest of the Romberg matrix
    for i in range(1, m):
        for j in range(1, i + 1):
            R[i, j] = (4**j * R[i, j - 1] - R[i - 1, j - 1]) / (4**j - 1)

    # Return the top-right value of the Romberg matrix
    return R

#Functions to be integrated
def f1(x):
    return 2024*x**8 - 1977*x**4 - 1981

def f2(x):
    return 1 / (1 + x**2)

def f3(x):
    return np.sin(5*x - np.pi/3)

# Limits of functions
limits_f1 = (-3, 2)
limits_f2 = (-3, 3)
limits_f3 = (-3*np.pi, np.pi/6)


romberg_results = {
    'a': romberg_integration(f1, *limits_f1)[-1, -1],
    'b': romberg_integration(f2, *limits_f2)[-1, -1],
    'c': romberg_integration(f3, *limits_f3)[-1, -1]
}

print(romberg_results)
