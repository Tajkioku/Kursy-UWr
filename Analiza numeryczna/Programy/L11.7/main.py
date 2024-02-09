import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from scipy.interpolate import interp1d

data = pd.read_csv('punkty.csv', header=None, names=['t', 'y'])

def f(t):
    return (t - 1.2) * (t + 4.7) * (t - 2.3)

t_values = np.linspace(min(data['t']), max(data['t']), 200)
f_values = f(t_values)

# Plot f(t)
plt.figure(figsize=(14, 8))
plt.scatter(data['t'], data['y'], color='red', label='Observations', zorder=2)
plt.plot(t_values, f_values, label='Function f(t)', zorder=1)

# Plot interpolating polynomial
interpolation_function = interp1d(data['t'], data['y'], kind='cubic', fill_value='extrapolate')
interpolated_values = interpolation_function(t_values)
plt.plot(t_values, interpolated_values, label='Interpolated polynomial', linestyle='--', zorder=3)

plt.title('Function, Observations, Interpolated Polynomial')
plt.xlabel('t')
plt.ylabel('Values')
plt.legend(bbox_to_anchor=(1.05, 1), loc='upper left')
plt.grid(True)
plt.show()


