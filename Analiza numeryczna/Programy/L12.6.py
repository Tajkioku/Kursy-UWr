import numpy as np
from scipy.special import factorial

def newton_cotes_coefficients(n):
    # Generate all the possible 't' values for the integral
    t_values = np.linspace(0, n-1, n)
    
    coefficients = np.zeros(n)
    
    # Calculate each coefficient
    for k in range(n):
        # Calculate the product term for the k-th coefficient
        product_term = np.prod([t_values[j] - t_values[k] for j in range(n) if j != k])
        
        coefficients[k] = (-1)**(n-1-k) * factorial(n-1) / (factorial(k) * factorial(n-1-k) * product_term)
    
    return coefficients

def integrate_newton_cotes(f, a, b, n):
    # Generate the n equally spaced sample points
    x = np.linspace(a, b, n)
    h = (b - a) / (n - 1)

    # Get the Newton-Cotes coefficients
    nc_coefficients = newton_cotes_coefficients(n)

    # Evaluate the function at the sample points
    fx = f(x)

    # Compute the weighted sum of the function values
    integral = h * sum(nc_coefficients * fx)
    
    return integral

# Define the function to integrate
def f(x):
    return 1 / (1 + 25 * x**2)

own_integral_results = {n: integrate_newton_cotes(f, -1, 1, n) for n in range(2, 25)}
print("Results using custom Newton-Cotes integration:")
for n, result in own_integral_results.items():
    print(f"For n = {n}, the integral is approximately {result:.10f}")
