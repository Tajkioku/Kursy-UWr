# Zadanie L8.9 Kamil Woźniak
# Rysowanie fraktali
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.colors import ListedColormap

def f(z, n):
    return z**n + 1

def df(z, n):
    return n * z**(n - 1)

def ddf(z, n):
    return n * (n - 1) * z**(n - 2)

def newton_method(z, n, N):
    for i in range(N):
        z -= f(z, n) / df(z, n)
    return z

# Przekształcony wzór równoważnie metody halleya z jednym dzieleniem
def halley_method(z0, n, N):
    for i in range(N):
        fz = f(z0, n)
        dfz = df(z0, n)
        ddfz = ddf(z0, n)
        denominator = 2*dfz*dfz - ddfz*fz
        if abs(denominator) < 1e-8:
            return z0
        z0 -= ((2*fz*dfz) / denominator)
    return z0

# Funkcja przypisuje kolor na podstawie pierwiastka wielomianu,
# do którego jest on najbliższy (odległy o epsilon).
def assign_color(z, roots, epsilon=1e-8):
    for i, root in enumerate(roots):
        if abs(z - root) < epsilon:
            return i
    return len(roots)  # Kolor czarny

# Funkcja generuje siatkę punktów na płaszczyźnie zespolonej,
# stosuje metodę Newtona / Halleya i przypisuje kolor dla kaźdego punktu.
def generate_color_grid(n, M, N, type):
    # Obliczanie pierwiastków wielomianu z^n + 1
    roots = np.roots([1] + [0]*(n - 1) + [1])
    # Rozkładam M wartości równomiernie z przedziału [-1, 1]
    grid_x = np.linspace(-1, 1, M)
    grid_y = np.linspace(-1, 1, M)
    # Oraz tworze siatkę kolorów M x M
    colors = np.zeros((M, M), dtype=int)
    
    # Do siatki przypisuje odpowiednie kolory
    for i, x in enumerate(grid_x):
        for j, y in enumerate(grid_y):
            z0 = complex(x, y)
            # Wybór metody Newton / Halley
            if type == 'newton':
                z_final = newton_method(z0, n, N)
            elif type == 'halley':
                z_final = halley_method(z0, n, N)
            colors[j, i] = assign_color(z_final, roots)
    return colors

# Rysowanie grafiki
def plot_fractals(n_values, N_values, M, type, filename):
    fig, axes = plt.subplots(len(N_values), len(n_values), 
    figsize=(4 * len(n_values), 4 * len(N_values)))
    for i, N in enumerate(N_values):
        for j, n in enumerate(n_values):
            ax = axes[i][j] if len(N_values) > 1 else axes[j]
            colors = generate_color_grid(n, M, N, type)
            cmap = ListedColormap([plt.cm.hsv(i / n) for i in range(n)] + [(0, 0, 0, 1)])
            ax.imshow(colors, extent=(-1, 1, -1, 1), cmap=cmap, origin='lower')
            ax.set_title(f'n = {n}, N = {N}, M = {M}')
            ax.axis('off')
    plt.tight_layout()
    # plt.show() # Wyswietlanie grafiki
    plt.savefig(filename) # Zapisywanie Grafiki



n_values = [3, 4, 5, 6]  # Potęgi n funkcji z^n + 1
N_values = [10, 20, 35]  # Ilość iteracji metody Newtona / Halleya
M = 50 # Rodzielczosc grafiki

plot_fractals(n_values, N_values, M, 'newton', 'newton50.png')
plot_fractals(n_values, N_values, M, 'halley', 'halley50.png')

M = 400
plot_fractals(n_values, N_values, M, 'newton', 'newton400.png')
plot_fractals(n_values, N_values, M, 'halley', 'halley400.png')

M = 800 
plot_fractals(n_values, N_values, M, 'newton', 'newton800.png')
plot_fractals(n_values, N_values, M, 'halley', 'halley800.png')

n_values = [3, 4, 5, 6]
N_values = [4, 6, 8]
M = 800
plot_fractals(n_values, N_values, M, 'halley', 'halley_small_N.png')