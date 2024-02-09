import numpy as np
import matplotlib.pyplot as plt
# from scipy.interpolate import CubicSpline

x_points = [5.5, 8.5, 10.5, 13, 17, 20.5, 24.5, 28, 32.5, 37.5, 40.5, 42.5, 45, 47, 49.5, 50.5, 51, 51.5, 52.5, 53, 52.8, 52, 51.5, 53, 54, 55, 56, 55.5, 54.5, 54, 55, 57, 58.5, 59, 61.5, 62.5, 63.5, 63, 61.5, 59, 55, 53.5, 52.5, 50.5, 49.5, 50, 51, 50.5, 49, 47.5, 46, 45.5, 45.5, 45.5, 46, 47.5, 47.5, 46, 43, 41, 41.5, 41.5, 41, 39.5, 37.5, 34.5, 31.5, 28, 24, 21, 18.5, 17.5, 16.5, 15, 13, 10, 8, 6, 6, 6, 5.5, 3.5, 1, 0, 0, 0.5, 1.5, 3.5, 5, 5, 4.5, 4.5, 5.5, 6.5, 6.5, 5.5]
y_points = [41, 40.5, 40, 40.5, 41.5, 41.5, 42, 42.5, 43.5, 45, 47, 49.5, 53, 57, 59, 59.5, 61.5, 63, 64, 64.5, 63, 61.5, 60.5, 61, 62, 63, 62.5, 61.5, 60.5, 60, 59.5, 59, 58.5, 57.5, 55.5, 54, 53, 51.5, 50, 50, 50.5, 51, 50.5, 47.5, 44, 40.5, 36, 30.5, 28, 25.5, 21.5, 18, 14.5, 10.5, 7.50, 4, 2.50, 1.50, 2, 3.50, 7, 12.5, 17.5, 22.5, 25, 25, 25, 25.5, 26.5, 27.5, 27.5, 26.5, 23.5, 21, 19, 17, 14.5, 11.5, 8, 4, 1, 0, 0.5, 3, 6.50, 10, 13, 16.5, 20.5, 25.5, 29, 33, 35, 36.5, 39, 41]
t = np.linspace(0, 1, len(x_points))

def compute_natural_cubic_spline_coefficients(x, y):
    n = len(x) - 1
    h = np.diff(x) # Licze interwaly  h[i] = x[i+1] - x[i]
    
    # wektor a
    a = y
    
    A = np.zeros((n + 1, n + 1))
    A[0, 0] = 1  # Naturalnosc drugiej pochodnej
    A[n, n] = 1 # Naturalnosc drugiej pochodnej

    # Rownania dla punktow wewnetrznych
    for i in range(1, n):
        A[i, i - 1] = h[i - 1]
        A[i, i] = 2 * (h[i - 1] + h[i])
        A[i, i + 1] = h[i]
    
    # b sluzy do wymuszenia ciaglosci pierwszej pochodnej
    b = np.zeros(n + 1)
    for i in range(1, n):
        b[i] = 3 * ((a[i + 1] - a[i]) / h[i] - (a[i] - a[i - 1]) / h[i - 1])
    
    # c wektor nieznanych 2 pochodnych
    c = np.linalg.solve(A, b)
    
    # Rozwiazanie b i d
    b = np.zeros(n)
    d = np.zeros(n)
    for i in range(n):
        b[i] = ((a[i + 1] - a[i]) / h[i]) - (h[i] / 3) * (2 * c[i] + c[i + 1])
        d[i] = (c[i + 1] - c[i]) / (3 * h[i])
        
    return a[:-1], b, c[:-1], d

# Obliczam Sx i Sy
coefficients_x = compute_natural_cubic_spline_coefficients(t, x_points)
coefficients_y = compute_natural_cubic_spline_coefficients(t, y_points)

# coefficients_x, coefficients_y


def evaluate_spline(t, x, coefficients):
    a, b, c, d = coefficients
    n = len(a)
    # t = np.atleast_1d(t)
    result = np.zeros_like(t)
    for i in range(n):
        # Znajdujemy ity wielomian
        # S(x) = yi + bi(x-xi) + ci(x-xi)^2 + di(x-xi)^3
        in_domain = (x[i] <= t) & (t <= x[i+1]) # Sprawdzanie ktore punkty wpadly w dany przedzial
        dx = t[in_domain] - x[i]
        result[in_domain] = (
            a[i] + b[i] * dx + c[i] * dx**2 + d[i] * dx**3
        )
    return result

# Podstawiam ti do otrzymanych wielomianow
t_dense = np.linspace(t[0], t[-1], 1000)
x_dense = evaluate_spline(t_dense, t, coefficients_x)
y_dense = evaluate_spline(t_dense, t, coefficients_y)

# Rysowanie
plt.figure(figsize=(10, 5))
plt.plot(x_dense, y_dense, label='NFS3')
plt.scatter(x_points, y_points, color='red', label='Punkty', zorder=5)
plt.title('NFS3')
plt.grid(True)
plt.show()
