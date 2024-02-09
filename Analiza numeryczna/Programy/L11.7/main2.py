import numpy as np
import pandas as pd
from numpy.polynomial.legendre import Legendre
import numpy as np
import matplotlib.pyplot as plt

data = pd.read_csv('punkty.csv', header=None)
t_values = data.iloc[:, 0].values
y_values = data.iloc[:, 1].values

plt.figure(figsize=(12, 6))
plt.scatter(t_values, y_values, color='blue', label='Data points')  

# Kolejne katy
for n in range(2, 16):

    # Dyskretny ilocyczn skalarny
    def inner_product(f, g, t_values):
        return np.sum(f(t_values) * g(t_values))

    # Generate Legendre polynomials up to degree n
    # We use the Legendre class which generates orthogonal polynomials on [-1, 1]
    # If our t_values are not in this range, we would need to scale them appropriately
    leg_polynomials = [Legendre.basis(deg) for deg in range(n + 1)]

    # Scale polynomials if t_values are not in [-1, 1]
    t_min, t_max = np.min(t_values), np.max(t_values)
    t_scaled = 2 * (t_values - t_min) / (t_max - t_min) - 1  # Scale to [-1, 1]

    # Wspolczynniki a_k
    a_k = np.array([inner_product(lambda x: leg_polynomials[k](x), lambda x: y_values, t_scaled) /
                    inner_product(lambda x: leg_polynomials[k](x), lambda x: leg_polynomials[k](x), t_scaled)
                    for k in range(n + 1)])

    # Wielomian w_m
    def w_m(x):
        # Skalowanie do [-1, 1]
        x_scaled = 2 * (x - t_min) / (t_max - t_min) - 1
        return sum(a_k[k] * leg_polynomials[k](x_scaled) for k in range(n + 1))

    # Plotting
    x_dense = np.linspace(np.min(t_values), np.max(t_values), 1000)
    w_m_values = w_m(x_dense)
    
    plt.plot(x_dense, w_m_values, label=f'Optimal Polynomial n={n}')
    plt.title('Data Points and Optimal Polynomial Approximation')
    plt.xlabel('t')
    plt.ylabel('y')
    plt.legend()
    plt.grid(True)
    
plt.show()
