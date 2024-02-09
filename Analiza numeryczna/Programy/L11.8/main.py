import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from numpy.polynomial.polynomial import Polynomial
from numpy.polynomial.legendre import Legendre

# Dane
covid_data = pd.read_csv('covid_data.csv')
x = np.arange(len(covid_data))
y = covid_data['Nowe przypadki'].values

# Optymalny wielomian uzywajac funkcji wbudowanych
degree = 3
coefficients = np.polyfit(x, y, degree)
p = np.poly1d(coefficients)

plt.figure(figsize=(14, 7))
plt.plot(x, y, 'b.', label='Original Data')
plt.plot(x, p(x), 'r-', label='Fitted Polynomial')
plt.title('Least Squares Polynomial Fit for COVID-19 New Cases in Poland')
plt.xlabel('Days since first case')
plt.ylabel('Number of new cases')
plt.legend()
plt.grid(True)
plt.show()


covid_data = pd.read_csv('covid_data.csv')
t_values = np.arange(len(covid_data))
y_values = covid_data['Nowe zgony'].values

plt.figure(figsize=(14, 7))
plt.plot(x, y, 'b.', label='Original Data')  # Original data points

# Z zadania L11.7 
# Kolejne katy
for n in range(3, 10):

    def inner_product(f, g, t_values):
        return np.sum(f(t_values) * g(t_values))

    leg_polynomials = [Legendre.basis(deg) for deg in range(n + 1)]

    t_min, t_max = np.min(t_values), np.max(x)
    t_scaled = 2 * (t_values - t_min) / (t_max - t_min) - 1

    a_k = np.array([inner_product(lambda x: leg_polynomials[k](x), lambda x: y, t_scaled) /
                    inner_product(lambda x: leg_polynomials[k](x), lambda x: leg_polynomials[k](x), t_scaled)
                    for k in range(n + 1)])

    def w_m(x):
        x_scaled = 2 * (x - t_min) / (t_max - t_min) - 1
        return sum(a_k[k] * leg_polynomials[k](x_scaled) for k in range(n + 1))

    x_dense = np.linspace(np.min(t_values), np.max(t_values), 1000)
    w_m_values = w_m(x_dense)
    
    plt.plot(x_dense, w_m_values, label=f'Optimal Polynomial n={n}')
    plt.title('Least Squares Polynomial Fit for COVID-19 New Deads in Poland')
    plt.xlabel('Days since first dead')
    plt.ylabel('Number of new cases')
    plt.legend()
    plt.grid(True)

plt.show()